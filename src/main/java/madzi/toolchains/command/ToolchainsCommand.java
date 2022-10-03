package madzi.toolchains.command;

import java.util.concurrent.Callable;
import picocli.CommandLine;

@CommandLine.Command(
        name = "toolchains",
        description = "manage your JDKs and Maven toolchains.xml"
)
public class ToolchainsCommand implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        // @todo add output version and help
        return 0;
    }

    public static void main(final String... args) {
        final var exitCode = new CommandLine(new ToolchainsCommand())
                // @todo add subcommands
                .execute(args);
        System.exit(exitCode);
    }
}
