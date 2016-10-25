package WHS_planner.Schedule;

import WHS_planner.Core.IO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class Schedule
{

    public static Stage MainStage;

    @FXML
    private Pane rootLayout;

    private Pane memes;

    private Map<String, Object> labels;

    private ScheduleBlock[] blocks;

    public static Scene schedule;
    public static Scene day;

    public static FXMLLoader loader;

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
        loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/Schedule/wankTest.fxml"));

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
        File f = new File("output.html");

        File input = new File("user.key");

        String user = null;
        String pass = null;

        try
        {
            if(!input.exists())
            {
                input.createNewFile();
            }

            FileReader fr = new FileReader(input);
            BufferedReader br = new BufferedReader(fr);

            user = br.readLine();
            pass = br.readLine();

            br.close();
            fr.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }




        ScheduleParser parse = new ScheduleParser();

        if(!f.exists() && user != null && pass != null )
        {
            parse.grabwebpage(user, pass);
        }
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
        for(int i = 0; i < array.size(); i++)
        {
            blocks[i] = array.get(i);
        }
        return blocks;
    }

    public Node getPane()
    {
        Node n = schedule.getRoot();
        return n;
    }

    public Node getdaypane()
    {
        Node n = day.getRoot();
        return n;
    }

}
