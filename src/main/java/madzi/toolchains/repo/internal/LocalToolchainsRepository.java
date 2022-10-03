package madzi.toolchains.repo.internal;

import java.io.IOException;
import java.nio.file.Path;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import madzi.toolchains.repo.ToolchainsRepository;
import org.xml.sax.SAXException;

/**
 * The implementation of ToolchainsRepository for local ~/.m2/toolchains.xml file.
 *
 * @author de
 */
public class LocalToolchainsRepository implements ToolchainsRepository {

    private final Path filePath;

    public LocalToolchainsRepository(final Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public void list() {
        try {
            System.out.println("NODE LIST");
            final var factory = DocumentBuilderFactory.newInstance();
            final var builder = factory.newDocumentBuilder();
            final var document = builder.parse(filePath.toFile());
            final var nodeList = document.getElementsByTagName("toolchain");
            final var len = nodeList.getLength();
            System.out.println("Found: " + len + " record(s)");
            for (int idx = 0; idx < len; ++idx) {
                System.out.println(nodeList.item(idx));
                // @todo extract correc nodes with types: toolchain/netbeans/...
            }
        } catch (final IOException | SAXException | ParserConfigurationException exception) {
            // @todo proper exception handling
            exception.printStackTrace();
        }
    }
}
