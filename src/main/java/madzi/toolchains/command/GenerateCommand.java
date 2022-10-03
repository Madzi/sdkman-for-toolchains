package madzi.toolchains.command;

import java.io.PrintStream;
import java.util.concurrent.Callable;
import picocli.CommandLine;

/**
 * Generate command.
 *
 * @author de
 */
@CommandLine.Command(
        name = "generate",
        description = "generates toolchains.xml based on installed JDKs with SDKMAN"
)
public class GenerateCommand implements Callable<Integer> {

    private final PrintStream stream;

    public GenerateCommand(final PrintStream stream) {
        this.stream = stream;
    }

    @Override
    public Integer call() throws Exception {
        // @todo implements generation of toolchains.xml
        return 0;
    }
}
