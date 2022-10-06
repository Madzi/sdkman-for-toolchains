package madzi.toolchains.command;

import java.nio.file.Paths;
import java.util.concurrent.Callable;
import madzi.toolchains.repo.ToolchainsRepository;
import madzi.toolchains.service.PrintService;
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

    private final PrintService printService;
    private final ToolchainsRepository toolchainsRepo;

    /**
     * Creates instance of check command.
     *
     * @param printService
     * @param repository
     */
    public CheckCommand(final PrintService printService, final ToolchainsRepository repository) {
        this.printService = printService;
        this.toolchainsRepo = repository;
    }

    @Override
    public Integer call() throws Exception {
        // @todo #1/DEV add check that toolchains.xml contains correct records
        toolchainsRepo.list().forEach(toolchain -> {
            printService.println(new StringBuilder()
                    .append("Checking: ")
                    .append(printService.format(toolchain))
                    .append(check(toolchain.configuration().jdkHome()) ? " ... OK" : " ... FAIL (folder does not exists)")
                    .toString());
        });
        return 0;
    }

    private boolean check(final String path) {
        final var file = Paths.get(path).toFile();
        return file.exists() && file.isDirectory();
    }
}
