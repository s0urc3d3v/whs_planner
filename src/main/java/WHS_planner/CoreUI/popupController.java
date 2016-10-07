package WHS_planner.CoreUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

public class popupController implements Initializable{

    @FXML
    private AnchorPane sportsPopupAnchorPane;

    @FXML
    private JFXButton addListButton;

    @FXML
    private JFXListView<SportsDate> popUpListView;

    private ObservableList<SportsDate> listViewContents;
    private Stage stage = null;
    private HashMap<String, Object> result = new HashMap<String, Object>();

    @FXML
    void plusButtonPressed(MouseEvent event) {
        listViewContents.add(new SportsDate(new Date(), "Football"));
        popUpListView.setItems(listViewContents);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listViewContents = FXCollections.observableArrayList();
        popUpListView.setItems(listViewContents);
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
