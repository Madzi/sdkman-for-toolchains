package madzi.apps.sdktool.model;

import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

public class JdkInfo {

    private final JdkVendor vendor;
    private final JdkVersion version;
    private final Path path;

    private JdkInfo(final JdkVendor vendor, final JdkVersion version, final Path path) {
        this.vendor = vendor;
        this.version = version;
        this.path = path;
    }

    public JdkVendor vendor() {
        return vendor;
    }

    public JdkVersion version() {
        return version;
    }

    public Path path() {
        return path;
    }

    public static JdkInfo parse(final Path path) {
        final String versionStr = path.getFileName().toString();
        final int dashIdx = Objects.requireNonNull(versionStr, "JDK version string cannot be NULL").indexOf("-");
        if (dashIdx <= 0) {
            throw new IllegalArgumentException("Expected version string in format '<version>-<vendor>'");
        }
        final String verStr = versionStr.substring(0, dashIdx);
        final String venStr = versionStr.substring(dashIdx + 1);
        final Optional<JdkVendor> vendorOpt = JdkVendor.findBySuffix(venStr);
        if (vendorOpt.isEmpty()) {
            throw new IllegalArgumentException("Unknown JDK Vendor '" + venStr + "'");
        }
        final JdkVendor vendor = vendorOpt.get();
        return new JdkInfo(vendor, versionParse(vendor, verStr), path);
    }

    private static JdkVersion versionParse(final JdkVendor vendor, final String versionStr) {
        return switch (vendor) {
            case LIBERICA -> baseParse(versionStr);
            case LIBERICA_NIK -> nikParse(versionStr);
            case GRAALVM -> graalParse(versionStr);
            default -> throw new IllegalArgumentException("Unexpected value: " + vendor);
        };
    }

    private static JdkVersion nikParse(final String versionStr) {
        final String[] verParts = versionStr.split("\\.");
        return JdkVersion.builder()
                .major(Integer.valueOf(verParts[2].substring(1)))
                .minor(Integer.valueOf(verParts[0]))
                .patch(Integer.valueOf(verParts[1]))
                .build();
    }

    private static JdkVersion graalParse(final String versionStr) {
        final String[] verParts = versionStr.split("\\.");
        return JdkVersion.builder()
                .major(Integer.valueOf(verParts[3].substring(1)))
                .minor(Integer.valueOf(verParts[0]))
                .patch(Integer.valueOf(verParts[1]))
                .subpatch(Integer.valueOf(verParts[2]))
                .build();
    }

    private static JdkVersion baseParse(final String versionStr) {
        final JdkVersion.Builder builder = JdkVersion.builder();
        final String[] verParts = versionStr.split("\\.");
        if (verParts.length < 1) {
            throw new IllegalArgumentException("The version string should contains atleast one number");
        }
        builder.major(Integer.valueOf(verParts[0]));
        if (verParts.length > 1) {
            if (isNumber(verParts[1])) {
                builder.minor(Integer.valueOf(verParts[1]));
            } else {
                builder.suffix(verParts[1]);
            }
        }
        if (verParts.length > 2) {
            if (isNumber(verParts[2])) {
                builder.patch(Integer.valueOf(verParts[2]));
            } else {
                builder.suffix(verParts[2]);
            }
        }
        if (verParts.length > 3) {
            if (isNumber(verParts[3])) {
                builder.subpatch(Integer.valueOf(verParts[3]));
            } else {
                builder.suffix(verParts[3]);
            }
        }
        if (verParts.length > 4) {
            builder.suffix(verParts[4]);
        }
        return builder.build();
    }

    private static boolean isNumber(final String str) {
        if (str == null) {
            return false;
        }
        try {
            double num = Double.parseDouble(str);
        } catch (final NumberFormatException numberFormatException) {
            return false;
        }
        return true;
    }
}
