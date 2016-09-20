package WHS_planner.Calendar;

/**
 * Created by geoffrey_wang on 9/18/16.
 */

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.layout.HBox;

public class TestController implements Initializable {

    @FXML
    private Label icon;

    public void initialize(URL location, ResourceBundle resources)
    {
        icon.setText(resources.getString("fa.file_text_o"));
    }
}

