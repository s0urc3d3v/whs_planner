package WHS_planner.Core;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import org.omg.CORBA.Object;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by matthewelbing on 16.09.16.
 */
public class JSON {

    FileWriter fileWriter;
    JSONObject object;
    JSONParser parser;

    public JSON (String filepath) throws IOException {
        fileWriter = new FileWriter(filepath);
        object = new JSONObject();
        parser = new JSONParser();
    }
    public void writePair(String key, String data){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key, data);
    }
    public void writeJsonArray(String key, Object data[]){
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < data.length; i++) {
            jsonArray.add(data[i]);
        }

    }
    public void updateFile(){
        try {
            fileWriter.write(object.toJSONString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
