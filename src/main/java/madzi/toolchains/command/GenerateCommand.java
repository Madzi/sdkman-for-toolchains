package madzi.toolchains.command;

import java.util.concurrent.Callable;
import picocli.CommandLine;

@CommandLine.Command(
        name = "generate",
        description = "generates toolchains.xml based on installed JDKs with SDKMAN"
)
public class GenerateCommand implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        // @todo: implements generation of toolchains.xml
        return 0;
    }
}
