package WHS_planner.Calendar;

/**
 * Created by geoffrey_wang on 9/18/16.
 */

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;


public class UIController implements Initializable {

    @FXML
    private JFXButton button;

    public void initialize(URL location, ResourceBundle resources) {
        button.setOnMouseClicked((event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                CalendarBox box = (CalendarBox) (button.getParent().getParent().getParent().lookup("#calendar-box"));
                box.addHomework(null);
                box.update();
                Calendar calendar = (Calendar)button.getParent().getParent().getParent().getParent().getParent().getParent();
                calendar.update(box.getWeek(),box.getDate());
            }else if (event.getButton() == MouseButton.SECONDARY) {
                CalendarBox box = (CalendarBox) (button.getParent().getParent().getParent().lookup("#calendar-box"));
                box.removeTest();
                box.update();
            }
        }));
    }
}

