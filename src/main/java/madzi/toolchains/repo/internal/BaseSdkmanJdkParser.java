package madzi.toolchains.repo.internal;

import java.nio.file.Path;
import madzi.toolchains.domain.Toolchain;
import madzi.toolchains.repo.SdkmanJdkParser;

/**
 *
 * @author de
 */
public class BaseSdkmanJdkParser implements SdkmanJdkParser {

    @Override
    public Toolchain parse(final Path path) {
        if (null == path) {
            throw new IllegalArgumentException("Provided path cannot be NULL");
        }
        final var builder = Toolchain.builder();
        // @todo #5/DEV implements parsing jdk folder name
        return builder.build();
    }
}
