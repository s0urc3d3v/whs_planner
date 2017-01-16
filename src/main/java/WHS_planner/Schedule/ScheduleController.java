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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

                if (!user.equals("") && !pass.equals(""))
                {
                    Title3.setText("");
                    spinner.setVisible(true);
                    Thread t = new Thread(() -> {
                        BufferedReader br;
                        try {
                            File f = new File("Documents" + File.separator + "DayArray.json");

                            if (!f.exists())
                            {
                                f.createNewFile();
                            }

                            br = new BufferedReader(new FileReader("Documents" + File.separator + "DayArray.json"));

                            if (br.readLine() == null)
                            {
                                buildLetterDays();
                            }

                            br.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        s = getletterday((Calendar.getInstance().get(Calendar.MONTH)+1)+"/"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                        if (s.length() == 1)
                        {
                            s = "Today is '" + s + "' day!";
                        }
                        else
                        {
                            ParseCalendar pc = new ParseCalendar();
                            pc.readData();
                            int m = Calendar.getInstance().get(Calendar.MONTH)+1;
                            int i = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                            int th = 0;
                            int year = Calendar.getInstance().get(Calendar.YEAR);

                            while(true)
                            {
                                String res = pc.getDay(m+"/"+i);

                                if(res.length() == 1)
                                {
                                    try
                                    {
                                        String day = parseDate(m+"/"+i+"/"+year);
                                        s += ", "+day+" will be a \'"+res+"\' day";
                                        break;
                                    }
                                    catch(Exception e)
                                    {
                                        System.out.println("Exception in getting the closest new school day.");
                                    }
                                }

                                switch(m)
                                {
                                    case 1: th = 31;
                                        break;
                                    case 2: th = 28;
                                        break;
                                    case 3: th = 31;
                                        break;
                                    case 4: th = 30;
                                        break;
                                    case 5: th = 31;
                                        break;
                                    case 6: th = 30;
                                        break;
                                    case 7: th = 31;
                                        break;
                                    case 8: th = 31;
                                        break;
                                    case 9: th = 30;
                                        break;
                                    case 10: th = 31;
                                        break;
                                    case 11: th = 30;
                                        break;
                                    case 12: th = 31;
                                        break;
                                }

                                i++;

                                if(i > th)
                                {
                                    i = 1;
                                    m++;

                                    if(m > 12)
                                    {
                                        m = 1;
                                        year++;
                                    }
                                }
                            }
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

    }


    private String getletterday(String s)
    {
        String result = "error";
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
        if (ipassFile.exists()) {
            ipassFile.delete();
        }
        updateSchedule();
    }

    public void updateSchedule() throws Exception
    {

        File schedule = new File("Documents"+File.separator+"Schedule.json");

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

        String s = getletterday((Calendar.getInstance().get(Calendar.MONTH)+1)+"/"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        return sch.getToday(s);
    }


    public String parseDate(String input) throws Exception
    {
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        Date d1 = sdf1.parse(input);
        DateFormat sdf2 = new SimpleDateFormat("EEEE");
        String res = sdf2.format(d1);
        return res;
    }

}
