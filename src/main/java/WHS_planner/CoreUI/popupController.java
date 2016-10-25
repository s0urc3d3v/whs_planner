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
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

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
    private JFXListView<JFXTextField> nameListView;

    @FXML
    private JFXListView<JFXDatePicker> dateListView;

    @FXML
    private JFXListView<JFXDatePicker> timeListView;

    private ObservableList<JFXTextField> nameContents;

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
        timeListView.getStyleClass().add("list-viewvert"); //Only removes the horizontal scrollbar

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

        JFXTextField tempText = new JFXTextField();
        tempText.setPromptText("Name of Event");
        nameContents.add(tempText);
        nameListView.setItems(nameContents);

        JFXDatePicker tempDate = new JFXDatePicker();
        tempDate.setPromptText("MM/DD/YYYY");
        dateContents.add(tempDate);
        dateListView.setItems(dateContents);

        JFXDatePicker tempTime = new JFXDatePicker();
        tempTime.setShowTime(true);
        tempTime.setPromptText("HH/MM AM/PM");
        timeContents.add(tempTime);
        timeListView.setItems(timeContents);


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

    class SportsEvent extends RecursiveTreeObject<SportsEvent> {
        //        private Date date;
//        private String event;
        private SimpleDateFormat dateFormat;
        private SimpleDateFormat timeFormat;

        Date time;
        Date date;
        StringProperty event;

        private SportsEvent(Date d, Date t, String s) {
            date = d;
            time = t;
            event = new SimpleStringProperty(s);
            dateFormat = new SimpleDateFormat("h:mm a");
            timeFormat = new SimpleDateFormat("EEE, MMM d");

            dateFormat.parse(date.toString(), new ParsePosition(0));
            timeFormat.parse(time.toString(), new ParsePosition(0));
        }

    }
}
