package WHS_planner.Schedule;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class dayController implements Initializable
{
    public void dirtyLittleSecret()
    {
        Schedule.MainStage.setScene(Schedule.schedule);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }
}
