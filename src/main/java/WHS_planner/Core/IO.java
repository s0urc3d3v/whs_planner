package WHS_planner.Core;

import WHS_planner.Schedule.ScheduleBlock;
import WHS_planner.Util.Course;
import WHS_planner.Util.Student;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import static java.lang.System.exit;

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
    //Not sure what this was for but don't think it should be deleted for now
//    void writeMeetingJsonData(Student requestingStudent, Student studentRequested, int month, int day, int year, long hour, long minute, Course course){
//        HashMap<String, Object> meeting = new HashMap<String, Object>();
//        meeting.put("requestingStudent", requestingStudent.getFirstName() + " " + requestingStudent.getLastName());
//        meeting.put("studentRequested", studentRequested.getFirstName() + " " + studentRequested.getLastName());
//        meeting.put("month", month);
//        meeting.put("day", day);
//        meeting.put("year", year);
//        meeting.put("hour", hour);
//        meeting.put("minute", minute);
//        meeting.put("course", course.toString());
//        jsonApi.writeArray("meetingKeys", meeting.entrySet().toArray()); //init with meeting.json.whsplannermeeting file of course
//        jsonApi.writeArray("meetingValues", meeting.entrySet().toArray());
//        unload();
//    }


    public void writeJsonMeetingData(Student requestingStudent, Student studentRequested, long month, long day, long year, long hour, long minute, Course course) throws IOException {
        JSONObject object = new JSONObject();
        //THIS CREATES INVALID JSON and it breaks the read method
        JSONArray requestingStudentJsonData = new JSONArray();
        requestingStudentJsonData.add(requestingStudent.getFirstName());
        requestingStudentJsonData.add(requestingStudent.getLastName());
        requestingStudentJsonData.add(requestingStudent.getEmail());
        requestingStudentJsonData.add(requestingStudent.getGrade());

        JSONArray studentRequestedJsonData = new JSONArray();
        studentRequestedJsonData.add(studentRequested.getFirstName());
        studentRequestedJsonData.add(studentRequested.getLastName());
        studentRequestedJsonData.add(studentRequested.getEmail());
        studentRequestedJsonData.add(studentRequested.getGrade());

        JSONArray requestedCourse = new JSONArray();
        requestedCourse.add(course.getName());
        requestedCourse.add(course.getPeriod());
        requestedCourse.add(course.getTeacher());
        requestedCourse.add(String.valueOf(course.getCourseLevel()));

        object.put("requestingStudent", requestingStudentJsonData.toJSONString());
        object.put("studentRequested", studentRequestedJsonData.toJSONString());
        object.put("month", month);
        object.put("day", day);
        object.put("year", year);
        object.put("hour", hour);
        object.put("minute", minute);

        object.put("course", requestedCourse);

        jsonApi.writeRaw(object);
        jsonApi.unloadWithoutWrite();

    }

    public Meeting readMeetingJsonData(){
        JSONObject rawObject = jsonApi.readRaw();
        //These students need to be parsed
        String requestingStudentUnparsed = (String) rawObject.get("requestingStudent");
        String studentRequestedUnparsed = (String) rawObject.get("studentRequested");
        long month = (long) rawObject.get("month");
        long day = (long) rawObject.get("day");
        long year = (long) rawObject.get("year");
        long hour = (long) rawObject.get("hour");
        long minute = (long) rawObject.get("minute");
        //String courseUnparsed = (String) rawObject.get("course"); differnt parse required
        System.out.println(requestingStudentUnparsed);
        Student requestingStudent = new Student(null, null, null, 0, null);
        Student studentRequested = new Student(null, null, null, 0, null);

        //Start parsing requestingStudentUnparsed
        int startRs = 0;
        int endRs = 0;
        for (startRs = 0; startRs < requestingStudentUnparsed.length(); startRs++) {
            if (requestingStudentUnparsed.charAt(startRs) == '"'){
                for (int j = startRs + 1; j < requestingStudentUnparsed.length(); j++) {
                    if (requestingStudentUnparsed.charAt(j) == '"'){
                        requestingStudent.setFirstName(requestingStudentUnparsed.substring(startRs + 1, j));
                        startRs = j + 2;
                        System.out.println(requestingStudent.getFirstName());
                        break;
                    }
                }
                break;
            }
        }
        for (int x = startRs;x < requestingStudentUnparsed.length(); x++) {
            if (requestingStudentUnparsed.charAt(x) == '"' && requestingStudentUnparsed.charAt(x) != ','){
                for (int j = x + 1; j < requestingStudentUnparsed.length(); j++) {
                    if (requestingStudentUnparsed.charAt(j) == '"'){
                        requestingStudent.setLastName(requestingStudentUnparsed.substring(x + 1, j));
                        startRs = j + 2;
                        System.out.println(requestingStudent.getLastName());
                        break;
                    }
                }
                break;
            }
        }
        for (int x = startRs;x < requestingStudentUnparsed.length(); x++){
            if (requestingStudentUnparsed.charAt(x) == '"' && requestingStudentUnparsed.charAt(x + 1) != ','){
                for (int i = x + 1; i < requestingStudentUnparsed.length(); i++) {
                    if (requestingStudentUnparsed.charAt(i) == '"'){
                        requestingStudent.setEmail(requestingStudentUnparsed.substring(x + 1, i));
                        startRs = i + 2;
                        System.out.println(requestingStudent.getEmail());
                        break;
                    }
                }
                break;
            }
        }
        //This is a number so it must be parsed specially but we know where it is!
        requestingStudent.setGrade(Integer.parseInt(requestingStudentUnparsed.substring(startRs, startRs + 2)));
        System.out.println(requestingStudent.getGrade());

        //requesting student parse done!!

        //studentRequesting parse start TODO:replace startRs and requestingStudentUnparsed and studentRequestedUnparsed
        int startSr = 0;
        int endSr = 0;
        for (startSr = 0; startSr < studentRequestedUnparsed.length(); startSr++) {
            if (studentRequestedUnparsed.charAt(startSr) == '"'){
                for (int j = startSr + 1; j < studentRequestedUnparsed.length(); j++) {
                    if (studentRequestedUnparsed.charAt(j) == '"'){
                        studentRequested.setFirstName(studentRequestedUnparsed.substring(startSr + 1, j));
                        startSr = j + 2;
                        System.out.println(studentRequested.getFirstName());
                        break;
                    }
                }
                break;
            }
        }
        for (int x = startSr;x < studentRequestedUnparsed.length(); x++) {
            if (studentRequestedUnparsed.charAt(x) == '"' && studentRequestedUnparsed.charAt(x) != ','){
                for (int j = x + 1; j < studentRequestedUnparsed.length(); j++) {
                    if (studentRequestedUnparsed.charAt(j) == '"'){
                        studentRequested.setLastName(studentRequestedUnparsed.substring(x + 1, j));
                        startSr = j + 2;
                        System.out.println(studentRequested.getLastName());
                        break;
                    }
                }
                break;
            }
        }
        for (int x = startSr;x < studentRequestedUnparsed.length(); x++){
            if (studentRequestedUnparsed.charAt(x) == '"' && studentRequestedUnparsed.charAt(x + 1) != ','){
                for (int i = x + 1; i < studentRequestedUnparsed.length(); i++) {
                    if (studentRequestedUnparsed.charAt(i) == '"'){
                        studentRequested.setEmail(studentRequestedUnparsed.substring(x + 1, i));
                        startSr = i + 2;
                        System.out.println(studentRequested.getEmail());
                        break;
                    }
                }
                break;
            }
        }
        //This is a number so it must be parsed specially but we know where it is!
        studentRequested.setGrade(Integer.parseInt(studentRequestedUnparsed.substring(startSr, startSr + 2)));
        System.out.println(studentRequested.getGrade());






        //does not work
//        JSONArray requestingStudentArray = (JSONArray) rawObject.get("studentRequesting"); //JSONArray
//        Iterator requestingStudentIterator = requestingStudentArray.iterator();
//        String rsFirstName = (String) requestingStudentIterator.next();
//        String rsLastName = (String) requestingStudentIterator.next();
//        String rsEmail = (String) requestingStudentIterator.next();
//        String rsGrade = (String) requestingStudentIterator.next();
//        String rsTeacher = (String) requestingStudentIterator.next();
//        Student requestingStudent = new Student(rsFirstName, rsLastName, rsEmail, Integer.parseInt(rsGrade), rsTeacher);
//        System.out.println(requestingStudent.toString());
//
//        JSONArray studentRequestedArray = (JSONArray) rawObject.get("studentRequested");
//        Iterator studentRequestedIterator = studentRequestedArray.iterator();
//        String srFirstName = (String) studentRequestedIterator.next();
//        String srLastName = (String) studentRequestedIterator.next();
//        String srEmail = (String) studentRequestedIterator.next();
//        String srGrade = (String) studentRequestedIterator.next();
//        String srTeacher = (String) studentRequestedIterator.next();
//        Student studentRequested = new Student(srFirstName, srLastName, srEmail, Integer.parseInt(srGrade), srTeacher);
//        System.out.println(studentRequested.toString());
//
//        JSONArray courseArray = (JSONArray) rawObject.get("course");
//        Iterator courseArrayIterator = courseArray.iterator();
//        String cName = (String) courseArrayIterator.next();
//        String cPeriod = (String) courseArrayIterator.next();
//        String cTeacher = (String) courseArrayIterator.next();
//        String cCourseLevel = (String) courseArrayIterator.next();
//
//        Course course = new Course(cName, Integer.parseInt(cPeriod), cTeacher, Course.level.valueOf(cCourseLevel));
//
//        int hour = Integer.parseInt(String.valueOf(rawObject.get("month")));
//        int minute = Integer.parseInt(String.valueOf(rawObject.get("minute")));
//        int month = Integer.parseInt(String.valueOf(rawObject.get("month")));
//        int day = Integer.parseInt(String.valueOf(rawObject.get("day")));
//        long year = Integer.parseInt(String.valueOf(rawObject.get("year")));
//
//        return new Meeting(requestingStudent, studentRequested, month, day, year, hour, minute, course, getJsonApi().getFilePath());
        return null;

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

    public void writeMap(Map<String, Integer> map)
    {
        Iterator iterator = map.keySet().iterator();
        while(iterator.hasNext())
        {
            Map.Entry entry = (Map.Entry<String,Integer>)iterator.next();
            jsonApi.writePair((String) entry.getKey(), Integer.toString( (Integer) entry.getValue()));
            iterator.remove();
        }
    }

    public Map<String, Integer> readMap()
    {
        //TODO: Write this fucking method.......eventually ~ John/Vrend
        return null;
    }

    public void writeArray(String arrayName, Object[] objects) {
        jsonApi.writeArray(arrayName, objects);
    }

    public Object[] readArray(String arrayName)
    {
        JSONArray array = jsonApi.readArray(arrayName);
        Object[] objects = new Object[array.size()];

        for (int i = 0; i < array.size(); i++)
        {
            objects[i] = array.get(i);
        }

        return objects;
    }

    public void setFirstRunVar(){
        jsonApi.writePair("hasRun", String.valueOf(true));
    }

    public boolean hasRun(){
        return (boolean) jsonApi.readPair("hasRun");
    }
    public JSON getJsonApi(){
        return jsonApi;
    }

}
