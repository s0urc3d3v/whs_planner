package WHS_planner.CoreUI;

import WHS_planner.Core.SportsHandler;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.skins.JFXDatePickerContent;
import com.jfoenix.skins.JFXTimePickerContent;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.awt.*;
import java.net.URL;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

public class popupController implements Initializable{

    @FXML
    private AnchorPane sportsPopupAnchorPane;

    @FXML
    private JFXButton addListButton;

    @FXML
    private JFXListView<JFXComboBox> nameListView;

    @FXML
    private JFXListView<JFXButton> minusListView;

    private ObservableList<JFXComboBox> nameContents;


    private ObservableList<JFXButton> minusContents;


    private Stage stage = null;
    private HashMap<String, Object> result = new HashMap<String, Object>();

    public popupController(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sportsPopupAnchorPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                resize();
            }
        });
        nameContents = FXCollections.observableArrayList();
        minusContents = FXCollections.observableArrayList();

        nameListView.setItems(nameContents);
        minusListView.setItems(minusContents);

        sportsPopupAnchorPane.applyCss();

        nameListView.getStylesheets().add("/CoreUI/ListView.css");
        nameListView.getStyleClass().add("list-views");
        minusListView.getStylesheets().add("/CoreUI/ListView.css");
        minusListView.getStyleClass().add("list-viewvert"); //Only removes the horizontal scrollbar

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Node n1 = nameListView.lookup(".scroll-bar");
                if (n1 instanceof ScrollBar) {
                    final ScrollBar bar1 = (ScrollBar) n1;

                    Node n4 = minusListView.lookup(".scroll-bar");
                    if (n4 instanceof ScrollBar) {
                        final ScrollBar bar4 = (ScrollBar) n4;
                        bar1.valueProperty().bindBidirectional(bar4.valueProperty());
                    }

                }
            }
        });

    }

    @FXML
    void plusButtonPressed(MouseEvent event) {
        resize();

        JFXComboBox tempCombo = new JFXComboBox();
        tempCombo.setPromptText("Sport");
        SportsHandler sportsHandler = new SportsHandler();
        String[] sports = sportsHandler.getSports();
        Label[] label = new Label[sports.length];
        for (int i = 0; i < sports.length; i++) {
            label[i].setText(sports[i]);
        }
        tempCombo.getItems().addAll(label);
        nameContents.add(tempCombo);
        nameListView.setItems(nameContents);

        JFXButton tempMinusButton = new JFXButton("-");
        tempMinusButton.setPrefSize(31, 31);
        minusContents.add(tempMinusButton);
        tempMinusButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int index = 0;
                for (int i = 0; i < minusContents.size(); i++) {
                    if (minusContents.get(i) == tempMinusButton) {
                        index = i;
                        break;
                    }
                }
                nameContents.remove(index);
                minusContents.remove(index);

                nameListView.setItems(nameContents);
                minusListView.setItems(minusContents);

            }
        });
        minusListView.setItems(minusContents);
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

    private void resize(){
        nameListView.setPrefWidth((sportsPopupAnchorPane.getWidth()-75)/3);
    }
}
