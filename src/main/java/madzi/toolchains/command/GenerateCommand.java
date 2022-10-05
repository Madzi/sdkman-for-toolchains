package madzi.toolchains.command;

import java.io.PrintStream;
import java.util.concurrent.Callable;
import madzi.toolchains.repo.SdkmanRepository;
import madzi.toolchains.repo.ToolchainsRepository;
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

    private final ToolchainsRepository toolchainsRepo;
    private final SdkmanRepository sdkmanRepo;
    private final PrintStream stream;

    public GenerateCommand(final PrintStream stream, final ToolchainsRepository toolchainsRepo, final SdkmanRepository sdkmanRepo) {
        this.stream = stream;
        this.toolchainsRepo = toolchainsRepo;
        this.sdkmanRepo = sdkmanRepo;
    }

    @Override
    public Integer call() throws Exception {
        sdkmanRepo.list().forEach(toolchainsRepo::add);
        return 0;
    }
}
