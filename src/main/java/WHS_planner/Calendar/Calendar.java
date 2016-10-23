package WHS_planner.Calendar;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by geoffrey_wang on 9/21/16.
 */
public class Calendar extends BorderPane {

    //Days of the week
    private String[] daysOfTheWeek = new String[]{"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    private Background background;

    private CalendarBox[][] calendar;
    private int startDay;
    private int numberOfDays;
    private Node taskBox;

    private VBox mainPane;

    private int currentTextBoxRow = -1;
    // MARK: day in foucus
    private int currentDate = -1;

    public Calendar(int startDay, int numberOfDays){

        this.startDay = startDay;
        this.numberOfDays = numberOfDays;

        CalendarUtility util = new CalendarUtility();

        //Loads the ttf font file into the program
        InputStream font = MainUI.class.getResourceAsStream("/FontAwesome/fontawesome.ttf");
        Font.loadFont(font,10);

        try {
            calendar = util.fillInCalendar(startDay, numberOfDays,new UIController());
        }catch (Exception e){
            e.printStackTrace();
        }

        mainPane = new VBox();
        mainPane.setId("vbox");//Replace this ID
        mainPane.setPadding(new Insets(5,5,5,5));
        ArrayList<Node> rows = new ArrayList<>();
        mainPane.setAlignment(Pos.CENTER);

        //Add the week days and first row to the calendar
        GridPane firstRow = new GridPane();
        firstRow.setAlignment(Pos.CENTER);
        firstRow.setHgap(10);
        firstRow.setVgap(10);
        firstRow.setPadding(new Insets(5,5,5,5));

        for (int dayIndex = 0; dayIndex < daysOfTheWeek.length; dayIndex++) {
            Label dayLabel = new Label(daysOfTheWeek[dayIndex]);
            dayLabel.getStyleClass().add("weekday");
            firstRow.add(dayLabel,dayIndex,0);
            firstRow.setHgrow(dayLabel,Priority.ALWAYS);
            dayLabel.prefWidthProperty().bind(this.getCalendarBox(1).widthProperty());
        }

        //Fill in rest of the calendar
        for (int r = 0; r < calendar.length ; r++) {
            GridPane row = new GridPane();
            row.setAlignment(Pos.CENTER);
            row.setHgap(10);
            row.setPadding(new Insets(5,5,5,5));
            for (int c = 0; c < calendar[r].length; c++) {
                CalendarBox tempCalendarBox;
                if (calendar[r][c] != null) {
                    tempCalendarBox = calendar[r][c];
                }else{
                    tempCalendarBox = new CalendarBox(0,0,false);
                }
                tempCalendarBox.prefHeightProperty().bind(row.heightProperty());
                row.add(tempCalendarBox,c,0);
                row.setHgrow(tempCalendarBox,Priority.ALWAYS);
            }
            rows.add(row);
        }

        for (Node row:rows) {
            mainPane.setVgrow(row,Priority.ALWAYS);
            GridPane tempGridPane = (GridPane)row;
            tempGridPane.setMinHeight(CalendarBox.CALENDAR_BOX_MIN_HEIGHT +10);
            tempGridPane.setMinWidth(7*CalendarBox.CALENDAR_BOX_MIN_WIDTH +10);
        }
        rows.add(0,firstRow);

        mainPane.getChildren().setAll(rows);

        this.setCenter(mainPane);

        LayoutAnimator animator = new LayoutAnimator();
//        animator.observe(mainPane.getChildren());
    }

    public void update(int row, int date){

        int[] rowIDs = new int[]{1,2,3,4,5};
        GridPane tempPane = (GridPane) mainPane.getChildren().get(1);

        if(currentDate != -1){
            if(date == currentDate) {
                changeButtonColor(getCalendarBox(currentDate).getButtonNode(), false);
                removeTaskBox(taskBox);
                currentTextBoxRow = -1;
                currentDate = -1;
            }else if(currentTextBoxRow == rowIDs[row]){
                changeButtonColor(getCalendarBox(currentDate).getButtonNode(), false);
                changeButtonColor(getCalendarBox(date).getButtonNode(),true);
                removeTaskBox(taskBox);
                addTaskBox(currentTextBoxRow, getCalendarBox(date).getTaskBox(tempPane.widthProperty()));
                currentDate = date;
            }else{
                changeButtonColor(getCalendarBox(currentDate).getButtonNode(), false);
                removeTaskBox(taskBox);
                currentTextBoxRow = rowIDs[row];
                currentDate = date;
                addTaskBox(currentTextBoxRow, getCalendarBox(date).getTaskBox(tempPane.widthProperty()));
                changeButtonColor(getCalendarBox(date).getButtonNode(),true);
            }
        }else{
            currentTextBoxRow = rowIDs[row];
            currentDate = date;
            addTaskBox(currentTextBoxRow, getCalendarBox(date).getTaskBox(tempPane.widthProperty()));
            changeButtonColor(getCalendarBox(date).getButtonNode(),true);
        }
        taskBox = getCalendarBox(date).getTaskBox(tempPane.widthProperty());
    }

    public void changeButtonColor(JFXButton button,boolean selected){
        if(selected){
            button.getStyleClass().setAll("box-button-selected");
        }else{
            button.getStyleClass().setAll("box-button");
        }
    }

    public CalendarBox getCalendarBox(int date){
        CalendarBox currentBox = null;

        for (int rowIndex = 0; rowIndex < calendar.length; rowIndex++) {
            for (int colIndex = 0; colIndex < calendar[rowIndex].length; colIndex++) {
                CalendarBox box = calendar[rowIndex][colIndex];
                if(box != null) {
                    if (Integer.valueOf(box.getDate()) == date) {
                        currentBox = calendar[rowIndex][colIndex];
                        break;
                    }
                }
            }
            if(currentBox != null){
                break;
            }
        }
        return currentBox;
    }

    public void addTaskBox(int row, Node taskBoxInstance){
        mainPane.getChildren().add(row+1, taskBoxInstance);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(1750));
        fadeIn.setNode(taskBoxInstance);

        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setCycleCount(1);
        fadeIn.setAutoReverse(false);
        fadeIn.playFromStart();
    }

