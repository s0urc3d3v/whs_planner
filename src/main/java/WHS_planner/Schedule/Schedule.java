package WHS_planner.Schedule;

import WHS_planner.Core.IO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Schedule
{

    public static Stage MainStage;

    @FXML
    private Pane rootLayout;

    @FXML
    private Pane memes;

    private Map<String, Object> labels;

    private ScheduleBlock[] blocks;

    public static Scene schedule;
    public static Scene day;

    public Schedule()
    {
        try
        {
            buildSchedule();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public void buildSchedule() throws Exception
    {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/Schedule/scheduletest.fxml"));

        rootLayout = loader.load();
        generateSchedule(loader);

        schedule = new Scene(rootLayout);

        FXMLLoader load2 = new FXMLLoader();

        load2.setLocation(getClass().getResource("/Schedule/day.fxml"));
        memes = load2.load();

        day = new Scene(memes);
    }


    private void generateSchedule(FXMLLoader loader)
    {
        labels = loader.getNamespace();

        blocks = getData();


        String currentClass;
        String currentTeacher;
        String currentPeriod;
        String currentRoom;

        int incr = 0;
        int incr2 = 1;

        for (int i = 0; i < 56; i++)
        {
            String s;

            currentClass = blocks[i].getClassName();
            currentTeacher = blocks[i].getTeacher();
            currentPeriod = blocks[i].getPeriodNumber();
            currentRoom = blocks[i].getRoomNumber();

            if(blocks[i].getClassName().trim().equals("free"))
            {
                s = "free";
            }
            else
            {
                s = currentClass+"\n"+currentTeacher+"\n"+currentRoom+"\n"+currentPeriod;
            }

            //System.out.println(s);

            String letter;

            switch(incr)
            {
                case 0: letter = "A";
                    break;
                case 1: letter = "B";
                    break;
                case 2: letter = "C";
                    break;
                case 3: letter = "D";
                    break;
                case 4: letter = "E";
                    break;
                case 5: letter = "F";
                    break;
                case 6: letter = "G";
                    break;
                case 7: letter = "H";
                    break;
                default: letter = "Time";
                    break;
            }

            incr++;
            if(incr == 8)
            {
                incr = 0;
                incr2++;
            }


            try
            {
                Label l = (Label) labels.get(letter+incr2);
                /*Bounds l2 = l.getBoundsInLocal();
                double scalex = l.getWidth()/l2.getWidth();
                l.setScaleX(scalex);*/
                l.setText(s);
            }
            catch(Exception e)
            {
                //e.printStackTrace();
            }
        }
    }

    public void parseSchedule()
    {
        ScheduleParser parse = new ScheduleParser();
        try
        {
            parse.getClasses();
        }
        catch(IOException ie)
        {
            ie.printStackTrace();
        }
    }

    public ScheduleBlock[] getData()
    {
        parseSchedule();
        IO dotaIo = new IO("Schedule.json");
        ArrayList<ScheduleBlock> array = dotaIo.readScheduleArray();
        dotaIo.unload();
        ScheduleBlock[] blocks = new ScheduleBlock[array.size()];
        for(int i = 0; i < array.size(); i++) {
            blocks[i] = array.get(i);
        }
        return blocks;
    }

    public Node getPane()
    {
        Node n = schedule.getRoot();
        return n;
    }

}
