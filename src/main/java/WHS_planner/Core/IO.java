package WHS_planner.Core;

import WHS_planner.Schedule.ScheduleBlock;
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
    public void writeScheduleArray(ScheduleBlock[] array)
    {
        int i = 0;
        for(ScheduleBlock block: array) {
            jsonApi.writeArray("@" + i, new Object[]{block.getClassName(), block.getPeriodNumber(), block.getRoomNumber(), block.getTeacher()});
            i++;
        }

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
