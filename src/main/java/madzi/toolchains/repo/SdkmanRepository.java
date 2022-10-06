package madzi.toolchains.repo;

import java.util.Collection;
import madzi.toolchains.domain.JdkRecord;

/**
 *
 * @author de
 */
public interface SdkmanRepository {

    Collection<JdkRecord> list();
}
