package WHS_planner.Meeting;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MeetingController implements Initializable{

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXButton importButton;

    @FXML
    private JFXListView<VBox> requestingListView;

    @FXML
    private JFXListView<VBox> classListView;

    @FXML
    private JFXListView<VBox> requestedListView;

    private ObservableList<VBox> requestingContents;

    private ObservableList<VBox> classContents;

    private ObservableList<VBox> requestedContents;



    private Stage stage = null;
    private HashMap<String, Object> result = new HashMap<String, Object>();

    public MeetingController(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initialized");
        anchorPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                resize();
            }
        });
        requestedContents = FXCollections.observableArrayList();
        requestingContents = FXCollections.observableArrayList();
        classContents = FXCollections.observableArrayList();

        classListView.setItems(classContents);
        requestingListView.setItems(requestingContents);
        requestedListView.setItems(requestedContents);

        addSession("Name1", "Grade1", "Level1", "Teacher1", "Class", "Time", "Date", "Name2", "Grade2", "Level2", "Teacher2");

        anchorPane.applyCss();

        requestingListView.getStylesheets().add("/CoreUI/ListView.css");
        requestingListView.getStyleClass().add("list-views");
        classListView.getStylesheets().add("/CoreUI/ListView.css");
        classListView.getStyleClass().add("list-views");
        requestedListView.getStylesheets().add("/CoreUI/ListView.css");
        requestedListView.getStyleClass().add("list-viewvert"); //Only removes the horizontal scrollbar

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Node n1 = requestingListView.lookup(".scroll-bar");
                if (n1 instanceof ScrollBar) {
                    final ScrollBar bar1 = (ScrollBar) n1;
                    Node n2 = classListView.lookup(".scroll-bar");
                    if (n2 instanceof ScrollBar) {
                        final ScrollBar bar2 = (ScrollBar) n2;
                        Node n3 = requestedListView.lookup(".scroll-bar");
                        if (n3 instanceof  ScrollBar) {
                            final ScrollBar bar3 = (ScrollBar) n3;
                            bar1.valueProperty().bindBidirectional(bar2.valueProperty());
                            bar2.valueProperty().bindBidirectional(bar3.valueProperty());
                            bar3.valueProperty().bindBidirectional(bar1.valueProperty());
                        }
                    }
                }
            }
        });




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
        requestingListView.setPrefWidth((anchorPane.getWidth())/3);
        classListView.setPrefWidth((anchorPane.getWidth())/3);
        requestedListView.setPrefWidth((anchorPane.getWidth())/3);
    }

    public void addSession(String requestingStudentName, String requestingGrade, String requestingLevel, String requestingTeacher, String className, String classTime, String meetingDate, String requestedStudentName, String requestedStudentGrade, String requestedStudentLevel, String requestedStudentTeacher){
        requestingContents.add(createVBox(requestingStudentName, requestingGrade, requestingLevel, requestingTeacher));
        classContents.add(createVBox(meetingDate ,className, classTime, " "));
        requestedContents.add(createVBox(requestedStudentName, requestedStudentGrade, requestedStudentLevel, requestedStudentTeacher));

        requestingListView.setItems(requestingContents);
        classListView.setItems(classContents);
        requestedListView.setItems(requestedContents);
    }

    public void changeSession(int Index,String requestingStudentName, String requestingGrade, String requestingLevel, String requestingTeacher, String className, String classTime, String meetingDate, String requestedStudentName, String requestedStudentGrade, String requestedStudentLevel, String requestedStudentTeacher){
        requestingContents.add(createVBox(requestingStudentName, requestingGrade, requestingLevel, requestingTeacher));
        classContents.add(createVBox(meetingDate, className, classTime, " "));
        requestedContents.add(createVBox(requestedStudentName, requestedStudentGrade, requestedStudentLevel, requestedStudentTeacher));

        requestingListView.setItems(requestingContents);
        classListView.setItems(classContents);
        requestedListView.setItems(requestedContents);
    }

    public void removeSession(int Index) {
        requestingContents.remove(Index);
        classContents.remove(Index);
        requestedContents.remove(Index);

        requestingListView.setItems(requestingContents);
        classListView.setItems(classContents);
        requestedListView.setItems(requestedContents);
    }

    private VBox createVBox(String one, String two, String three, String four){
        Label l1 = new Label(one);
        l1.setPadding(new Insets(5));
        Label l2 = new Label(two);
        l2.setPadding(new Insets(5));
        Label l3 = new Label(three);
        l3.setPadding(new Insets(5));
        Label l4 = new Label(four);
        l4.setPadding(new Insets(5));
        VBox vbox = new VBox(l1, l2, l3, l4);
        return vbox;
    }


    @FXML
    void importButtonPressed(ActionEvent event) {
        //pop your code in here elbing
    }
}
