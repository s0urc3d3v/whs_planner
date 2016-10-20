package WHS_planner.Schedule;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    private BorderPane[] bPanes;

    private Timer progressbartimer;

    private boolean normalDay;

    private Map<String, Object> labels;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        labels = Schedule.loader.getNamespace();



        panes = new BorderPane[72];
        bPanes = new BorderPane[10];
        int count = 0;
        //Fills Arrays
        for (int i = 0; i < 72; i++) {
            panes[i] = new BorderPane();
        }
        for (int i = 0; i < 10; i++) {
            bPanes[i] = new BorderPane();
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
            grid.add(bPanes[i],i,1);
            bPanes[i].setBorder(new Border(new BorderStroke(new Color(1,1,1,1),
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

            bPanes[i].setStyle("-fx-background-color: #ffa500");
            bPanes[i].toBack();
        }
        //we can set the day here
        Title3.setText("It is A Day");
        normalDay = true;



        progressbartimer = new Timer(1000, this);
        progressbartimer.start();
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
                mod = 0;
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
}
