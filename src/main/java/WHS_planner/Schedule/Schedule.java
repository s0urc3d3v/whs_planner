package WHS_planner.Schedule;

import WHS_planner.Core.IO;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.util.Map;

public class Schedule extends Application
{

    public static Stage MainStage;

    @FXML
    Pane rootLayout;

    @FXML
    Pane memes;

    private Map<String, Object> labels;


    public static Scene schedule;
    public static Scene day;

    public static void main(String[] args)
    {
        launch(args);
    }

    private ScheduleBlock[] blocks;

    public void start(Stage PrimaryStage)
    {
        MainStage = PrimaryStage;

        PrimaryStage.setTitle("src" + File.pathSeparator + "main" + File.pathSeparator + "resources" + File.pathSeparator + "Schedule");

        try
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

            PrimaryStage.setResizable(true);
            PrimaryStage.setMinHeight(520);
            PrimaryStage.setMinWidth(573);
            PrimaryStage.setScene(schedule);
            PrimaryStage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
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
            currentClass = blocks[i].getClassName();
            currentTeacher = blocks[i].getTeacher();
            currentPeriod = blocks[i].getPeriodNumber();
            currentRoom = blocks[i].getRoomNumber();

            String s = currentClass+"\n"+currentTeacher+"\n"+currentRoom+"\n"+currentPeriod;

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

            Label l = (Label) labels.get(letter+incr2);
            l.setText(s);

        }
    }

    public ScheduleBlock[] getData()
    {
        ScheduleBlock[] blocks;
        IO dotaIo = new IO("Schedule");
        blocks = (ScheduleBlock[]) dotaIo.readScheduleArray();
        return blocks;
    }

}
