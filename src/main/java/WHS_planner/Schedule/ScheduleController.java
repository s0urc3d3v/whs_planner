package WHS_planner.Schedule;

import WHS_planner.Main;
import WHS_planner.UI.MainPane;
import WHS_planner.Util.XorTool;
import com.jfoenix.controls.JFXSpinner;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
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
    private JFXSpinner spinner;


    private VBox[] panes;


    private String s;

    private String letter;

    private Timer timer;

    private Pane oldpane;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        timer = new Timer(60000, this);
        timer.start();
        spinner.setVisible(false);
        grid.setGridLinesVisible(false);
        grid.setStyle("-fx-background-color: #F1F1F1;");
        panes = new VBox[72];
        int count = 0;
        //Fills Arrays
        for (int i = 0; i < 72; i++) {
            panes[i] = new VBox();
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
        File Schedule = new File("Documents/Schedule.json");
        if(Schedule.exists() && !ipass.exists()) {

            try
            {
                Title3.setText("");
                s = getletterday((Calendar.getInstance().get(Calendar.MONTH)+1)+"/"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                if (s.length() == 1)
                {
                    letter = s;
                    setClass();
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
                                s += ", next school day will be a \'"+res+"\' day";
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

                Title3.setText(s);
                spinner.setVisible(false);
            }
            catch(Exception e)
            {
                //System.out.println("Error in ScheduleController\n data couldn't be found in ipass.key");
            }
        }

        if(ipass.exists() || Schedule.exists())
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
                            letter = s;
                            setClass();
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
                                        s += ", next school day will be a \'"+res+"\' day";
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
                        Platform.runLater(ipass::delete);
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
        updateSchedule();
    }

    private void updateSchedule() throws Exception
    {

        File schedule = new File("Documents"+File.separator+"Schedule.json");
        File ipasskey = new File("Keys"+File.separator+"ipass.key");

        if(schedule.exists())
        {
            schedule.delete();
        }

        if(ipasskey.exists())
        {
            ipasskey.delete();
        }

        MainPane mp = (MainPane) Main.getMainPane();
        mp.resetSchedule();

    }


    public ScheduleBlock[] getCurrentSchedule() {
        MainPane mp = (MainPane) Main.getMainPane();
        Schedule sch = mp.getSchedule();

        String s = getletterday((Calendar.getInstance().get(Calendar.MONTH)+1)+"/"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        return sch.getToday(s);
    }


    private String parseDate(String input) throws Exception {
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        Date d1 = sdf1.parse(input);
        DateFormat sdf2 = new SimpleDateFormat("EEEE");
        return sdf2.format(d1);
    }

    private void setClass() {

        int row = 4;
        int col = 0;

        switch(letter) {
            case "A": col = 1;
                break;
            case "B": col = 2;
                break;
            case "C": col = 3;
                break;
            case "D": col = 4;
                break;
            case "E": col = 5;
                break;
            case "F": col = 6;
                break;
            case "G": col = 7;
                break;
            case "H": col = 8;
                break;
        }

        try
        {
            row = getBlock();
            if(row == 7)
            {
                return;
            }
            row++;
        } catch (Exception e) {
            e.printStackTrace();
        }

        Pane pane = null;

        ObservableList<Node> childrens = grid.getChildren();
        for (Node node : childrens) {

            if(node instanceof Pane)
            {
                if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                    pane = (Pane) node;
                    if (oldpane != null) {
                        oldpane.setStyle("-fx-background-color: #ffffff");
                        oldpane.setBorder(new Border(new BorderStroke(Color.rgb(241, 241, 241, 1), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));
                    }
                    break;
                }
            }

        }


        pane.setStyle("-fx-background-color: #d9d9d9");
        pane.setBorder(new Border(new BorderStroke(Color.rgb(241,241,241,1), BorderStrokeStyle.SOLID, CornerRadii.EMPTY,BorderStroke.THIN)));
        oldpane = pane;
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(letter != null)
        {
            setClass();

            s = getletterday((Calendar.getInstance().get(Calendar.MONTH)+1)+"/"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
            if (s.length() == 1)
            {
                letter = s;
                s = "Today is '" + s + "' day!";
                Title3.setText(s);
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
                            s += ", next school day will be a \'"+res+"\' day";
                            break;
                        }
                        catch(Exception e2)
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
        }
}

    private int getBlock() throws Exception{
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("HH:mm");
        String dateS = df.format(date);
        int num = parseDate2(dateS);
        double mod;
        if (java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_WEEK) == 4) {
            if (num >= 450 && num < 495) {
                mod = 1;
            } else if (num >= 495 && num < 535) {
                mod = 1;
            } else if (num >= 535 && num <= 620) {
                mod = 2;
            } else if (num >= 575 && num < 620) {
                mod = 3;
            } else if (num >= 620 && num < 700) {
                mod = 4;
            } else if (num >= 700 && num < 745) {
                mod = 5;
            } else if (num >= 745 && num <= 785) {
                mod = 6;
            } else {
                mod = 0;
            }
        } else {
            if (num >= 450 && num < 512) {
                mod = 1;
            } else if (num >= 512 && num < 579) {
                mod = 2;
            } else if (num >= 579 && num < 641) {
                mod = 3;
            } else if (num >= 641 && num < 736) {
                mod = 4;
            } else if (num >= 736 && num < 798) {
                mod = 5;
            } else if (num >= 798 && num <= 855) {
                mod = 6;
            } else {
                mod = 7;
            }
        }
        return (int) mod;
    }


    private int parseDate2(String date) {
        String hour = date.substring(0, date.indexOf(":"));
        String minute = date.substring(date.indexOf(":") + 1);
        int hr = Integer.parseInt(hour);
        int min = Integer.parseInt(minute);
        min += (hr * 60);
        return min;
    }
}
