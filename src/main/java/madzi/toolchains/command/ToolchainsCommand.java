package madzi.toolchains.command;

import java.io.PrintStream;
import java.util.concurrent.Callable;
import madzi.toolchains.infra.Environment;
import madzi.toolchains.infra.internal.LocalFileSystem;
import madzi.toolchains.repo.SdkmanJdkParser;
import madzi.toolchains.repo.internal.BaseSdkmanJdkParser;
import madzi.toolchains.repo.internal.FileToolchainsLoader;
import madzi.toolchains.repo.internal.LocalSdkmanRepository;
import madzi.toolchains.repo.internal.LocalToolchainsRepository;
import madzi.toolchains.repo.internal.StaxToolchainsXmlConverter;
import picocli.CommandLine;

/**
 * Main command, application entrypoint.
 *
 * @author de
 */
@CommandLine.Command(
        name = "toolchains",
        description = "manage your JDKs and Maven toolchains.xml"
)
public class ToolchainsCommand implements Callable<Integer> {

    private final PrintStream stream;

    /**
     * Creates instance of toolchains command.
     *
     * @param stream the output stream
     */
    public ToolchainsCommand(final PrintStream stream) {
        this.stream = stream;
    }

    @Override
    public Integer call() throws Exception {
        stream.println("Toolkit for manipulate with Maven toolchains.xml");
        stream.println();
        return 0;
    }

    /**
     * Application entry point.
     *
     * @param args command line arguments
     */
    public static void main(final String... args) {
        final var stream = System.out;
        final var environment = Environment.builder().build();
        final var fileSystem = new LocalFileSystem(environment);
        final var sdkmanRepo = new LocalSdkmanRepository(fileSystem.sdkmanJdks(), new BaseSdkmanJdkParser());
        final var toolchainsRepo = new LocalToolchainsRepository(new FileToolchainsLoader(fileSystem.mavenToolchains(), new StaxToolchainsXmlConverter()));
        final var exitCode = new CommandLine(new ToolchainsCommand(stream))
                .addSubcommand(new GenerateCommand(stream, toolchainsRepo, sdkmanRepo))
                .addSubcommand(new CheckCommand(stream, toolchainsRepo))
                .addSubcommand(new ListCommand(stream, toolchainsRepo))
                .execute(args);
        System.exit(exitCode);
    }
}
