package madzi.apps.sdktool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;

import madzi.apps.sdktool.configuration.SdkToolConfiguration;
import madzi.apps.sdktool.model.JdkInfo;
import madzi.apps.sdktool.service.JdkLocatorService;
import madzi.apps.sdktool.service.ToolchainsBuilderService;

public class SdkToolApplication {

    private final SdkToolConfiguration configuration;

    private SdkToolApplication(final SdkToolConfiguration configuration) {
        this.configuration = configuration;
    }

    private void run() {
        final JdkLocatorService jdkLocService = configuration.jdkLocationService();
        final ToolchainsBuilderService toolBldService = configuration.toolchainsBuilderService();
        final Collection<JdkInfo> jdks = jdkLocService.findInstalledJdk();
        final String xml = toolBldService.buildToolchains(jdks);
//        System.out.println(xml);
        try {
            Path toolchainsXml = configuration.mavenHome().resolve("toolchains.xml");
            if (toolchainsXml.toFile().exists()) {
                toolchainsXml.toFile().delete();
            }
            Files.writeString(toolchainsXml, xml, StandardOpenOption.CREATE_NEW);
        } catch (final IOException ioException) {
            //
        }
    }

    public static void main(String... args) {
        System.out.println("sdkman -> toolchains convertor");
        SdkToolConfiguration config = new SdkToolConfiguration();
        SdkToolApplication app = new SdkToolApplication(config);
        app.run();
    }
}
