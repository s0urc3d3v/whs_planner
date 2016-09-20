package WHS_planner.CoreUI;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SimpleController implements Initializable {

    @FXML
    private JFXButton buttonTest;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        buttonTest.setText("Login Better!");

        // initialize your logic here: all @FXML variables will have been injected

    }


}
