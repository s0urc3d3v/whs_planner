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
    public void writeScheduleArray(String[][] classLayout){
        JSONArray baseJsonArray = new JSONArray();
        for (int i = 0; i < classLayout.length; i++) {
            JSONArray secondaryJsonArray = new JSONArray();
            for (int j = 0; j < classLayout[0].length; j++) {
                secondaryJsonArray.add(classLayout[i][j]);
            }
            baseJsonArray.add(secondaryJsonArray);
        }
        jsonApi.writePair("schedule", baseJsonArray.toJSONString());
    }
}
