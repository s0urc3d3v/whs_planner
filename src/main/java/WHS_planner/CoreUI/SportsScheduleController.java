package WHS_planner.CoreUI;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class SportsScheduleController implements Initializable {

    @FXML
    JFXButton buttonTest;

    private Stage stage = null;
    private HashMap<String, Object> result = new HashMap<String, Object>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buttonTest.setText("it workes");
    }

    public HashMap<String, Object> getResult() {
        return this.result;
    }

    /**
     * setting the stage of this view
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Closes the stage of this view
     */
    private void closeStage() {
        if(stage!=null) {
            stage.close();
        }
    }

}
