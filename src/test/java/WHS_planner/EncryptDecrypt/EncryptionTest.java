package WHS_planner.EncryptDecrypt;

import WHS_planner.Util.AesTool;
import junit.framework.TestCase;

/**
 * Created by spam on 11/21/2016.
 */
public class EncryptionTest extends TestCase {
    public void testEncryptAndDecrypt() {
        try {
            AesTool encryptPasswordWithAes = new AesTool("Hello World", "Bar12345Bar12345");
            String data = encryptPasswordWithAes.encrypt();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
