package madzi.toolchains.repo;

import java.nio.file.Paths;
import java.util.List;
import madzi.toolchains.domain.Toolchain;
import madzi.toolchains.repo.internal.StaxToolchainsXmlConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Toolchains XML Converter")
public class ToolchainsXmlConverterTest {

    @Test
    @DisplayName("can create")
    public void testCanCreateXml() {
        final var converter = new StaxToolchainsXmlConverter();
        final var xml = converter.toXml(List.of(Toolchain.builder()
                                                         .type(Toolchain.Type.JDK)
                                                         .version("17")
                                                         .vendor("oracle")
                                                         .path(Paths.get("/home"))
                                                         .build()));
        System.out.println(xml);
    }
}
