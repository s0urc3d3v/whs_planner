package WHS_planner.Util;

import WHS_planner.Core.JSON;
import org.json.simple.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Key;
import java.util.Base64;

import static java.lang.System.exit;
import static org.apache.commons.codec.binary.Hex.encodeHex;


/**
 * Created by spam on 11/18/2016.
 */
public class AesTool {
    private SecretKey AES_KEY;
    private Cipher cipher;
    private String data = "";
    private byte enc[];
    public AesTool(String data, SecretKey secretKeySpec) throws Exception { //key has to be a 128 bit key
        cipher = Cipher.getInstance("AES");
        this.data = data;
        this.AES_KEY = secretKeySpec;
    }
    public String encrypt() throws Exception{
        cipher.init(Cipher.ENCRYPT_MODE, AES_KEY); //This is broken
        byte[] enc = cipher.doFinal(data.getBytes());
        this.enc = enc;
        return new String(enc);
    }

    public String decrypt(){
        try {

            cipher.init(Cipher.DECRYPT_MODE, AES_KEY);
            String dec = new String(cipher.doFinal(AES_KEY.getEncoded()));
            return dec;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void done() {
        String encodedKey = Base64.getEncoder().encodeToString(AES_KEY.getEncoded());
        try {
            FileWriter fileWriter = new FileWriter("Keys" + File.separator + "aes.key");
            fileWriter.write(encodedKey);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}