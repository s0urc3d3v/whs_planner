package WHS_planner.Calendar;

/**
 * Created by geoffrey_wang on 9/18/16.
 */

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;


public class UIController implements Initializable {

    @FXML
    private JFXButton button;
    @FXML
    private HBox iconContainer;

    public void initialize(URL location, ResourceBundle resources) {
        button.setOnMouseClicked((event -> {
            if(iconContainer.lookup("#homework-icon") != null){
                Label icon = (Label)(iconContainer.lookup("#homework-icon"));
                icon.setText(resources.getString("FontAwesome.file_text_o")); //Set the icon text to a file icon
            }
            if(iconContainer.lookup("#test-icon") != null){
                Label icon = (Label)(iconContainer.lookup("#test-icon"));
                icon.setText(resources.getString("FontAwesome.check")); //Set the icon text to a file icon
            }
        }));
//        icon.setText(resources.getString("FontAwesome.file_text_o")); //Set the icon text to a file icon
    }
}

