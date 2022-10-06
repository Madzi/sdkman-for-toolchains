package madzi.toolchains.service;

import madzi.toolchains.domain.JdkRecord;
import madzi.toolchains.domain.Toolchain;

/**
 *
 * @author de
 */
public interface PrintService {

    void print(String text);

    void println(String text);

    String format(JdkRecord jdk);

    String format(Toolchain toolchain);
}
