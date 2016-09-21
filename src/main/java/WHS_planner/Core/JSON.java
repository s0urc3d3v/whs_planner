package WHS_planner.Core;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by matthewelbing on 16.09.16.
 */
public class JSON {

    private FileWriter fileWriter;
    private JSONObject object;
    private JSONParser parser;
    private String filePath = "";

    public JSON () throws IOException {
       parser = new JSONParser();
    }

    /**
     @Param filePath
     @return If the file was successfully loaded
     */
    public boolean loadFile(String filePath) {
        this.filePath = filePath;
        try {
            Object raw = null;
            try {
                raw = parser.parse(new FileReader(filePath));
                fileWriter = new FileWriter(filePath);
            } catch (IOException e) {
                ErrorHandler.HandleIOError(e);
                return false;
            }
            object = (JSONObject) raw;
        } catch (ParseException e) {
            ErrorHandler.handleGenericError("Parser Error with JSON File loading", e);
            return false;
        }
        parser = new JSONParser();
        return true;
    }

    public void writePair(String key, String data){
        object.put(key, data);
    }

    public void writeArray(String key, Object data[]){
        key = "@" + key;
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < data.length; i++) {
            jsonArray.add(data[i]);
        }
    }

    /**
     * Once the file is unloaded it cannot be read from or written from until a new file is loaded with loadFile.
     */
    public void unloadFile(){
        try {
            fileWriter.write(object.toJSONString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            ErrorHandler.HandleIOError(e);
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

    public HashMap<Object, Object> dropJsonDb(){
        HashMap<Object, Object> JsonData = new HashMap<Object, Object>();
        try {
            Object parseObject = parser.parse(new FileReader(filePath));
            JSONObject jsonObject = (JSONObject) parseObject;
            Object keySet = jsonObject.keySet();
            Object dataSet = jsonObject.entrySet();
            JsonData.put(keySet, dataSet);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return JsonData;
    }

}
