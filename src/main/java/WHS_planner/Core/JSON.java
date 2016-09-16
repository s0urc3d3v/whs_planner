package WHS_planner.Core;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by matthewelbing on 16.09.16.
 */
public class JSON {

    private FileWriter fileWriter;
    private JSONObject object;
    private JSONParser parser;
    private String filePath = "add_path";

    public JSON () throws IOException {
        fileWriter = new FileWriter(filePath);
        try {
            object =  (JSONObject) parser.parse(new FileReader(filePath));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        object = new JSONObject();
        parser = new JSONParser();
    }

    public void writePair(String key, String data){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key, data);
    }

    public void writeArray(String key, Object data[]){
        key = "@" + key;
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < data.length; i++) {
            jsonArray.add(data[i]);
        }
        updateFile();
    }

    public void updateFile(){
        try {
            fileWriter.write(object.toJSONString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object readObject(String key) {
        return object.get(key);
    }

    public Object[] readArray(String key) {
        JSONArray array = (JSONArray) object.get(key);
        //Turn the JSONArray into an object array
        int length = array.size();
        Object objArray[] = new Object[length];
        for(int i = 0; i < length; i++) {
            objArray[i] = array.get(i);
        }
        return objArray;
    }

    public Object readPair(String key) {
        if(key.length() >= 1 && key.substring(0, 1).equals("@")) {
            //It is an array and needs to be parsed as one.
            return readArray(key);
        }
        return readObject(key);
    }
}
