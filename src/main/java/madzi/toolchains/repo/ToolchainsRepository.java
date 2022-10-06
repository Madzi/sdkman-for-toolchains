package madzi.toolchains.repo;

import java.util.List;
import madzi.toolchains.domain.Toolchain;

/**
 * Representation of toolchains.xml file.
 *
 * @author de
 */
public interface ToolchainsRepository extends AutoCloseable {

    List<Toolchain> list();

    void add(Toolchain toolchain);

    void delete(Toolchain toolchain);
}
