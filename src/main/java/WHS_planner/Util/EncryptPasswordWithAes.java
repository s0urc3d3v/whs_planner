package WHS_planner.Util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * Created by spam on 11/18/2016.
 */
public class EncryptPasswordWithAes { //http://stackoverflow.com/questions/23561104/how-to-encrypt-and-decrypt-string-with-my-passphrase-in-java-pc-not-mobile-plat
    private  Key AES_KEY = null;

    /*
   TODO This is bugged becuase it gets called multiple times somehow (probably the interpretor not code), fix needed
     */
    public EncryptPasswordWithAes(String key) {
        AES_KEY = new SecretKeySpec(key.getBytes(), "AES"); //THIS NEEDS TO BE SAVED AT SOME POINT...

    }

    public  String encryptPassword(String password){

        try {
            Cipher encryptionCipher = Cipher.getInstance("AES");
            encryptionCipher.init(Cipher.ENCRYPT_MODE, AES_KEY);
            byte[] encryptedData = encryptionCipher.doFinal(password.getBytes());
            return new String(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decryptPassword(String password){ //This is the AES key from above

        try {
            Cipher decryptionCipher = Cipher.getInstance("AES");
            decryptionCipher.init(Cipher.DECRYPT_MODE, AES_KEY);
            byte[] decryptedData = decryptionCipher.doFinal(password.getBytes());
            return new String(decryptedData);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
