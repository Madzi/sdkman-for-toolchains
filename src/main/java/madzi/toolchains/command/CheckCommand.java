package madzi.toolchains.command;

import java.io.PrintStream;
import java.util.concurrent.Callable;
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

    /**
     * Creates instance of check command.
     *
     * @param stream the output stream
     */
    public CheckCommand(final PrintStream stream) {
        this.stream = stream;
    }

    @Override
    public Integer call() throws Exception {
        // @todo add check that toolchains.xml contains correct records
        return 0;
    }
}
