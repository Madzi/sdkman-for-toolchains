package madzi.toolchains.service.internal;

import java.io.PrintStream;
import java.util.List;
import madzi.toolchains.domain.JdkRecord;
import madzi.toolchains.domain.Toolchain;
import madzi.toolchains.service.PrintService;

/**
 *
 * @author de
 */
public class ConsolePrintService implements PrintService {

    private final PrintStream stream;

    public ConsolePrintService(final PrintStream stream) {
        this.stream = stream;
    }

    @Override
    public void print(final String text) {
        stream.print(text);
    }

    @Override
    public void println(final String text) {
        stream.println(text);
    }

    @Override
    public String format(JdkRecord jdk) {
        return new StringBuilder()
                .append("JDK (")
                .append(version(jdk.version().major()))
                .append(", ")
                .append(jdk.vendor().name().toLowerCase().replaceAll("_", "-"))
                .append(") -> ")
                .append(jdk.path())
                .toString();
    }

    @Override
    public String format(Toolchain toolchain) {
        final var builder = new StringBuilder()
                .append(toolchain.type())
                .append(" (")
                .append(toolchain.provides().version());
        if (null != toolchain.provides().vendor()) {
            builder.append(", ")
                    .append(toolchain.provides()
                    .vendor());
        }
        builder.append(") -> ")
                .append(toolchain.configuration().jdkHome());
        return builder.toString();
    }

    private String version(final String version) {
        if (List.of("4", "5", "6", "7", "8").contains(version)) {
            return "1." + version;
        } else {
            return version;
        }
    }

    private String version(final int version) {
        if (version > 8) {
            return String.valueOf(version);
        } else {
            return "1." + String.valueOf(version);
        }
    }
}
