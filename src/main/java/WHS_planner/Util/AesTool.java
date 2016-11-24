package WHS_planner.Util;

import WHS_planner.Core.ErrorHandler;
import WHS_planner.Core.JSON;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import static org.apache.commons.codec.binary.Hex.*;


/**
 * Created by spam on 11/18/2016.
 */
public class AesTool { //http://stackoverflow.com/questions/23561104/how-to-encrypt-and-decrypt-string-with-my-passphrase-in-java-pc-not-mobile-pla
    private Key AES_KEY;
    private Cipher cipher;
    private String data = "";
    private byte enc[];
    public AesTool(String data, String key) throws Exception { //key has to be a 128 bit key
        AES_KEY = new SecretKeySpec(key.getBytes(), "AES");
        cipher = Cipher.getInstance("AES");
        this.data = data;
    }
    public String encrypt() throws Exception{
        cipher.init(Cipher.ENCRYPT_MODE, AES_KEY);
        byte[] enc = cipher.doFinal(data.getBytes());
        this.enc = enc;
        return new String(enc);
    }

    public String decrypt(){
        try {

            cipher.init(Cipher.DECRYPT_MODE, AES_KEY);
            String dec = new String(cipher.doFinal(enc));
            return dec;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void done(){
        JSON jsonApi = new JSON();
        jsonApi.loadFile("Keys" + File.separator + "keys.key.json");
        char[] hex = encodeHex(AES_KEY.getEncoded());
        jsonApi.writePair("aesKey", String.valueOf(hex));
    }
}
