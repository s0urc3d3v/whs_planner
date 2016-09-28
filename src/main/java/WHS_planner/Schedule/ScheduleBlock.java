package WHS_planner.Schedule;

/**
 * Created by william_robison on 9/28/16.
 */
public class ScheduleBlock {
    private String name;
    private String teacherName;
    private String roomNumber;
    private String periodNumber;


    public ScheduleBlock(String name, String teacherName, String roomNumber, String periodNumber){
        this.name = name;
        this.teacherName = teacherName;
        this.roomNumber = roomNumber;
        this.periodNumber = periodNumber;

    }
}
