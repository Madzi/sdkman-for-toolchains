package madzi.toolchains.command;

import java.io.PrintStream;
import java.util.concurrent.Callable;
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

    public ListCommand(final PrintStream stream) {
        this.stream = stream;
    }

    @Override
    public Integer call() throws Exception {
        // @todo implements list existing toolchains
        return 0;
    }
}
