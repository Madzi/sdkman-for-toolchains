package madzi.toolchains.command;

import java.util.concurrent.Callable;
import madzi.toolchains.domain.JdkVersion;
import madzi.toolchains.domain.Toolchain;
import madzi.toolchains.repo.SdkmanRepository;
import madzi.toolchains.repo.ToolchainsRepository;
import madzi.toolchains.service.PrintService;
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

    private final PrintService printService;
    private final SdkmanRepository sdkmanRepo;
    private final ToolchainsRepository toolchainsRepo;

    public GenerateCommand(final PrintService printService, final ToolchainsRepository toolchainsRepo, final SdkmanRepository sdkmanRepo) {
        this.printService = printService;
        this.sdkmanRepo = sdkmanRepo;
        this.toolchainsRepo = toolchainsRepo;
    }

    @Override
    public Integer call() throws Exception {
        printService.println("Generating ...");
        // @todo #1/DEV the id for toolchains.xml should be able to defined in external config
        sdkmanRepo.list().forEach(jdkRecord -> {
            printService.println("Detected: " + printService.format(jdkRecord));
            toolchainsRepo.add(Toolchain.builder()
                    .type(Toolchain.Type.JDK)
                    .version(String.valueOf(jdkRecord.version().major()))
                    .vendor(jdkRecord.vendor().name().toLowerCase().replaceAll("_", "-"))
                    .path(jdkRecord.path())
                    .build());
        });
        // @todo #1/DEV generate list for toolchains.xml
        return 0;
    }

    private String printVersion(final JdkVersion version) {
        if (version.major() > 8) {
            return String.valueOf(version.major());
        } else {
            return "1." + String.valueOf(version.major());
        }
    }
}
