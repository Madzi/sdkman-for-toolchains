package madzi.toolchains.domain;

public record ProvidesRecord(String version, String vendor) implements Toolchain.Provides {
}
