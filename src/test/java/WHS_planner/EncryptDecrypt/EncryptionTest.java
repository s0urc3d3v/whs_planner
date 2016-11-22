package WHS_planner.EncryptDecrypt;

import WHS_planner.Util.EncryptPasswordWithAes;
import junit.framework.TestCase;

/**
 * Created by spam on 11/21/2016.
 */
public class EncryptionTest extends TestCase {
    public void testEncrypt() {
        EncryptPasswordWithAes encryptPasswordWithAes= new EncryptPasswordWithAes("Abc12345Abc12345");
        String data = encryptPasswordWithAes.encryptPassword("test");
        System.out.println(data);
        String data2 = encryptPasswordWithAes.decryptPassword(data);
        System.out.println(data2);
    }
}
