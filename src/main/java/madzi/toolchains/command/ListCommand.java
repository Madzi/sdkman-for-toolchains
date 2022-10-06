package madzi.toolchains.command;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import madzi.toolchains.domain.Toolchain;
import madzi.toolchains.repo.ToolchainsRepository;
import madzi.toolchains.service.PrintService;
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

    private final PrintService printService;
    private final ToolchainsRepository toolchainsRepo;

    public ListCommand(final PrintService printService, final ToolchainsRepository repository) {
        this.printService = printService;
        this.toolchainsRepo = repository;
    }

    @Override
    public Integer call() throws Exception {
        // @todo #1/DEV sort toolchains for output
        toolchainsRepo.list().forEach(item -> printService.println(printService.format(item)));
        return 0;
    }
}
