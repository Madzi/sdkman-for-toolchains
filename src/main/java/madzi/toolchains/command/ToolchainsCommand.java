package madzi.toolchains.command;

import java.io.PrintStream;
import java.util.concurrent.Callable;
import picocli.CommandLine;

@CommandLine.Command(
        name = "toolchains",
        description = "manage your JDKs and Maven toolchains.xml"
)
public class ToolchainsCommand implements Callable<Integer> {

    private final PrintStream stream;

    public ToolchainsCommand(final PrintStream stream) {
        this.stream = stream;
    }

    @Override
    public Integer call() throws Exception {
        stream.println("Toolkit for manipulate with Maven toolchains.xml");
        stream.println();
        return 0;
    }

    public static void main(final String... args) {
        final var stream = System.out;
        final var exitCode = new CommandLine(new ToolchainsCommand(stream))
                .addSubcommand(new GenerateCommand())
                .addSubcommand(new CheckCommand())
                .addSubcommand(new ListCommand())
                // @todo: add subcommands
                .execute(args);
        System.exit(exitCode);
    }
}
