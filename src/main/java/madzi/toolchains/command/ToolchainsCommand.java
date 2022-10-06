package madzi.toolchains.command;

import java.util.concurrent.Callable;
import madzi.toolchains.infra.Environment;
import madzi.toolchains.infra.internal.LocalFileSystem;
import madzi.toolchains.service.internal.BaseSdkmanJdkParser;
import madzi.toolchains.repo.internal.FileToolchainsLoader;
import madzi.toolchains.repo.internal.LocalSdkmanRepository;
import madzi.toolchains.repo.internal.LocalToolchainsRepository;
import madzi.toolchains.service.PrintService;
import madzi.toolchains.service.internal.ConsolePrintService;
import madzi.toolchains.service.internal.StaxToolchainsXmlConverter;
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

    private final PrintService printService;

    /**
     * Creates instance of toolchains command.
     *
     * @param printService the printing service
     */
    public ToolchainsCommand(final PrintService printService) {
        this.printService = printService;
    }

    @Override
    public Integer call() throws Exception {
        printService.println("Toolkit for manipulate with Maven toolchains.xml");
        printService.println("");
        return 0;
    }

    /**
     * Application entry point.
     *
     * @param args command line arguments
     */
    public static void main(final String... args) {
        final var printService = new ConsolePrintService(System.out);
        final var environment = Environment.builder().build();
        final var fileSystem = new LocalFileSystem(environment);
        final var sdkmanRepo = new LocalSdkmanRepository(fileSystem.sdkmanJdks(), new BaseSdkmanJdkParser());
        final var toolchainsRepo = new LocalToolchainsRepository(new FileToolchainsLoader(fileSystem.mavenToolchains(), new StaxToolchainsXmlConverter()));
        final var exitCode = new CommandLine(new ToolchainsCommand(printService))
                .addSubcommand(new GenerateCommand(printService, toolchainsRepo, sdkmanRepo))
                .addSubcommand(new CheckCommand(printService, toolchainsRepo))
                .addSubcommand(new ListCommand(printService, toolchainsRepo))
                .execute(args);
        System.exit(exitCode);
    }
}
