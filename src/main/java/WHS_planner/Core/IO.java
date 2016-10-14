package WHS_planner.Core;

import WHS_planner.Schedule.ScheduleBlock;
import org.json.simple.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class IO
{
    private JSON jsonApi;
    public IO(String fileName) {
        jsonApi = new JSON();
        jsonApi.loadFile(fileName);
    }

    public void writeScheduleArray(ScheduleBlock[] array) {
        int i = 0;
        for(ScheduleBlock block: array) {
            jsonApi.writeArray(i + "", new Object[]{block.getClassName(), block.getTeacher(), block.getRoomNumber(), block.getPeriodNumber()});
            i++;
        }

    }

    //TODO: This is a pretty heavy method.
    public ArrayList<ScheduleBlock> readScheduleArray() {
        ArrayList<ScheduleBlock> scheduleBlockArrayList = new ArrayList<>();
        for(int i = 0; i <= 57; i++) {
            JSONArray array = jsonApi.readArray("@" + i);
            if(array != null) {
                Iterator iterator = array.iterator();
                scheduleBlockArrayList.add(new ScheduleBlock(((String)array.get(0)).split(":")[1], ((String)array.get(1)).split(":")[1], ((String)array.get(2)).split(":")[1], ((String)array.get(3)).split(":")[1]));
            }
        }
        return scheduleBlockArrayList;
    }

    public void unload() {
        jsonApi.unloadFile();
    }
}