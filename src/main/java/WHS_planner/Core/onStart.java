package WHS_planner.Core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by matthewelbing on 15.11.16.
 */
public class onStart {
    public void config() throws Exception {
        //Check if ipass Creditials exist
        IO io = new IO("src" + File.separator + "main" + File.separator + "resources"+ File.separator + "Core" + File.separator + "util.json");
        File ipassKey = new File("Keys" + File.separator + "ipass.key");
        String username = "";
        char[] password = new char[256]; //YOUR PASSWORD IS NOT LONGER THAN 256
        if (ipassKey.exists() && new BufferedReader(new FileReader(ipassKey)).readLine() != null){
            char[] ipassKeyContents = Files.readAllLines(Paths.get("Keys" + File.separator + "ipass.key")).toString().toCharArray();
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
}
