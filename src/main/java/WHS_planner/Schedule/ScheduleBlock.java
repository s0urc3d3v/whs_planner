package WHS_planner.Schedule;

public class ScheduleBlock
{
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

    public String getClassName()
    {
        return name;
    }

    public String getTeacher()
    {
        return teacherName;
    }

    public String getRoomNumber()
    {
        return roomNumber;
    }

    public String getPeriodNumber()
    {
        return periodNumber;
    }
}
