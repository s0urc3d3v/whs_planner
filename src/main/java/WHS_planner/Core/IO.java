package WHS_planner.Core;

import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by matthewelbing on 27.09.16.
 */
public class IO {
    private JSON jsonApi;
    public IO(String fileName)
    {
        jsonApi = new JSON();
        jsonApi.loadFile(fileName);
    }
    public void writeScheduleArray(Object[] classLayout)
    {
        jsonApi.writeArray("schedule", classLayout);

    }
    public Object[] readScheduleArray()
    {
        return (Object[]) jsonApi.readPair("@schedule");
    }

    public void unload()
    {
        jsonApi.unloadFile();
    }
}
