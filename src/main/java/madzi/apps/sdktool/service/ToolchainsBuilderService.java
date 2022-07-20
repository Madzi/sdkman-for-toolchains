package madzi.apps.sdktool.service;

import java.util.Collection;

import madzi.apps.sdktool.model.JdkInfo;

public interface ToolchainsBuilderService {

    String buildToolchains(Collection<JdkInfo> infos);
}
