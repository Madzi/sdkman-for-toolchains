package madzi.toolchains.infra.internal;

import java.nio.file.Path;
import java.nio.file.Paths;
import madzi.toolchains.infra.Environment;
import madzi.toolchains.infra.FileSystem;

/**
 *
 * @author de
 */
public class LocalFileSystem implements FileSystem {

    private final Environment environment;

    public LocalFileSystem(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Path userHome() {
        return Paths.get(environment.getRequiredProperty(SysVariable.USER_HOME));
    }

    @Override
    public Path mavenToolchains() {
        return userHome().resolve(".m2").resolve("toolchains.xml");
    }

    @Override
    public Path sdkmanJdks() {
        return userHome().resolve(".sdkman").resolve("candidates").resolve("java");
    }

    // @todo #1/DEV can be added applicationFolder & currentFolder

    private enum SysVariable implements Environment.Variable {
        USER_HOME("user.home");

        private final String key;

        SysVariable(final String key) {
            this.key = key;
        }

        public String key() {
            return key;
        }
    }
}
