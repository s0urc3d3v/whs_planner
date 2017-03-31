package WHS_planner.Schedule;

import WHS_planner.Core.IO;
import WHS_planner.Main;
import WHS_planner.Util.XorTool;
import com.jfoenix.controls.JFXCheckBox;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class Schedule
{

    public static Scene schedule;
    private final String[] start = {"7:30", "8:32", "9:39", "10:41", "12:16", "1:18", "0:00"};
    private final String[] end = {"8:27", "9:29", "10:36", "12:11", "1:13", "2:15", "0:00"};
    private final String[] wens = {"7:30", "8:15", "9:35", "10:20", "11:40", "12:25", "0:00"};
    private final String[] wene = {"8:10", "8:55", "10:15", "11:35", "12:20", "1:05", "0:00"};
    private final String[] bells = {"7:30", "8:26", "9:58", "10:55", "12:26", "1:23", "0:00"};
    private final String[] belle = {"8:21", "9:18", "10:50", "12:21", "1:18", "2:15", "0:00"};
    @FXML
    private Pane rootLayout;
    @FXML
    private Pane login;
    private Map<String, Object> labels;
    private ScheduleBlock[] blocks;
    private ScheduleController scheduleControl;
    private JFXCheckBox checkBox;

    public Schedule(JFXCheckBox checkBox)
    {
        this.checkBox = checkBox;
        this.checkBox.setOnAction(e -> Platform.runLater(this::resetLabels));

        try
        {
            //replaced with threading...
            buildSchedule();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    public JFXCheckBox getCheck()
    {
        return checkBox;
    }

    public void resetLabels()
    {
        for (int i = 1; i <= 7; i++) {
            Label l = (Label) labels.get("Time"+i);
            String s;
            if(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == 4) {
                s = "Period " + i + "\nStart: " + wens[i - 1] + "\nEnd:" + wene[i - 1];
            } else if (checkBox.isSelected()) {
                s = "Period " + i + "\nStart: " + bells[i - 1] + "\nEnd:" + belle[i - 1];
            } else {
                s = "Period " + i + "\nStart: " + start[i - 1] + "\nEnd:" + end[i - 1];
            }
            l.setText(s);
            l.setWrapText(true);
        }
    }

    private void buildSchedule() throws Exception
    {
        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(getClass().getResource("/Schedule/schedule_layout.fxml"));

        rootLayout = loader.load();
        scheduleControl = loader.getController();



        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(getClass().getResource("/Schedule/material_login.fxml"));

        login = loader2.load();

        loginController control2 = loader2.getController();



        login.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {

                try
                {
                    control2.submit();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        File f = new File(Main.SAVE_FOLDER+ File.separator +"Keys/ipass.key");
        File g = new File(Main.SAVE_FOLDER+ File.separator +"Schedule.json");

        if(!f.exists() && !g.exists())
        {
            schedule = new Scene(login);
        }
        else
        {
            generateSchedule(loader);
            schedule = new Scene(rootLayout);
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
            String s;

            currentClass = blocks[i].getClassName();
            currentTeacher = blocks[i].getTeacher();
            currentTeacher = currentTeacher.replace("<br>", " & ");
            currentClass = currentClass.replace("&amp;", "&");
            currentPeriod = blocks[i].getPeriodNumber();
            currentRoom = blocks[i].getRoomNumber();

            if(blocks[i].getClassName().trim().equals("Free"))
            {
                s = "Free";
            }
            else
            {
                s = currentClass+"\n"+currentTeacher+"\n"+currentRoom+"\n"+ "Block:" + currentPeriod;
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

            try
            {
                Label l = (Label) labels.get(letter+incr2);
                l.setText(s);
                l.setWrapText(true);
            }
            catch(Exception e)
            {
                //e.printStackTrace();
            }

            incr++;
            if(incr == 8)
            {
                incr = 0;
                incr2++;
            }
        }

        for (int i = 1; i <= 7; i++)
        {
            Label l = (Label) labels.get("Time"+i);


            String s;

            if(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == 4)
            {
//                s = "Period "+i+"\nStart: "+"\n"+wens[i-1]+"\nEnd:\n"+wene[i-1];
                s = "Period " + i + "\nStart: " + wens[i - 1] + "\nEnd:" + wene[i - 1];

            } else if (checkBox.isSelected()) {
                s = "Period " + i + "\nStart: " + bells[i - 1] + "\nEnd:" + belle[i - 1];
            }

            else
            {
//                s = "Period "+i+"\nStart: "+"\n"+start[i-1]+"\nEnd:\n"+end[i-1];
                s = "Period " + i + "\nStart: " + start[i - 1] + "\nEnd:" + end[i - 1];

            }

            l.setText(s);
            l.setWrapText(true);
        }
    }

    private void parseSchedule()
    {
        File f = new File(Main.SAVE_FOLDER+ File.separator +"output.html");

        File input = new File(Main.SAVE_FOLDER + File.separator +"Keys/ipass.key");

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

            user = XorTool.decode(user, Main.getXorKey());
            pass = XorTool.decode(pass, Main.getXorKey());

            br.close();
            fr.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


        ScheduleParserV3 parse = new ScheduleParserV3();

        if(!f.exists() && user != null && pass != null )
        {
            final String u = user;
            final String p = pass;

            Thread t = new Thread(() ->
            {
                parse.grabwebpage(u, p);

            });
            t.start();
            try
            {
                t.join();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            try
            {
                parse.getClasses();
                f.delete();
            }
            catch(IOException ie)
            {
                ie.printStackTrace();
            }
        }

    }

    public ScheduleBlock[] getData()
    {
        File schedulefile = new File(Main.SAVE_FOLDER+ File.separator +"Schedule.json");

        if(!schedulefile.exists())
        {
            try {
                schedulefile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            parseSchedule();

        }
        IO dotaIo = new IO(Main.SAVE_FOLDER+ File.separator +"Schedule.json");
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
        return schedule.getRoot();
    }



    public ScheduleBlock[] getToday(String letter)
    {
        ScheduleBlock b[] = new ScheduleBlock[6];
        int x;
        switch(letter)
        {
            case "A": x = 0;
                break;
            case "B": x = 1;
                break;
            case "C": x = 2;
                break;
            case "D": x = 3;
                break;
            case "E": x = 4;
                break;
            case "F": x = 5;
                break;
            case "G": x = 6;
                break;
            case "H": x = 7;
                break;
            default:  x = -1;
                break;
        }

        for (int i = 0; i < 6; i++)
        {
            b[i] = blocks[x+(i*8)];
        }

        return b;
    }

    public ScheduleController getScheduleControl() {
        return scheduleControl;
    }

    public boolean isLoggedIn() {
        File f = new File(Main.SAVE_FOLDER+ File.separator +"Keys/ipass.key");
        File g = new File(Main.SAVE_FOLDER+ File.separator +"Schedule.json");

        return f.exists() || g.exists();
    }
}
