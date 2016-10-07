package WHS_planner.CoreUI;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class popupController implements Initializable{

    @FXML
    private AnchorPane sportsPopupAnchorPane;

    @FXML
    private JFXButton addListButton;

    @FXML
    private JFXListView<String> popUpListView;

    private ObservableList<String> listViewContents;

    @FXML
    void plusButtonPressed(MouseEvent event) {
        listViewContents.add("daddy");
        popUpListView.setItems(listViewContents);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listViewContents = FXCollections.observableArrayList("rip", "a", "really", "fat", "vape");
        popUpListView.setItems(listViewContents);
    }
}
