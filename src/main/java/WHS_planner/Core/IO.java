package WHS_planner.Core;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class IO
{
    private JSON jsonApi;
    public IO(String fileName)
    {
        jsonApi = new JSON();
        jsonApi.loadFile(fileName);
    }
    public void writeScheduleArray(Object[] classLayout)
    {
        jsonApi.writeArray("Schedule", classLayout);

    }
    public JSONArray readScheduleArray()
    {
        return jsonApi.readArray("@Schedule");
    }

    public void unload()
    {
        jsonApi.unloadFile();
    }
}
