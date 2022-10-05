package madzi.toolchains.command;

import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import madzi.toolchains.repo.ToolchainsRepository;
import picocli.CommandLine;

/**
 * Check command.
 *
 * @author de
 */
@CommandLine.Command(
        name = "check",
        description = "Checks that all toolchains into toolchains.xml contains correct paths"
)
public class CheckCommand implements Callable<Integer> {

    private final PrintStream stream;
    private final ToolchainsRepository repository;

    /**
     * Creates instance of check command.
     *
     * @param stream the output stream
     */
    public CheckCommand(final PrintStream stream, final ToolchainsRepository repository) {
        this.stream = stream;
        this.repository = repository;
    }

    @Override
    public Integer call() throws Exception {
        // @todo #1/DEV add check that toolchains.xml contains correct records
        repository.list().forEach(toolchain -> {
            final var builder = new StringBuilder();
            builder.append("Checking: ")
                   .append(toolchain.type())
                   .append(" (")
                   .append(toolchain.provides().version());
            if (null != toolchain.provides().vendor()) {
                builder.append(", ")
                       .append(toolchain.provides().vendor());
            }
            builder.append(") ")
                   .append(toolchain.configuration().jdkHome());
            if (check(toolchain.configuration().jdkHome())) {
                builder.append(" ... OK");
            } else {
                builder.append(" ... FAIL (folder does not exists)");
            }
            stream.println(builder.toString());
        });
        return 0;
    }

    private boolean check(final String path) {
        final var file = Paths.get(path).toFile();
        return file.exists() && file.isDirectory();
    }
}
