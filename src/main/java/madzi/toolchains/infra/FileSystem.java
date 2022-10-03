package madzi.toolchains.infra;

import java.nio.file.Path;

/**
 *
 * @author de
 */
public interface FileSystem {

    Path userHome();

    Path mavenToolchains();

    Path sdkmanJdks();
}
