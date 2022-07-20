package madzi.apps.sdktool.model;

import java.util.Optional;
import java.util.stream.Stream;

public enum JdkVendor {
    CORRETTO("amazon", "amzn"),
    DRAGONWELL("alibaba", "albba"),
    GLUON("gluon", "gln"),
    GRAALVM("graalvm", "grl"),
    JAVANET("java.net", "open"),
    LIBERICA("liberica", "librca"),
    LIBERICA_NIK("liberica-nik", "nik"),
    MANDREL("mandrel", "mandrel"),
    MICROSOFT("microsoft", "ms"),
    ORACLE("oracle", "oracle"),
    SAPMACHINE("sap.machine", "sapmchn"),
    SEMERU("semeru", "sem"),
    TEMERUN("temerun", "tem"),
    TRAVA("trava", "trava"),
    ZULU("zulu", "zulu");

    JdkVendor(final String vendor, final String suffix) {
        this.vendor = vendor;
        this.suffix = suffix;
    }

    private final String vendor;
    private final String suffix;

    public String vendor() {
        return vendor;
    }

    public String suffix() {
        return suffix;
    }

    public static Optional<JdkVendor> findBySuffix(final String suffix) {
        return Stream.of(values()).filter(item -> item.suffix.equals(suffix)).findFirst();
    }
}
