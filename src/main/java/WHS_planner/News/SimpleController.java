package WHS_planner.News;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by andrew_eggleston on 9/26/16.
 */
public class SimpleController implements Initializable {

    @FXML
    private Text dialog;

    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        dialog.setText("text");
    }



}
