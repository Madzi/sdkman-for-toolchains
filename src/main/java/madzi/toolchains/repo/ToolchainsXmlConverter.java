package madzi.toolchains.repo;

import madzi.toolchains.domain.Toolchain;

public interface ToolchainsXmlConverter {

    Iterable<Toolchain> fromXml(String xml);

    String toXml(Iterable<Toolchain> toolchains);
}
