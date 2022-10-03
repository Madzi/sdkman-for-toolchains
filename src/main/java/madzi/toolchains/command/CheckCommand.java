package madzi.toolchains.command;

import java.util.concurrent.Callable;

public class CheckCommand implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        // @todo: add check that toolchains.xml contains correct records
        return 0;
    }
}
