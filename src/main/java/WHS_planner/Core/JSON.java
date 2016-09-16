package WHS_planner.Core;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Created by matthewelbing on 16.09.16.
 */
public class JSON {

    FileWriter fileWriter;
    JSONObject object;
    JSONParser parser;

    public JSON (String filepath) throws IOException {
        fileWriter = new FileWriter(filepath);
        try {
            object =  (JSONObject) parser.parse(new FileReader(filepath));
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
    public void writeJsonArray(String key, String data) {
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
