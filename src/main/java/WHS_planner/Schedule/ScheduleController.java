package WHS_planner.Schedule;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import java.net.URL;
import java.util.ResourceBundle;

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

    String classes[][] = new String[9][6];

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        //we can set the day here
        Title3.setText("It is A Day");
    }

    public void TylerIsACreep()
    {
        Schedule.MainStage.setScene(Schedule.day);
    }


}
