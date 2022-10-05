package madzi.toolchains.repo.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import madzi.toolchains.domain.Toolchain;
import madzi.toolchains.repo.ToolchainsRepository;

public class InMemoryToolchainsRepository implements ToolchainsRepository {

    private final Set<Toolchain> toolchains;

    public InMemoryToolchainsRepository() {
        this.toolchains = new HashSet<>();
    }

    @Override
    public Collection<Toolchain> list() {
        return Collections.unmodifiableCollection(toolchains);
    }

    @Override
    public void add(final Toolchain toolchain) {
        toolchains.add(toolchain);
    }

    @Override
    public void delete(final Toolchain toolchain) {
        toolchains.remove(toolchain);
    }

    @Override
    public void close() throws Exception {
        // nothing to do
    }
}
