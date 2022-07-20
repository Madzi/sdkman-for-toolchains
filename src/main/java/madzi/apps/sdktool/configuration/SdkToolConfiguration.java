package madzi.apps.sdktool.configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

import madzi.apps.sdktool.service.JdkLocatorService;
import madzi.apps.sdktool.service.MavenToolchainsBuilderService;
import madzi.apps.sdktool.service.SdkManJdkLocatorService;
import madzi.apps.sdktool.service.ToolchainsBuilderService;

public class SdkToolConfiguration {

    public JdkLocatorService jdkLocationService() {
        Path sdkmanFolderJava = sdkmanHome().resolve("candidates").resolve("java");
        return new SdkManJdkLocatorService(sdkmanFolderJava);
    }

    public ToolchainsBuilderService toolchainsBuilderService() {
        return new MavenToolchainsBuilderService();
    }

    public Path sdkmanHome() {
        return userHome().resolve(".sdkman");
    }

    public Path mavenHome() {
        return userHome().resolve(".m2");
    }

    public Path userHome() {
        return Paths.get(System.getProperty("user.home"));
    }
}
