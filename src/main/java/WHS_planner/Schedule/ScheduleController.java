package WHS_planner.Schedule;

import WHS_planner.Util.AesTool;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;
import javax.swing.Timer;

public class ScheduleController implements Initializable, ActionListener
{

    @FXML
    private GridPane grid;

    @FXML
    private Label Title3;

    @FXML
    private ProgressBar progressBar;

    private BorderPane[] panes;

    private Timer progressbartimer;

    private boolean normalDay;

    private Map<String, Object> labels;


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


        File ipass = new File("Keys/ipass.key");

        if(ipass.exists())
        {
            try
            {
                BufferedReader bri = new BufferedReader(new FileReader(ipass));
                String user = bri.readLine();
                String pass = bri.readLine();
                //TODO json read aes key from keys.key.json
                AesTool usernameTool = new AesTool(user, );
                AesTool passwordTool = new AesTool(pass);

                bri.close();

                if(user == null || pass == null || user.equals("") || pass.equals(""))
                {
                    System.out.println("No ipass data found");
                }
                else
                {
                    String s;

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

                    //we can set the day here
                    Title3.setText(s);
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

        normalDay = true;

        progressbartimer = new Timer(1000, this);
        progressbartimer.start();
    }


    public String getletterday()
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

    public void buildLetterDays()
    {
        try
        {
            File f = new File("Keys/ipass.key");

            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            String user = br.readLine();
            String pass = br.readLine();

            br.close();
            fr.close();

            File tmp = new File("tmp");

            if(!tmp.exists() || tmp.listFiles().length == 0)
            {
                System.out.println("User: "+user+" : Password: "+pass);

                GrabDay gd = new GrabDay(user, pass);
                gd.grabData();
            }

            ParseCalendar pc = new ParseCalendar();
            pc.setData();
            pc.writeData();

            delete(tmp);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    public double progressVal()
    {
        Date date = new Date();

        DateFormat df = new SimpleDateFormat("HH:mm");

        String dateS = df.format(date);

        int num = parseDate(dateS);

        double mod = 1;

        if(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == 4)
        {
            if(num >= 450 && num < 496)
            {
                mod = (496-num)/46.0;
            }
            else if(num >= 496 && num < 576)
            {
                mod = (576-num)/80.0;
            }
            else if(num >= 576 && num < 641)
            {
                mod = (641-num)/65.0;
            }
            else if(num >= 641 && num < 716)
            {
                mod = (716-num)/75.0;
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
        else if(normalDay)
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

    public int parseDate(String date)
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
}
