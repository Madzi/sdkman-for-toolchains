package madzi.toolchains.domain;

import java.util.stream.Stream;

/**
 *
 * @author de
 */
public enum JdkVendor {
    CORRETTO("Corretto", "amzn"),
    DRAGON_WELL("Dragonwell", "albba"),
    GLUON("Gluon", "gln"),
    GRAAL_VM("GraalVM", "grl"),
    JAVA_NET("Java.net", "open"),
    LIBERICA("Liberica", "librca"),
    LIBERICA_NIK("LibericaNIK", "nik"),
    MANDREL("Mandrel", "mandrel"),
    MICROSOFT("Microsoft", "ms"),
    ORACLE("Oracle", "oracle"),
    SAP_MACHINE("SapMachine", "sapmchn"),
    SEMERU("Semeru", "sem"),
    TEMURIN("Temurin", "tem"),
    TRAVA("Trava", "trava"),
    ZULU("Zulu", "zulu");

    private final String vendor;
    private final String identifier;

    JdkVendor(final String vendor, final String identifier) {
        this.vendor = vendor;
        this.identifier = identifier;
    }

    public String vendor() {
        return vendor;
    }

    public String identifier() {
        return identifier;
    }

    public static JdkVendor parse(final String suffix) {
        if (null == suffix) {
            throw new IllegalArgumentException("The suffix cannot be NULL");
        }
        return Stream.of(values())
                     .filter(jdk -> jdk.identifier.equalsIgnoreCase(suffix))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("Unable to parse unknown identifier: " + suffix));
    }
}
