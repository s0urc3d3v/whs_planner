package WHS_planner.Core;

import WHS_planner.Schedule.ScheduleBlock;
import WHS_planner.Util.Course;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by matthewelbing on 27.09.16.
 */
public class IO {
    private JSON jsonApi;
    public IO(String fileName) {
        jsonApi = new JSON();
        jsonApi.loadFile(fileName);
    }
    public void writeScheduleArray(ScheduleBlock[] array) {
        int i = 0;
        for(ScheduleBlock block: array) {
            jsonApi.writeArray("@" + i, new Object[]{block.getClassName(), block.getPeriodNumber(), block.getRoomNumber(), block.getTeacher()});
            jsonApi.writeArray(i + "", new Object[]{block.getClassName(), block.getTeacher(), block.getRoomNumber(), block.getPeriodNumber()});
            i++;
        }
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


    public void writeMeeting(Student requestingStudent, Student studentRequested, long hour, long minute, Course course) throws IOException {
        JSONObject object = new JSONObject();

        JSONArray requestingStuentJsonData = new JSONArray();
        requestingStuentJsonData.add(requestingStudent.getFirstName());
        requestingStuentJsonData.add(requestingStudent.getLastName());
        requestingStuentJsonData.add(requestingStudent.getEmail());
        requestingStuentJsonData.add(requestingStudent.getGrade());

        JSONArray studentRequestedJsonData = new JSONArray();
        studentRequestedJsonData.add(studentRequested.getFirstName());
        studentRequestedJsonData.add(studentRequested.getLastName());
        studentRequestedJsonData.add(studentRequested.getEmail());
        studentRequestedJsonData.add(studentRequested.getGrade());

        JSONArray requestedCourse = new JSONArray();
        requestedCourse.add(course.getName());
        requestedCourse.add(course.getPeriod());
        requestedCourse.add(course.getTeacher());
        requestedCourse.add(course.getCourseLevel());


        object.put("studentRequesting", requestingStuentJsonData);
        object.put("studentRequested", studentRequested);
        object.put("hour", hour);
        object.put("minute", minute);

        object.put("course", requestedCourse);

        jsonApi.loadFile("src" + File.separator + "main" + File.separator + "resources" + File.separator + "Core" + File.separator + "meeting.json.whsplannermeeting");

        jsonApi.writeRaw(object);
        jsonApi.unloadFile();

    }

    public void readMeetingJsonData(){
        JSONObject rawObject = jsonApi.readRaw();

        JSONArray requestingStudentArray = (JSONArray) rawObject.get("studentRequesting"); //JSONArray
        Iterator<String> requestingStudentIterator = requestingStudentArray.iterator();
        String rsFirstName = requestingStudentIterator.next();
        String rsLastName = requestingStudentIterator.next();
        String rsEmail = requestingStudentIterator.next();
        String rsGrade = requestingStudentIterator.next();
        Student requestingStudent = new Student(rsFirstName, rsLastName, rsEmail, Integer.parseInt(rsGrade));

        JSONArray studentRequestedArray = (JSONArray) rawObject.get("studentRequested");
        Iterator<String> studentRequestedIterator = studentRequestedArray.iterator();
        String srFirstName = studentRequestedIterator.next();
        String srLastName = studentRequestedIterator.next();
        String srEmail = studentRequestedIterator.next();
        String srGrade = studentRequestedIterator.next();
        Student studentRequested = new Student(srFirstName, srLastName, srEmail, Integer.parseInt(srGrade));

        JSONArray courseArray = (JSONArray) rawObject.get("course");
        Iterator<String> courseArrayIterator = courseArray.iterator();
        String cName = courseArrayIterator.next();
        String cPeriod = courseArrayIterator.next();
        String cTeacher = courseArrayIterator.next();
        String cCourseLevel = courseArrayIterator.next();

        Course course = new Course(cName, Integer.parseInt(cPeriod), cTeacher, Course.level.valueOf(cCourseLevel));

        int hour = Integer.parseInt(String.valueOf(rawObject.get("hour")));
        int minute = Integer.parseInt(String.valueOf(rawObject.get("minute")));

        Meeting receivedMeeting = new Meeting(requestingStudent, studentRequested, hour, minute, course);

    }

    public ArrayList<ScheduleBlock> readScheduleArray() {
        ArrayList<ScheduleBlock> scheduleBlockArrayList = new ArrayList<>();
            for(int i = 0; i <= 57; i++) {
                JSONArray array = jsonApi.readArray("@" + i);
                if(array != null) {Iterator iterator = array.iterator();
                    scheduleBlockArrayList.add(new ScheduleBlock(((String)array.get(0)).split(":")[1], ((String)array.get(1)).split(":")[1], ((String)array.get(2)).split(":")[1], ((String)array.get(3)).split(":")[1]));
                }
            }
        return scheduleBlockArrayList;
    }

    public void writeMap(Map<String, Integer> map) {
        Iterator iterator = map.keySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry entry = (Map.Entry<String,Integer>)iterator.next();
            jsonApi.writePair((String) entry.getKey(), Integer.toString( (Integer) entry.getValue()));
            iterator.remove();
        }
    }

    public void writeArray(String arrayName, Object[] objects) {
        jsonApi.writeArray(arrayName, objects);
    }
}
