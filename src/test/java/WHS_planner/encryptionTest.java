package WHS_planner;

import WHS_planner.Util.XorTool;
import junit.framework.TestCase;

import java.util.Random;

/**
 * Created by matthewelbing on 07.12.16.
 */
public class encryptionTest extends TestCase {
    public void testEncryption() throws Exception {
        String username = "Dank_user";
        String password = "DankPassword";
        Random r = new Random();
        String key = String.valueOf(r.nextInt());

        String userEnc = XorTool.encode(username, key);
        String passEnc = XorTool.encode(password, key);

        if (!(XorTool.decode(userEnc, key).equals(username))){
            fail();
        }
        if (!(XorTool.decode(passEnc, key).equals(password))){
            fail();
        }


    }
}
