package WHS_planner.Core;

import WHS_planner.Util.MemoryCredentials;
import org.apache.commons.codec.DecoderException;
import org.apache.xpath.operations.Or;

import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyException;

import static org.apache.commons.codec.binary.Hex.*;


/**
 * Created by matthewelbing on 15.11.16.
 */
public class OnStart {
    public void config() throws Exception {
        //Check if ipass Creditials exist
        IO io = new IO("src" + File.separator + "main" + File.separator + "resources"+ File.separator + "Core" + File.separator + "util.json");
        File ipassKey = new File("Keys" + File.separator + "keys.key.json");
        String username = "";
        String password; //YOUR PASSWORD IS NOT LONGER THAN 256

    }
    public void getCreditials(MemoryCredentials memoryCredentials){
        JSON jsonApi = new JSON();
        jsonApi.loadFile("keys" + File.separator + "keys.key.json");
        String username = (String) jsonApi.readPair("ipassUser");
        String password = ((String) jsonApi.readPair("ipassPass")); //TODO: decrypt
        String aesKey = (String) jsonApi.readPair("aesKey");
        byte[] encoded = null;
        try {
            encoded = decodeHex(aesKey.toCharArray());

        } catch (DecoderException e) {
            e.printStackTrace();
            ErrorHandler.handleGenericError("key could not be encoded", new KeyException());
        }


        memoryCredentials.setIpassUsername(username);
        memoryCredentials.setIpassPassword(password);
        memoryCredentials.setAES_KEY(new SecretKeySpec(encoded, "AES"));

    }


}
