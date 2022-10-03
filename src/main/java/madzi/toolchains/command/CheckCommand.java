package madzi.toolchains.command;

import java.util.concurrent.Callable;
import picocli.CommandLine;

@CommandLine.Command(
        name = "check",
        description = "Checks that all toolchains into toolchains.xml contains correct paths"
)
public class CheckCommand implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        // @todo: add check that toolchains.xml contains correct records
        return 0;
    }
}
