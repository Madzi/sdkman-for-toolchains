package madzi.toolchains.command;

import java.io.PrintStream;
import java.util.concurrent.Callable;
import madzi.toolchains.repo.ToolchainsRepository;
import picocli.CommandLine;

/**
 * List commands.
 *
 * @author de
 */
@CommandLine.Command(
        name = "list",
        description = "List all toolchains from file toolchains.xml"
)
public class ListCommand implements Callable<Integer> {

    private final PrintStream stream;
    private final ToolchainsRepository repository;

    public ListCommand(final PrintStream stream, final ToolchainsRepository repository) {
        this.stream = stream;
        this.repository = repository;
    }

    @Override
    public Integer call() throws Exception {
        repository.list().forEach(toolchain -> {
            final var builder = new StringBuffer()
                    .append("=> ")
                    .append(toolchain.type())
                    .append(" - ")
                    .append(toolchain.provides().version());
            if (null != toolchain.provides().vendor()) {
                builder.append(" - ")
                       .append(toolchain.provides().vendor());
            }
            builder.append(" -> ")
                   .append(toolchain.configuration().jdkHome());
            stream.println(builder.toString());
        });
        return 0;
    }
}
