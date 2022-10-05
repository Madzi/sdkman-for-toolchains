package madzi.toolchains.repo;

import madzi.toolchains.domain.Toolchain;

public interface ToolchainsLoader {

    Iterable<Toolchain> load();

    void save(Iterable<Toolchain> items);
}
