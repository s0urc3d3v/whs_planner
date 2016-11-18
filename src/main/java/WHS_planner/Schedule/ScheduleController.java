package WHS_planner.Schedule;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;

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

        panes = new BorderPane[82];
        int count = 0;
        //Fills Arrays
        for (int i = 0; i < 82; i++) {
            panes[i] = new BorderPane();
        }

        for (int i = 1; i < 9; i++) {
            for (int j = 2; j < 9; j++) {
                grid.add(panes[count],i,j);
                panes[count].setStyle("-fx-background-color: #ffc04c");
                panes[count].toBack();
                panes[count].setBorder(new Border(new BorderStroke(new Color(1,1,1,1),
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                count++;
            }
        }

        for (int i = 2; i < 9; i++) {
            grid.add(panes[54 + i],0,i);
            panes[54 + i].setStyle("-fx-background-color: #ffc04c");
            panes[54 + i].toBack();
            panes[54 + i].setBorder(new Border(new BorderStroke(new Color(1,1,1,1),
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }


        for (int i = 0; i < 9; i++) {
            grid.add(panes[i + 63],i,1);
            panes[i + 63].setBorder(new Border(new BorderStroke(new Color(1,1,1,1),
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            panes[i + 63].setStyle("-fx-background-color: #ffa500");
            panes[i + 63].toBack();
        }


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
            s = "Today is " + s + " day!";
        }

        //we can set the day here
        Title3.setText(s);


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
            File f = new File("Keys/keys.key.json");

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

        if(normalDay)
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
