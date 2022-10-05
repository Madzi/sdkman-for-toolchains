package madzi.toolchains.repo;

import java.util.Collection;
import madzi.toolchains.domain.Toolchain;

/**
 *
 * @author de
 */
public interface SdkmanRepository {

    Collection<Toolchain> list();
}
