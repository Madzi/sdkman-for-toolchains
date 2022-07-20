package madzi.apps.sdktool.model;

import java.util.Objects;

public class JdkVersion implements Comparable<JdkVersion> {

    private static final char DOT = '.';

    private final Integer major;
    private final Integer minor;
    private final Integer patch;
    private final Integer subpatch;
    private final String suffix;

    private JdkVersion(final Builder builder) {
        this.major = builder.major;
        this.minor = builder.minor;
        this.patch = builder.patch;
        this.subpatch = builder.subpatch;
        this.suffix = builder.suffix;
    }

    public String jdkVersion() {
        if (major < 9) {
            return "1." + major;
        } else {
            return String.valueOf(major);
        }
    }

    public String version() {
        final StringBuilder builder = new StringBuilder();
        if (major != null) {
            builder.append(major);
        }
        append(builder, minor);
        append(builder, patch);
        append(builder, subpatch);
        append(builder, suffix);
        return builder.toString();
    }

    private void append(final StringBuilder builder, final Integer intValue) {
        if (intValue != null) {
            builder.append(DOT).append(intValue);
        }
    }

    private void append(final StringBuilder builder, final String strValue) {
        if (strValue != null) {
            builder.append(DOT).append(strValue);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(major, minor, patch, subpatch, suffix);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof JdkVersion that) {
            return Objects.equals(this.major, that.major)
                    && Objects.equals(this.minor, that.minor)
                    && Objects.equals(this.patch, that.patch)
                    && Objects.equals(this.subpatch, that.subpatch)
                    && Objects.equals(this.suffix, that.suffix);
        }
        return false;
    }

    @Override
    public String toString() {
        return "JDKVersion[major=" + major + ", minor=" + minor + ", patch=" + patch + ", subpatch=" + subpatch + ", suffix=" + suffix + "]";
    }

    @Override
    public int compareTo(final JdkVersion that) {
        if (compareInt(this.major, that.major) == 0) {
            if (compareInt(this.minor, that.minor) == 0) {
                if (compareInt(this.patch, that.patch) == 0) {
                    return compareInt(this.subpatch, that.subpatch);
                }
                return compareInt(this.patch, that.patch);
            }
            return compareInt(this.minor, that.minor);
        }
        return compareInt(this.major, that.major);
    }

    public static int compareInt(final Integer i1, final Integer i2) {
        if (i1 == null) {
            if (i2 == null) {
                return 0;
            }
            return 1;
        } else {
            if (i2 == null) {
                return -1;
            }
            return Integer.compare(i1, i2);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Integer major;
        private Integer minor;
        private Integer patch;
        private Integer subpatch;
        private String suffix;

        private Builder() {
        }

        public Builder major(final Integer major) {
            this.major = major;
            return this;
        }

        public Builder minor(final Integer minor) {
            this.minor = minor;
            return this;
        }

        public Builder patch(final Integer patch) {
            this.patch = patch;
            return this;
        }

        public Builder subpatch(final Integer subpatch) {
            this.subpatch = subpatch;
            return this;
        }

        public Builder suffix(final String suffix) {
            this.suffix = suffix;
            return this;
        }

        public JdkVersion build() {
            return new JdkVersion(this);
        }
    }

    @FunctionalInterface
    public static interface Parser {

        JdkVersion parse(String versionStr);
    }
}
