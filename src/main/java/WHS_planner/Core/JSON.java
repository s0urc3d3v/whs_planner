package WHS_planner.Core;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

/**
 * Created by matthewelbing on 16.09.16.
 */
public class JSON {
    public JSON (){

    }
    public void writePair(String key, String data){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key, data);
    }
    public void writeJsonArray(String key, String data){

    }
}
