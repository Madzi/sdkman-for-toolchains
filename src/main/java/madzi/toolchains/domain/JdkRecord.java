package madzi.toolchains.domain;

import java.nio.file.Path;

/**
 *
 * @author de
 */
public record JdkRecord(JdkVendor vendor, JdkVersion version, Path path) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private JdkVendor vendor;
        private JdkVersion version;
        private Path path;

        private Builder() {
        }

        public Builder vendor(final JdkVendor vendor) {
            this.vendor = vendor;
            return this;
        }

        public Builder version(final JdkVersion version) {
            this.version = version;
            return this;
        }

        public Builder path(final Path path) {
            this.path = path;
            return this;
        }

        public JdkRecord build() {
            return new JdkRecord(vendor, version, path);
        }
    }
}
