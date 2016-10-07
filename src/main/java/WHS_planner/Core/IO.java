package WHS_planner.Core;

import WHS_planner.Util.Course;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        jsonApi.writeArray("Schedule", classLayout);

    }
    public Object[] readScheduleArray()
    {
        return (Object[]) jsonApi.readPair("@Schedule");
    }

    public void unload()
    {
        jsonApi.unloadFile();
    }

    public void writeMeetingJsonData(String requestingStudent, String studentRequested, long hour, long minute, Course course){
        HashMap<String, Object> meeting = new HashMap<String, Object>();
        meeting.put("requestingStudent", requestingStudent);
        meeting.put("studentRequested", studentRequested);
        meeting.put("hour", hour);
        meeting.put("minute", minute);
        meeting.put("course", course);
        jsonApi.writeArray("meetingKeys", meeting.entrySet().toArray()); //init with meeting.json file of course
        jsonApi.writeArray("meetingValues", meeting.entrySet().toArray());
        unload();
    }
}
