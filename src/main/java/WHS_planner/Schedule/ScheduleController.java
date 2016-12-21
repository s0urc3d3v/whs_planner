package WHS_planner.Schedule;

import WHS_planner.Main;
import WHS_planner.UI.MainPane;
import WHS_planner.Util.XorTool;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class ScheduleController implements Initializable, ActionListener
{

    private boolean write = false;

    @FXML
    private GridPane grid;

    @FXML
    private Label Title3;

    @FXML
    private ProgressBar progressBar;

    private BorderPane[] panes;

    private Timer progressbartimer;

    private String s;


//    Pane getBar() {
//        BorderPane anchor = new BorderPane();
////        HBox anchor = new HBox();
////        AnchorPane anchor = new AnchorPane();
//
////        anchor.getChildren().add(progressBar);
//        anchor.setCenter(progressBar);
//        return anchor;
//    }
    ProgressBar getBar() {
        return progressBar;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
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

                if (pass == null || user.equals("") || pass.equals(""))
                {
                    System.out.println("No ipass data found");
                }
                else
                {
                    Title3.setText("");

                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {

                            BufferedReader br;
                            try
                            {
                                File f = new File("DayArray.json");

                                if(!f.exists())
                                {
                                    f.createNewFile();
                                }

                                br = new BufferedReader(new FileReader("DayArray.json"));

                                if (br.readLine() == null)
                                {
                                    buildLetterDays();
                                }

                                br.close();
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }

                            s = getletterday();

                            if(s.length() == 1)
                            {
                                s = "Today is '" + s + "' day!";
                            }

                            //you can't do javafx stuff on other threads
                            Platform.runLater(() -> Title3.setText(s));

                        }
                    });
                    t.start();


                }
            }
            catch(Exception e)
            {
                System.out.println("Error in ScheduleController\n data couldn't be found in ipass.key");
            }
        }
        else
        {
            System.out.println("No ipass file, try logging in");
        }

        progressbartimer = new Timer(1000, this);
        progressbartimer.start();
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
            File f = new File("Keys/ipass.key");

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
                        }
                        catch(Exception e)
                        {

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


    private double progressVal()
    {
        Date date = new Date();

        DateFormat df = new SimpleDateFormat("HH:mm");

        String dateS = df.format(date);

        int num = parseDate(dateS);

        double mod;

        if(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == 4)
        {
            if(num >= 450 && num < 495)
            {
                mod = (495-num)/45.0;
            }
            else if(num >= 495 && num < 575)
            {
                mod = (575-num)/80.0;
            }
            else if(num >= 575 && num < 620)
            {
                mod = (620-num)/45.0;
            }
            else if(num >= 620 && num < 700)
            {
                mod = (700-num)/80.0;
            }
            else if(num >= 700 && num < 745)
            {
                mod = (745-num)/45.0;
            }
            else if(num >= 745 && num <= 785)
            {
                mod = (785-num)/40.0;
            }
            else
            {
                mod = 1;
            }
        }
        else
        {
            if(num >= 450 && num < 512)
            {
                mod = (512-num)/62.0;
            }
            else if(num >= 512 && num < 579)
            {
                mod = (579-num)/67.0;
            }
            else if(num >= 579 && num < 641)
            {
                mod = (641-num)/62.0;
            }
            else if(num >= 641 && num < 736)
            {
                mod = (736-num)/95.0;
            }
            else if(num >= 736 && num < 798)
            {
                mod = (798-num)/62.0;
            }
            else if(num >= 798 && num <= 855)
            {
                mod = (855-num)/57.0;
            }
            else
            {
                mod = 1;
            }
        }

        return mod;
    }

    private int parseDate(String date)
    {
        String hour = date.substring(0, date.indexOf(":"));
        String minute = date.substring(date.indexOf(":")+1);

        int hr = Integer.parseInt(hour);
        int min = Integer.parseInt(minute);

        min += (hr*60);

        return min;
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        double d = progressVal();
        d = 1.0-d;
        progressBar.setProgress(d);
    }

    private void delete(File file) throws IOException
    {

        for (File childFile : file.listFiles())
        {

            if (childFile.isDirectory())
            {
                delete(childFile);
            }
            else
            {
                if (!childFile.delete())
                {
                    throw new IOException();
                }
            }
        }

        if (!file.delete())
        {
            throw new IOException();
        }
    }

    public void updateSchedule() throws Exception
    {
        File keys = new File("Keys");

        File[] files = keys.listFiles();

        for(File f : files)
        {
            if(f.getName().equals("ipass.key") || f.getName().equals("xor.key"))
            {

            }
            else
            {
                f.delete();
            }
        }

        File schedule = new File("Schedule.json");
        File days = new File("DayArray.json");
        File output = new File("output.html");

        if(schedule.exists())
        {
            schedule.delete();
        }
        if(days.exists())
        {
            days.delete();
        }
        if(output.exists())
        {
            output.delete();
        }

        MainPane mp = (MainPane) Main.getMainPane();
        mp.resetSchedule();

    }


    public ScheduleBlock[] getCurrentSchedule()
    {
        MainPane mp = (MainPane) Main.getMainPane();
        Schedule sch = mp.getSchedule();

        String s = getletterday();

        ScheduleBlock[] sb = sch.getToday(s);

        return sb;
    }

}
