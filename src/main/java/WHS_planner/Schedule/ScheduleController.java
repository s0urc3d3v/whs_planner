package WHS_planner.Schedule;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ScheduleController implements Initializable
{
    @FXML
    private GridPane grid;

    @FXML
    private Label Title;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Title.setText("It is A Day");
    }
}
