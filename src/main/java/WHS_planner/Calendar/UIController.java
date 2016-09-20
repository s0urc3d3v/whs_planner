package WHS_planner.Calendar;

/**
 * Created by geoffrey_wang on 9/18/16.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;


public class UIController implements Initializable {

    @FXML
    private Label icon;

    public void initialize(URL location, ResourceBundle resources)
    {
        icon.setText(resources.getString("FontAwesome.file_text_o"));
    }
}

