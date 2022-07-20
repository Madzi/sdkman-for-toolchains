package madzi.apps.sdktool.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import madzi.apps.sdktool.model.JdkInfo;

public class MavenToolchainsBuilderService implements ToolchainsBuilderService {

    private static final String IDENT_STR = "    ";
    private static final char NEW_LINE = '\n';
    private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    private static final String TOOLCHAINS_HEADER = """
            <toolchains xmlns="http://maven.apache.org/TOOLCHAINS/1.1.0"
                        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                        xsi:schemaLocation="http://maven.apache.org/TOOLCHAINS/1.1.0 http://maven.apache.org/xsd/toolchains-1.1.0.xsd">
            """;
    private static final String TOOLCHAINS_FOOTER = "</toolchains>";

    @Override
    public String buildToolchains(final Collection<JdkInfo> infos) {
        final Map<String, JdkInfo> map = new HashMap<>();
        infos.forEach(info -> {
            final String key = info.vendor().name() + "-" + info.version().jdkVersion();
            JdkInfo onMap = map.get(key);
            if (onMap != null) {
                if (info.version().compareTo(onMap.version()) > 0) {
                    map.put(key, info);
                }
            } else {
                map.put(key, info);
            }
        });
        final StringBuilder builder = new StringBuilder(XML_HEADER).append(NEW_LINE)
                                                                   .append(TOOLCHAINS_HEADER).append(NEW_LINE);
        map.values().forEach(jdkItem -> {
            builder.append(ident()).append("<toolchain>").append(NEW_LINE)
                   .append(ident(2)).append("<type>").append("jdk").append("</type>").append(NEW_LINE)
                   .append(ident(2)).append("<provides>").append(NEW_LINE)
                   .append(ident(3)).append("<version>").append(jdkItem.version().jdkVersion()).append("</version>").append(NEW_LINE)
                   .append(ident(3)).append("<vendor>").append(jdkItem.vendor().vendor()).append("</vendor>").append(NEW_LINE)
                   .append(ident(2)).append("</provides>").append(NEW_LINE)
                   .append(ident(2)).append("<configuration>").append(NEW_LINE)
                   .append(ident(3)).append("<jdkHome>").append(jdkItem.path()).append("</jdkHome>").append(NEW_LINE)
                   .append(ident(2)).append("</configuration>").append(NEW_LINE)
                   .append(ident()).append("</toolchain>").append(NEW_LINE);
        });
        return builder.append(TOOLCHAINS_FOOTER).append(NEW_LINE).toString();
    }

    private String ident() {
        return IDENT_STR;
    }

    private String ident(final int count) {
        return IDENT_STR.repeat(count);
    }
}
