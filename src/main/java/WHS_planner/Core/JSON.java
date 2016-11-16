package WHS_planner.Core;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by matthewelbing on 16.09.16.
 */
public class JSON {

    private FileWriter fileWriter;
    private JSONObject object = new JSONObject();
    private JSONParser parser;
    String filePath;

    public JSON() {
        parser = new JSONParser();
    }

    public String getFilePath() {
        return filePath;
    }

    /**
     * @return If the file was successfully loaded
     * @Param filePath
     */
    public boolean loadFile(String filePath) {
        this.filePath = filePath;

        try {
            try {
                object = (JSONObject) parser.parse(new FileReader(filePath));
                fileWriter = new FileWriter(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (ParseException e) {
            object = new JSONObject();
            try {
                fileWriter = new FileWriter(filePath);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return false;
        }
        parser = new JSONParser();
        return true;
    }

    /**
     * Unloads a JSON file from memory
     *
     * @Note: Once the file is unloaded it cannot be read from or written from until a new file is loaded with loadFile.
     */
    public void unloadFile() {
        try {
            fileWriter.write(object.toJSONString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            ErrorHandler.HandleIOError(e);
        }
    }

    public void unloadWithoutWrite(){
        try {
            fileWriter.flush();
            fileWriter.close();
    } catch (IOException e) {
            ErrorHandler.HandleIOError(e);
        }
    }

    /*
    READING METHODS
     */

    /**
     * Loads and returns a single object from a JSON file.
     *
     * @Param key of object to load
     * @Return Object from JSON File
     */
    public Object readObject(String key) {
        return object.get(key);
    }

    /**
     * Loads an array of objects from a JSON file
     *
     * @Param Key of object to load
     * @Return Object array from JSON File
     */
    public JSONArray readArray(String key) {
        JSONArray array = (JSONArray) object.get(key);
        return array;
    }

    /**
     * Loads the key value from a JSON file. Only public interface for this functionality.
     *
     * @Param String key of the JSON value
     * @Return returns either an object array or a single object that can be cast to a JSONObject.
     */
    public Object readPair(String key) {
        if (key.length() >= 1 && key.substring(0, 1).equals("@")) {
            //It is an array and needs to be parsed as one.
            return readArray(key);
        }
        return readObject(key);
    }

    /**
     * 'Drops' the JSON Table, giving you the entire table
     *
     * @Return returns an entire hashmap of Objects that can be casted into JSONObjects and accessed
     */
    public HashMap<Object, Object> dropJsonDb() {
        Set set = object.keySet();
        Iterator<String> iterator = set.iterator();
        HashMap<Object, Object> hashMap = new HashMap<>();
        while (iterator.hasNext()) {
            String key = iterator.next();
            hashMap.put(key, object.get(key));
        }
        return hashMap;
    }

    /*
    WRITE METHODS
     */

    /**
     * Writes a value to the JSON File in memory
     *
     * @Param Key is the identifier of the JSON Object
     * @Param Data is the value of the JSON Object
     */
    public void writePair(String key, String data) {
        object.put(key, data);
    }

    /**
     * Writes an array to the JSON File in memory
     *
     * @Param Key is the identifier of the JSON Object
     * @Param Data is the value of the JSON Object
     */
    public void writeArray(String key, Object data[]) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < data.length; i++) {
            jsonArray.add(key + i + ": " + data[i]);
        }
        object.put("@" + key, jsonArray);
    }

    /**
     * Writes a raw JSONObject allowing for custom file layouts
     * @param obj JSONObject with custom layout
     * @throws IOException IOEXception is thrown form FileWriter.write(), and .flush()
     */
    public void writeRaw(JSONObject obj) throws IOException {
        fileWriter.write(obj.toJSONString());
        fileWriter.flush();
    }

    /**
     * Gets raw json data for custom parsing
     * @return JSONObject object
     */
    public JSONObject readRaw(){
        System.out.println(object.toString());
        return object;
    }
}

