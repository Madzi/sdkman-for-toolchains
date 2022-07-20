package madzi.apps.sdktool.model;

import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JdkInfoTest {

    @Test
    public void testOracleJdk() {
        JdkInfo jdkInfo = JdkInfo.parse(Paths.get("src/test/resources/jdk/8.0.332.fx-librca"));
        Assertions.assertNotNull(jdkInfo);
        Assertions.assertEquals(JdkVendor.LIBERICA, jdkInfo.vendor());
        Assertions.assertTrue(jdkInfo.path().toFile().isDirectory());
        Assertions.assertEquals("1.8", jdkInfo.version().jdkVersion());
        Assertions.assertEquals("8.0.332.fx", jdkInfo.version().version());
    }
}
