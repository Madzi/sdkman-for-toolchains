package madzi.toolchains.repo;

import java.util.Collection;
import madzi.toolchains.domain.Toolchain;

/**
 * Representation of toolchains.xml file.
 *
 * @author de
 */
public interface ToolchainsRepository extends AutoCloseable {

    Collection<Toolchain> list();

    void add(Toolchain toolchain);

    void delete(Toolchain toolchain);
}
