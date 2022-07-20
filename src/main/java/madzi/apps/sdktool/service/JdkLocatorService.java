package madzi.apps.sdktool.service;

import java.util.Collection;

import madzi.apps.sdktool.model.JdkInfo;

public interface JdkLocatorService {

    Collection<JdkInfo> findInstalledJdk();
}
