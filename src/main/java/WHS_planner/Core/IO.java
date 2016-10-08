package WHS_planner.Core;

import WHS_planner.Util.Course;
import org.json.simple.JSONObject;

import java.util.HashMap;

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
    void writeMeetingJsonData(Student requestingStudent, Student studentRequested, long hour, long minute, Course course){
        HashMap<String, Object> meeting = new HashMap<String, Object>();
        meeting.put("requestingStudent", requestingStudent.getFirstName() + " " + requestingStudent.getLastName());
        meeting.put("studentRequested", studentRequested.getFirstName() + " " + studentRequested.getLastName());
        meeting.put("hour", hour);
        meeting.put("minute", minute);
        meeting.put("course", course.toString());
        jsonApi.writeArray("meetingKeys", meeting.entrySet().toArray()); //init with meeting.json.whsplannermeeting file of course
        jsonApi.writeArray("meetingValues", meeting.entrySet().toArray());
        unload();
    }

    public void readMeetingJsonData(){
        HashMap<String, Object> meeting = new HashMap<String, Object>();
        JSONObject meetingKeys = (JSONObject) jsonApi.readPair("@meetingKeys");
        JSONObject meetingValues = (JSONObject) jsonApi.readPair("@meetingValues");
        for (Object key : meeting.keySet()){
            String keyStr = (String)key;
            Object keyValue = meeting.get(keyStr);
            System.out.println("key: " + keyStr + " value: " + keyValue);
        }
     }
}
