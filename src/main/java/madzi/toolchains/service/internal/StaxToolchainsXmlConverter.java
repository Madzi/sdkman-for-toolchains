package madzi.toolchains.service.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Optional;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import madzi.toolchains.domain.Toolchain;
import madzi.toolchains.domain.Toolchain.Type;
import madzi.toolchains.service.ToolchainsXmlConverter;

public class StaxToolchainsXmlConverter implements ToolchainsXmlConverter {

    private static final String TAG_TOOLCHAINS = "toolchains";
    private static final String TAG_TOOLCHAIN = "toolchain";
    private static final String TAG_TYPE = "type";
    private static final String TAG_PROVIDES = "provides";
    private static final String TAG_VERSION = "version";
    private static final String TAG_VENDOR = "vendor";
    private static final String TAG_CONFIGURATION = "configuraion";
    private static final String TAG_JDK_HOME = "jdkHome";
    private static final String TAG_INSTALL_DIR = "installDir";

    @Override
    public Iterable<Toolchain> fromXml(final String xml) {
        final var set = new HashSet<Toolchain>();
        try (var stream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))) {
            final var factory = XMLInputFactory.newInstance();
            final var reader = factory.createXMLEventReader(stream);
            Optional<Toolchain.Builder> opBuilder = Optional.empty();
            while (reader.hasNext()) {
                final var event = reader.nextEvent();
                if (event.isStartElement()) {
                    final var element = event.asStartElement();
                    switch (element.getName().getLocalPart()) {
                        case TAG_TOOLCHAIN -> opBuilder = Optional.of(Toolchain.builder());
                        case TAG_TYPE -> {
                            opBuilder.ifPresent(builder -> {
                                final var type = getData(reader);
                                if (Type.JDK.name().equalsIgnoreCase(type)) {
                                    builder.type(Type.JDK);
                                } else if (Type.NETBEANS.name().equalsIgnoreCase(type)) {
                                    builder.type(Type.NETBEANS);
                                }
                            });
                        }
                        case TAG_VERSION -> {
                            opBuilder.ifPresent(builder -> builder.version(getData(reader)));
                        }
                        case TAG_VENDOR -> {
                            opBuilder.ifPresent(builder -> builder.vendor(getData(reader)));
                        }
                        case TAG_JDK_HOME -> {
                            opBuilder.ifPresent(builder -> builder.path(Paths.get(getData(reader))));
                        }
                        case TAG_INSTALL_DIR -> {
                            opBuilder.ifPresent(builder -> builder.path(Paths.get(getData(reader))));
                        }
                        default -> {
                        }
                    }
                } else if (event.isEndElement()) {
                    final var element = event.asEndElement();
                    switch (element.getName().getLocalPart()) {
                        case TAG_TOOLCHAIN -> {
                            opBuilder.ifPresent(builder -> set.add(builder.build()));
                            opBuilder = Optional.empty();
                        }
                        default -> {
                        }
                    }
                }
            }
        } catch (final IOException | XMLStreamException exception) {
            throw new IllegalStateException(exception);
        }
        return set;
    }

    @Override
    public String toXml(final Iterable<Toolchain> toolchains) {
        try (var stream = new ByteArrayOutputStream()) {
            final var output = XMLOutputFactory.newFactory();
            final var writer = output.createXMLStreamWriter(stream);
            writer.writeStartDocument(StandardCharsets.UTF_8.name(), "1.0");
            writer.writeStartElement(TAG_TOOLCHAINS);
            writer.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
            writer.writeAttribute("xmlns", "http://maven.apache.org/TOOLCHAINS/1.1.0");
            writer.writeAttribute("xsi:schemaLocation", "http://maven.apache.org/TOOLCHAINS/1.1.0 http://maven.apache.org/xsd/toolchains-1.1.0.xsd");
            writer.writeComment("This document was generated by 'toolchains' tool. Please don't touch it.");
            if (toolchains != null) {
                toolchains.forEach(toolchain -> {
                    try {
                        writer.writeStartElement(TAG_TOOLCHAIN);
                        writer.writeStartElement(TAG_TYPE);
                        writer.writeCharacters(toolchain.type().name().toLowerCase());
                        writer.writeEndElement();
                        writer.writeStartElement(TAG_PROVIDES);
                        writer.writeStartElement(TAG_VERSION);
                        writer.writeCharacters(toolchain.provides().version());
                        writer.writeEndElement();
                        if (null != toolchain.provides().vendor()) {
                            writer.writeStartElement(TAG_VENDOR);
                            writer.writeCharacters(toolchain.provides().vendor());
                            writer.writeEndElement();
                        }
                        writer.writeEndElement();
                        writer.writeStartElement(TAG_CONFIGURATION);
                        if (Type.JDK == toolchain.type()) {
                            writer.writeStartElement(TAG_JDK_HOME);
                            writer.writeCharacters(toolchain.configuration().jdkHome());
                            writer.writeEndElement();
                        } else if (Type.NETBEANS == toolchain.type()) {
                            writer.writeStartElement(TAG_INSTALL_DIR);
                            writer.writeCharacters(toolchain.configuration().installDir());
                            writer.writeEndElement();
                        }
                        writer.writeEndElement();
                        writer.writeEndElement();
                    } catch (final XMLStreamException exception) {
                        throw new RuntimeException("Unable to write toolchain element", exception);
                    }
                });
            }
            writer.writeEndElement();
            writer.writeEndDocument();
            writer.flush();
            writer.close();
            return format(stream.toString());
        } catch (final IOException | XMLStreamException exception) {
            throw new IllegalStateException(exception);
        }
    }

    private String getData(final XMLEventReader reader) {
        try {
            return reader.nextEvent().asCharacters().getData();
        } catch (final XMLStreamException exception) {
            throw new RuntimeException("Unable to process data from XML", exception);
        }
    }

    private String format(String xml) {
        try {
            final var factory = TransformerFactory.newInstance();
            final var transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            final var source = new StreamSource(new StringReader(xml));
            final var target = new StringWriter();
            transformer.transform(source, new StreamResult(target));
            return target.toString();
        } catch (final TransformerException exception) {
            return xml;
        }
    }
}
