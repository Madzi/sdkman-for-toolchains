package madzi.toolchains.repo;

import java.nio.file.Path;
import madzi.toolchains.domain.Toolchain;

/**
 *
 * @author de
 */
public interface SdkmanJdkParser {

    Toolchain parse(Path path);
}
