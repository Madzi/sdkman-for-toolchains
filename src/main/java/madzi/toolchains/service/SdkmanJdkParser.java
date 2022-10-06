package madzi.toolchains.service;

import java.nio.file.Path;
import madzi.toolchains.domain.JdkRecord;

/**
 *
 * @author de
 */
public interface SdkmanJdkParser {

    JdkRecord parse(Path path);
}