    public void removeTaskBox(Node taskBoxInstance){
        FadeTransition fadeOut = new FadeTransition(Duration.millis(1750));
        fadeOut.setNode(taskBoxInstance);

        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setCycleCount(1);
        fadeOut.setAutoReverse(false);
        fadeOut.playFromStart();

        mainPane.getChildren().remove(taskBoxInstance);
    }
//    public void loadTaskBox(int row){
//        FXMLLoader loader = new FXMLLoader();
//        loader.setResources(ResourceBundle.getBundle("FontAwesome.fontawesome"));
//        loader.setController(new TaskBoxController());
//        loader.setLocation(getClass().getResource("/Calendar/taskBox.fxml"));
//
//        try {//TODO Replace with errorhandler
//            HBox pane = loader.load();
//            GridPane tempPane = (GridPane) mainPane.getChildren().get(4);
//            pane.prefWidthProperty().bind(tempPane.widthProperty());
//            JFXTextField textBox = (JFXTextField) pane.getChildren().get(0);
//            pane.setHgrow(textBox,Priority.ALWAYS);
//
//            mainPane.getChildren().add(row+1, pane);
//
//            FadeTransition fadeIn = new FadeTransition(Duration.millis(1750));
//            fadeIn.setNode(pane);
//
//            fadeIn.setFromValue(0.0);
//            fadeIn.setToValue(1.0);
//            fadeIn.setCycleCount(1);
//            fadeIn.setAutoReverse(false);
//            fadeIn.playFromStart();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    public CalendarBox GetCurrentCalendarBox(){
        return getCalendarBox(currentDate);
    }
}
