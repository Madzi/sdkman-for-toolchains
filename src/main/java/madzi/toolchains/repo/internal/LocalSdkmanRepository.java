package madzi.toolchains.repo.internal;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import madzi.toolchains.domain.Toolchain;
import madzi.toolchains.repo.SdkmanJdkParser;
import madzi.toolchains.repo.SdkmanRepository;

/**
 *
 * @author de
 */
public class LocalSdkmanRepository implements SdkmanRepository {

    private final Path path;
    private final SdkmanJdkParser parser;

    public LocalSdkmanRepository(final Path path, final SdkmanJdkParser parser) {
        this.path = path;
        this.parser = parser;
    }

    @Override
    public Collection<Toolchain> list() {
        final var set = new HashSet<Toolchain>();
        try {
            Files.walk(path, 1, FileVisitOption.FOLLOW_LINKS)
                 .filter(this::accept)
                 .forEach(parser::parse);
        } catch (final IOException exception) {
            throw new RuntimeException("Unable to take list of installed JDKs");
        }
        return set;
    }

    private boolean accept(final Path path) {
        if (null == path) {
            return false;
        }
        final var file = path.toFile();
        return file.exists() && file.isDirectory() && file.getName().contains("-");
    }
}
