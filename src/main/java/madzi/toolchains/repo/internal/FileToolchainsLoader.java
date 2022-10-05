package madzi.toolchains.repo.internal;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import madzi.toolchains.domain.Toolchain;
import madzi.toolchains.repo.ToolchainsLoader;
import madzi.toolchains.repo.ToolchainsXmlConverter;

/**
 *
 * @author de
 */
public class FileToolchainsLoader implements ToolchainsLoader {

    private final Path path;
    private final ToolchainsXmlConverter converter;

    public FileToolchainsLoader(final Path path, final ToolchainsXmlConverter converter) {
        this.path = path;
        this.converter = converter;
    }

    @Override
    public Iterable<Toolchain> load() {
        try {
            return converter.fromXml(Files.readString(path, StandardCharsets.UTF_8));
        } catch (final IOException exception) {
            throw new IllegalStateException("Unable to load toolchains from file: " + path.getFileName(), exception);
        }
    }

    @Override
    public void save(final Iterable<Toolchain> items) {
        try {
            Files.writeString(path, converter.toXml(items), StandardCharsets.UTF_8, StandardOpenOption.CREATE);
        } catch (final IOException exception) {
            throw new IllegalStateException("Unable to save toolchains.xml", exception);
        }
    }
}
