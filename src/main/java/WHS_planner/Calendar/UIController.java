package WHS_planner.Calendar;

/**
 * Created by geoffrey_wang on 9/18/16.
 */

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;


public class UIController implements Initializable {

    @FXML
    private JFXButton button;
    @FXML
    private HBox iconContainer;

    private int currentTextBoxRow = -1;
    private int currentDate = -1;

    public void initialize(URL location, ResourceBundle resources) {



        button.setOnMouseClicked((event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                CalendarBox box = (CalendarBox) (button.getParent().getParent().getParent().lookup("#calendar-box"));
                box.addHomework(null);
                box.update();
                VBox vbox = (VBox)button.getParent().getParent().getParent().getParent().getParent();

                if(currentTextBoxRow == box.getRow()+1) {//It is in the same row
                    if(currentDate == box.getDate()) {
                        vbox.getChildren().remove(currentTextBoxRow);
                        currentTextBoxRow = -1;
                        currentDate = -1;
                    }
                }else{//It is not in the same row
                    if(currentTextBoxRow != -1) {//There is a box
                        vbox.getChildren().remove(currentTextBoxRow);
                    }
                    currentTextBoxRow = box.getRow()+1;
                    currentDate = box.getDate();
                }

                FXMLLoader loader = new FXMLLoader();
                loader.setResources(ResourceBundle.getBundle("FontAwesome.fontawesome"));
                loader.setLocation(getClass().getResource("/Calendar/taskBox.fxml"));

                try {//TODO Replace with errorhandler
                    if(currentTextBoxRow != -1) {
                        vbox.getChildren().add(currentTextBoxRow, loader.load());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (event.getButton() == MouseButton.SECONDARY) {
                CalendarBox box = (CalendarBox) (button.getParent().getParent().getParent().lookup("#calendar-box"));
                box.removeTest();
                box.update();
            }
        }));
    }
}

