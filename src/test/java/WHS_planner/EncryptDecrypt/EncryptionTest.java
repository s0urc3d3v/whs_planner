package WHS_planner.EncryptDecrypt;

import WHS_planner.Util.EncryptPasswordWithAes;
import junit.framework.TestCase;

/**
 * Created by spam on 11/21/2016.
 */
public class EncryptionTest extends TestCase {
    public void testEncrypt() {
        String data = EncryptPasswordWithAes.encryptPassword("test", "Abc12345Abc12345");
        EncryptPasswordWithAes.decryptPassword(data, "Abc12345Abc12345");
    }
}
