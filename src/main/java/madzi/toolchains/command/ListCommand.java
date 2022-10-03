package madzi.toolchains.command;

import java.util.concurrent.Callable;
import picocli.CommandLine;

@CommandLine.Command(
        name = "list",
        description = "List all toolchains from file toolchains.xml"
)
public class ListCommand implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        // @todo: implements list existing toolchains
        return 0;
    }
}
