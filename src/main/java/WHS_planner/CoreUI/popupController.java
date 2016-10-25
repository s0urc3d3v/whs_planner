package WHS_planner.CoreUI;

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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class popupController implements Initializable{

    @FXML
    private AnchorPane sportsPopupAnchorPane;

    @FXML
    private JFXButton addListButton;

    @FXML
    private JFXListView<VBox> nameListView;

    @FXML
    private JFXListView<JFXDatePicker> dateListView;

    @FXML
    private JFXListView<JFXDatePicker> timeListView;

    private ObservableList<VBox> nameContents;

    private ObservableList<JFXDatePicker> dateContents;

    private ObservableList<JFXDatePicker> timeContents;

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
        dateContents = FXCollections.observableArrayList();
        timeContents = FXCollections.observableArrayList();
        nameListView.setItems(nameContents);
        dateListView.setItems(dateContents);
        timeListView.setItems(timeContents);

        sportsPopupAnchorPane.applyCss();

        nameListView.getStylesheets().add("/CoreUI/ListView.css");
        nameListView.getStyleClass().add("list-views");
        dateListView.getStylesheets().add("/CoreUI/ListView.css");
        dateListView.getStyleClass().add("list-views");
        timeListView.getStylesheets().add("/CoreUI/ListView.css");
        timeListView.getStyleClass().add("list-viewvert");

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Node n1 = nameListView.lookup(".scroll-bar");
                System.out.println(nameListView.lookup(".scroll-bar"));
                if (n1 instanceof ScrollBar) {
                    final ScrollBar bar1 = (ScrollBar) n1;
                    Node n2 = dateListView.lookup(".scroll-bar");
                    if (n2 instanceof ScrollBar) {
                        final ScrollBar bar2 = (ScrollBar) n2;
                        Node n3 = timeListView.lookup(".scroll-bar");
                        if (n3 instanceof  ScrollBar) {
                            final ScrollBar bar3 = (ScrollBar) n3;
                            System.out.println("bound");
                            bar1.valueProperty().bindBidirectional(bar2.valueProperty());
                            bar2.valueProperty().bindBidirectional(bar3.valueProperty());
                            bar3.valueProperty().bindBidirectional(bar1.valueProperty());
                        }
                    }
                }
            }
        });


    }



    @FXML
    void plusButtonPressed(MouseEvent event) {
        resize();
        VBox v = new VBox(new Hyperlink("test"), new Label("Hello"), new Label("Description"));
        nameContents.add(v); //Add VBox to the Observable List
        nameListView.setItems(nameContents);
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
        nameListView.setPrefWidth(sportsPopupAnchorPane.getWidth()/3);
        dateListView.setPrefWidth(sportsPopupAnchorPane.getWidth()/3);
        timeListView.setPrefWidth(sportsPopupAnchorPane.getWidth()/3);
    }



}
