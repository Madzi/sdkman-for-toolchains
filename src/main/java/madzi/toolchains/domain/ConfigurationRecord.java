package madzi.toolchains.domain;

import java.nio.file.Path;

public record ConfigurationRecord(Path path) implements Toolchain.Configuration {

    @Override
    public String jdkHome() {
        return path.toString();
    }

    @Override
    public String installDir() {
        return path.toString();
    }
}
