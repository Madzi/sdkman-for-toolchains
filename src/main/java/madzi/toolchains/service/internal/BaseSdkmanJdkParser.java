package madzi.toolchains.service.internal;

import java.nio.file.Path;
import java.util.Optional;
import madzi.toolchains.domain.JdkRecord;
import madzi.toolchains.domain.JdkVendor;
import madzi.toolchains.domain.JdkVersion;
import madzi.toolchains.service.SdkmanJdkParser;

/**
 *
 * @author de
 */
public class BaseSdkmanJdkParser implements SdkmanJdkParser {

    private static final String DOT = ".";
    private static final String RELEASE_PREFIX = "r";

    @Override
    public JdkRecord parse(final Path path) {
        if (null == path) {
            throw new IllegalArgumentException("Provided path cannot be NULL");
        }
        final var name = path.toFile().getName();
        final var parts = name.split("-");
        return JdkRecord.builder()
                        .vendor(JdkVendor.parse(parts[1]))
                        .version(parseVersion(parts[0]))
                        .path(path)
                        .build();
    }

    private JdkVersion parseVersion(final String input) {
        return parseVersion(input, null);
    }

    private JdkVersion parseVersion(final String input, final String suffix) {
        if (!input.contains(DOT)) {
            final var version = parseNum(input);
            return new JdkVersion(version.orElseThrow(() -> new IllegalArgumentException("Cannot detect major version: " + input)), input, suffix);
        }
        final var lastIdx = input.lastIndexOf(DOT);
        final var last = input.substring(lastIdx + 1);
        final var lastN = parseNum(last);
        if (lastN.isPresent()) {
            final var firstIdx = input.indexOf(DOT);
            final var first = input.substring(0, firstIdx);
            final var firstN = parseNum(first);
            if (firstN.isPresent()) {
                return new JdkVersion(firstN.get(), input, suffix);
            } else {
                throw new IllegalStateException("Unable to process version: " + input);
            }
        } else {
            if (last.startsWith(RELEASE_PREFIX)) {
                final var version = last.substring(1);
                final var versionN = parseNum(version);
                if (versionN.isPresent()) {
                    return new JdkVersion(versionN.get(), input.substring(0, lastIdx), suffix);
                } else {
                    throw new IllegalStateException("Unable to process version: " + input);
                }
            }
            return parseVersion(input.substring(0, lastIdx), last);
        }
    }

    private Optional<Integer> parseNum(final String str) {
        try {
            return Optional.of(Integer.valueOf(str));
        } catch (final NumberFormatException exception) {
            return Optional.empty();
        }
    }
}
