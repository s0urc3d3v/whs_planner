package WHS_planner.Schedule;

import WHS_planner.Main;
import WHS_planner.UI.MainPane;
import WHS_planner.Util.XorTool;
import com.jfoenix.controls.JFXSpinner;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.*;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

public class ScheduleController implements Initializable
{

    private boolean write = false;

    @FXML
    private GridPane grid;

    @FXML
    private Label Title3;

    @FXML
    private JFXSpinner spinner;


    private BorderPane[] panes;


    private String s;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        spinner.setVisible(false);
        grid.setGridLinesVisible(false);
        grid.setStyle("-fx-background-color: #F1F1F1;");
        panes = new BorderPane[82];
        int count = 0;
        //Fills Arrays
        for (int i = 0; i < 82; i++) {
            panes[i] = new BorderPane();
        }
        for (int i = 1; i < 9; i++) {
            for (int j = 2; j < 9; j++) {
                grid.add(panes[count],i,j);
                panes[count].setStyle("-fx-background-color: #ffffff");
                panes[count].toBack();
                panes[count].setBorder(new Border(new BorderStroke(Color.rgb(241,241,241,1),
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY,BorderStroke.THIN)));
                count++;
            }
        }
        for (int i = 2; i < 9; i++) {
            grid.add(panes[54 + i],0,i);
            panes[54 + i].setStyle("-fx-background-color: #ffffff");
            panes[54 + i].toBack();
            panes[54 + i].setBorder(new Border(new BorderStroke(Color.rgb(241,241,241,1),
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
        }
        for (int i = 0; i < 9; i++) {
            grid.add(panes[i + 63],i,1);
            panes[i + 63].setBorder(new Border(new BorderStroke(Color.rgb(241,241,241,1),
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
            panes[i + 63].setStyle("-fx-background-color: #ffffff");
            panes[i + 63].toBack();
        }
        File ipass = new File("Keys" + File.separator + "ipass.key");
        if(ipass.exists())
        {
            try
            {
                BufferedReader bri = new BufferedReader(new FileReader(ipass));
                String user = bri.readLine();
                String pass = bri.readLine();
                user = XorTool.decode(user, Main.getXorKey());
                pass = XorTool.decode(pass, Main.getXorKey());
                bri.close();
                if (/*pass == null || */user.equals("") || pass.equals(""))
                {
                    //System.out.println("No ipass data found");
                }
                else
                {
                    Title3.setText("Calendar loading");
                    spinner.setVisible(true);
                    Thread t = new Thread(() -> {
                        BufferedReader br;
                        try {
                            File f = new File("src" + File.separator + "main" + File.separator + "resources" + File.separator + "Schedule" + File.separator + "json" + File.separator + "DayArray.json");

                            if (!f.exists())
                            {
                                f.createNewFile();
                            }

                            br = new BufferedReader(new FileReader("src" + File.separator + "main" + File.separator + "resources" + File.separator + "Schedule" + File.separator + "json" + File.separator + "DayArray.json"));

                            if (br.readLine() == null)
                            {
                                buildLetterDays();
                            }

                            br.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        s = getletterday();
                        if (s.length() == 1) {
                            s = "Today is '" + s + "' day!";
                        }
                        //you can't do javafx stuff on other threads
                        Platform.runLater(() -> Title3.setText(s));
                        Platform.runLater(() -> spinner.setVisible(false));
                    });
                    t.start();
                }
            }
            catch(Exception e)
            {
                //System.out.println("Error in ScheduleController\n data couldn't be found in ipass.key");
            }
        }
        else
        {
            //System.out.println("No ipass file, try logging in");
        }
    }


    private String getletterday()
    {
        String result = "error";
        String s = (Calendar.getInstance().get(Calendar.MONTH)+1)+"/"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        ParseCalendar pc = new ParseCalendar();
        try
        {
            pc.readData();
            result = pc.getDay(s);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }

    private void buildLetterDays()
    {
        try
        {
            File f = new File("Keys"+File.separator+"ipass.key");

            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            String user = br.readLine();
            String pass = br.readLine();

            user = XorTool.decode(user, Main.getXorKey());
            pass = XorTool.decode(pass, Main.getXorKey());

            br.close();
            fr.close();

            File downloadcache = new File("Keys"+File.separator+"DLCache.key");

            BufferedWriter dlcw;

            if(!downloadcache.exists())
            {
                downloadcache.createNewFile();
                dlcw = new BufferedWriter(new FileWriter(downloadcache));
                dlcw.write("false");
                dlcw.close();
                GrabDay gd = new GrabDay(user, pass);
                gd.grabData();
                write = true;
            }
            else
            {
                BufferedReader dlc = new BufferedReader(new FileReader(downloadcache));
                String val = dlc.readLine();
                dlc.close();
                if(val != null)
                {
                    if(val.equals("false"))
                    {
                        File tmpf = new File("tmp");
                        dlcw = new BufferedWriter(new FileWriter(downloadcache));
                        dlcw.write("false");
                        dlcw.close();
                        try
                        {
                            delete(tmpf);
                        } catch (Exception ignored) {
                        }
                        GrabDay gd = new GrabDay(user, pass);
                        gd.grabData();
                        downloadcache.delete();
                        downloadcache.createNewFile();
                        dlcw = new BufferedWriter(new FileWriter(downloadcache));
                        dlcw.write("true");
                        write = true;
                        dlcw.close();
                    }
                }
            }
            File tmp = new File("tmp");
            if(write)
            {
                ParseCalendar pc = new ParseCalendar();
                pc.setData();
                pc.writeData();
                delete(tmp);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void delete(File file) throws IOException {
        for (File childFile : file.listFiles()) {
            if (childFile.isDirectory()) {
                delete(childFile);
            } else {
                if (!childFile.delete()) {
                    throw new IOException();
                }
            }
        }
        if (!file.delete()) {
            throw new IOException();
        }
    }


    public void logout() throws Exception {
        File ipassFile = new File("Keys" + File.separator + "ipass.key");
        File schedule = new File("src" + File.separator + "main" + File.separator + "resources" + File.separator + "Schedule" + File.separator + "json" + File.separator + "Schedule.json");
        if (ipassFile.exists()) {
            ipassFile.delete();
        }
        if (schedule.exists()) {
            schedule.delete();
        }
        MainPane mp = (MainPane) Main.getMainPane();
        mp.resetSchedule();
    }

    public void updateSchedule() throws Exception
    {

        File schedule = new File("src"+File.separator+"main"+File.separator+"resources"+File.separator+"Schedule"+File.separator+"json"+File.separator+"Schedule.json");

        if(schedule.exists())
        {
            schedule.delete();
        }

        MainPane mp = (MainPane) Main.getMainPane();
        mp.resetSchedule();

    }


    public ScheduleBlock[] getCurrentSchedule()
    {
        MainPane mp = (MainPane) Main.getMainPane();
        Schedule sch = mp.getSchedule();

        String s = getletterday();

        return sch.getToday(s);
    }

}
