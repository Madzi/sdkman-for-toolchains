package madzi.toolchains.repo.internal;

import java.util.List;
import madzi.toolchains.domain.Toolchain;
import madzi.toolchains.repo.ToolchainsLoader;
import madzi.toolchains.repo.ToolchainsRepository;

/**
 * The implementation of ToolchainsRepository for local ~/.m2/toolchains.xml file.
 *
 * @author de
 */
public class LocalToolchainsRepository implements ToolchainsRepository {

    private final ToolchainsRepository repository;
    private final ToolchainsLoader loader;

    public LocalToolchainsRepository(final ToolchainsLoader loader) {
        this.repository = new InMemoryToolchainsRepository();
        this.loader = loader;
        loader.load().forEach(repository::add);
    }

    @Override
    public List<Toolchain> list() {
        return repository.list();
    }

    @Override
    public void add(final Toolchain toolchain) {
        repository.add(toolchain);
        loader.save(repository.list());
    }

    @Override
    public void delete(final Toolchain toolchain) {
        repository.delete(toolchain);
        loader.save(repository.list());
    }

    @Override
    public void close() throws Exception {
        loader.save(repository.list());
    }
}
