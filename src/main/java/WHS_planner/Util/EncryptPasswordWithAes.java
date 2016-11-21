package WHS_planner.Util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * Created by spam on 11/18/2016.
 */
public class EncryptPasswordWithAes { //http://stackoverflow.com/questions/23561104/how-to-encrypt-and-decrypt-string-with-my-passphrase-in-java-pc-not-mobile-plat
    public static String encryptPassword(String password, String key){

        try {
            Key desKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher eCypher = Cipher.getInstance("AES");
            eCypher.init(Cipher.ENCRYPT_MODE, desKey);
            byte[] utf8ByteArr = password.getBytes();

            byte[] encryptedData = eCypher.doFinal(utf8ByteArr);

            return new sun.misc.BASE64Encoder().encode(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decryptPassword(String password, String key){

        try {
            Cipher dCipher = Cipher.getInstance("AES");
            Key desKey = new SecretKeySpec(key.getBytes(), "AES");
            dCipher.init(Cipher.DECRYPT_MODE, desKey);
            byte[] data = new sun.misc.BASE64Decoder().decodeBuffer(password);

            byte[] utf8ByteArr = dCipher.doFinal();
            return new String(utf8ByteArr, "UTF8");
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
