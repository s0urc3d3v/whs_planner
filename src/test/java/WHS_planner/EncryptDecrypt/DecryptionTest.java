package WHS_planner.EncryptDecrypt;

import WHS_planner.Util.AesTool;
import junit.framework.TestCase;

/**
 * Created by spam on 11/21/2016.
 */
public class DecryptionTest extends TestCase {
    public void testDecryption(AesTool aesTool){
        try {
            System.out.println(aesTool.decrypt());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
