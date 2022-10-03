package madzi.toolchains.command;

import java.util.concurrent.Callable;
import picocli.CommandLine;

@CommandLine.Command(
        name = "help",
        version = "1.0.0",
        helpCommand = true,
        description = "provide help and examples of usages"
)
public class HelpCommand implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println("xxx");
        // @todo: provide correct help
        return 0;
    }
}
