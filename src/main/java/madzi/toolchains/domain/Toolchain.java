package madzi.toolchains.domain;

import java.nio.file.Path;
import java.util.Objects;

public sealed interface Toolchain permits ToolchainRecord {

    Type type();

    Provides provides();

    Configuration configuration();

    interface Provides {

        String version();

        String vendor();
    }

    interface Configuration {

        String jdkHome();

        String installDir();
    }

    enum Type {
        JDK, NETBEANS;
    }

    static Builder builder() {
        return new Builder();
    }

    class Builder {

        private Type type;
        private String version;
        private String vendor;
        private Path path;

        public Builder type(final Type type) {
            this.type = type;
            return this;
        }

        public Builder version(final String version) {
            this.version = version;
            return this;
        }

        public Builder vendor(final String vendor) {
            this.vendor = vendor;
            return this;
        }

        public Builder path(final Path path) {
            this.path = path;
            return this;
        }

        public Toolchain build() {
            return new ToolchainRecord(
                    Objects.requireNonNull(type, "The type cannot be NULL"),
                    new ProvidesRecord(Objects.requireNonNull(version, "The version cannot be NULL"), vendor),
                    new ConfigurationRecord(Objects.requireNonNull(path, "The path cannot be NULL")));
        }
    }
}
