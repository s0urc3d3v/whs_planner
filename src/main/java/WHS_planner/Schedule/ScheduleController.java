package WHS_planner.Schedule;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import java.net.URL;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;

public class ScheduleController implements Initializable
{
    @FXML
    private GridPane grid;

    @FXML
    private Label Title3;

    @FXML
    private Button ChangeButton;

    @FXML
    private JFXButton changestuff;

    @FXML
    private ProgressBar progressBar;


    private Timer progressbartimer;

    private boolean normalDay;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        //we can set the day here
        Title3.setText("It is A Day");
        normalDay = true;

        double d = progressVal();
        d = 1.0-d;

        System.out.println(d);
        progressBar.setProgress(d);

    }

    public void TylerIsACreep()
    {
        Schedule.MainStage.setScene(Schedule.day);
    }

    public double progressVal()
    {
        Date date = new Date();

        DateFormat df = new SimpleDateFormat("HH:mm");

        String dateS = df.format(date);

        int num = parseDate(dateS);

        double mod = 0;

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


}
