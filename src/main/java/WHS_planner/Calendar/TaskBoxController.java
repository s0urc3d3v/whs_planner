package WHS_planner.Calendar;

/**
 * Created by geoffrey_wang on 9/18/16.
 */

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;


public class TaskBoxController implements Initializable {

    @FXML
    private JFXTextField textBox;

    @FXML
    private JFXCheckBox override;

    public void initialize(URL location, ResourceBundle resources) {
        override.setSelected(true);
        override.setCursor(Cursor.HAND);

//        System.out.println(textBox.getParent());
        try {
            textBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode() == KeyCode.ENTER) {
//                        System.out.println(textBox.getText());
                        textBox.clear();
                    }
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

