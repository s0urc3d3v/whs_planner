package WHS_planner.Core;

import WHS_planner.Util.MemoryCredentials;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by matthewelbing on 15.11.16.
 */
public class OnStart {
    public void config() throws Exception {
        //Check if ipass Creditials exist
        IO io = new IO("src" + File.separator + "main" + File.separator + "resources"+ File.separator + "Core" + File.separator + "util.json");
        File ipassKey = new File("Keys" + File.separator + "keys.key.json");
        String username = "";
        char[] password = new char[256]; //YOUR PASSWORD IS NOT LONGER THAN 256
        if (ipassKey.exists() && new BufferedReader(new FileReader(ipassKey)).readLine() != null){
            char[] ipassKeyContents = Files.readAllLines(Paths.get("Keys" + File.separator + "keys.key.json")).toString().toCharArray();
            for (int i = 0; i < ipassKeyContents.length; i++) {
                if (ipassKeyContents[i] == ':'){ //DON'T USE A COLON IN PASSWORD
                    for (int j = 0; j < i; j++) {
                        username += ipassKeyContents[j];
                    }
                    for (int k = i + 1; k < ipassKey.length(); k--) {
                        password[k] = ipassKeyContents[k];
                    }
                }
            }

        }
        else {
            if (!io.hasRun()){

            }
            else {
                ErrorHandler.handleNoIpassKeyFileError();
            }
        }
    }
    public void getCreditials(MemoryCredentials memoryCredentials){
        JSON jsonApi = new JSON();
        jsonApi.loadFile("keys" + File.separator + "keys.key.json");
        String username = (String) jsonApi.readPair("ipassUser");
        char[] password = ((String) jsonApi.readPair("ipassPass")).toCharArray(); //TODO: security

        memoryCredentials.setIpassUsername(username);
        memoryCredentials.setIpassPassword(password);

    }


}
