package WHS_planner.Core;

import org.json.simple.JSONArray;

import java.util.ArrayList;

/**
 * Created by matthewelbing on 27.09.16.
 */
public class IO {
    private JSON jsonApi;
    public IO(){
        jsonApi = new JSON();
    }
    public boolean writeScheduleArray(String[][] classLayout){
        JSONArray baseJsonArray = new JSONArray();
        for (int i = 0; i < classLayout.length; i++) {
            JSONArray secondaryJsonArray;
            for (int j = 0; j < classLayout[0].length; j++) {

            }
        }
    }
}
