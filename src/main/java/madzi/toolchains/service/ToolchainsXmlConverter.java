package madzi.toolchains.service;

import madzi.toolchains.domain.Toolchain;

public interface ToolchainsXmlConverter {

    Iterable<Toolchain> fromXml(String xml);

    String toXml(Iterable<Toolchain> toolchains);
}
