package madzi.toolchains.domain;

import java.nio.file.Path;

public record ToolchainRecord(
        Toolchain.Type type,
        Toolchain.Provides provides,
        Toolchain.Configuration configuration
) implements Toolchain {

    public static Toolchain jdk(final String version, final String vendor, final Path jdkHome) {
        return new ToolchainRecord(Type.JDK, new ProvidesRecord(version, vendor), new ConfigurationRecord(jdkHome));
    }

    public static Toolchain netbeans(final String version, final Path installDir) {
        return new ToolchainRecord(Type.NETBEANS, new ProvidesRecord(version, null), new ConfigurationRecord(installDir));
    }
}
