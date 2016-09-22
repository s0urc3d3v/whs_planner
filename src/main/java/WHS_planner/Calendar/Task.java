package WHS_planner.Calendar;

/**
 * Created by geoffrey_wang on 9/20/16.
 */
public class Task {
    public String Class, Title, Description;

    public Task(String class1,String title1, String description1){
        Class = class1;
        Title = title1;
        Description = description1;
    }

    public void changeClass(String class1){
        Class = class1;
    }
    public void changeTitle(String title1){
        Title = title1;
    }
    public void changeDescription(String description1){
        Description = description1;
    }
}
