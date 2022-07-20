package madzi.apps.sdktool.service;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import madzi.apps.sdktool.model.JdkInfo;

public class SdkManJdkLocatorService implements JdkLocatorService {

    private static final String DASH = "-";

    private final Path sdkManJavaFolder;

    public SdkManJdkLocatorService(final Path sdkManJavaFolder) {
        this.sdkManJavaFolder = sdkManJavaFolder;
    }

    @Override
    public Collection<JdkInfo> findInstalledJdk() {
        try {
        return Files.walk(sdkManJavaFolder, 1, FileVisitOption.FOLLOW_LINKS)
                    .filter(this::acceptable)
                    .map(this::detectJdk)
                    .collect(Collectors.toSet());
        } catch (final IOException ioException) {
            return Collections.emptySet();
        }
    }

    private boolean acceptable(final Path path) {
        return path.getFileName().toString().contains(DASH)
                && path.toFile().isDirectory();
    }

    private JdkInfo detectJdk(final Path path) {
        return JdkInfo.parse(path);
    }
}
