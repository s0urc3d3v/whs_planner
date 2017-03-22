package WHS_planner.Calendar;

import WHS_planner.Core.IO;
import WHS_planner.Core.JSON;
import WHS_planner.Main;
import WHS_planner.Schedule.Schedule;
import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Calendar extends BorderPane {
    private String filePath;

    //Days of the week
    private String[] daysOfTheWeek = new String[]{"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

    private CalendarBox[][] calendar;
    private int month;
    private int startDay;
    private int numberOfDays;
    private Node taskBox;

    //Tzurs code
    private CalendarHelper dayFinder = new CalendarHelper();
    private IO io;
    private JSON json;
    //End Tzurs code
    private VBox mainPane;
    private int currentTextBoxRow = -1;
    // MARK: day in focus
    private int currentDate = -1;
    private Schedule schedule;

    public Schedule getSchedule()
    {
        return schedule;
    }

    public void setSchedule(Schedule sc)
    {
        schedule = sc;
    }
    public Calendar(int month, JFXButton nextButton, JFXButton prevButton, Schedule sc) {
        this.month = month;
        filePath = Main.SAVE_FOLDER + File.separator + "Calendar" + File.separator + "Calendar-" + (this.month+1) + ".json";
        this.schedule = sc;

        File saveFile = new File(Main.SAVE_FOLDER + File.separator + this.month + "CalendarHolder.json");
        if(!saveFile.exists()){
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.startDay = dayFinder.getWeekdayMonthStarts(month);
        this.numberOfDays = dayFinder.getDaysInMonth(month);

        CalendarUtility util = new CalendarUtility(this);

        //Loads the ttf font file into the program
        InputStream font = Main.class.getResourceAsStream("/FontAwesome/fontawesome.ttf");
        Font.loadFont(font,10);

        mainPane = new VBox();
        mainPane.setId("vbox");//Replace this ID
        mainPane.setPadding(new Insets(5,5,5,5));
        ArrayList<Node> rows = new ArrayList<>();
        mainPane.setAlignment(Pos.CENTER);

        //Top Row
        BorderPane topRow = new BorderPane();
//        HBox topRow = new HBox();
        Label monthLabel = new Label(dayFinder.getMonthString(month+1));
//        topRow.setStyle("fx-background-color:#FFFFFF;");

        monthLabel.getStyleClass().add("monthLabel");
        prevButton.getStyleClass().add("monthButton");
        nextButton.getStyleClass().add("monthButton");

//        topRow.getChildren().addAll(prevButton, monthLabel, nextButton);
        topRow.setLeft(prevButton);
        topRow.setCenter(monthLabel);
        topRow.setRight(nextButton);

        topRow.prefWidthProperty().bind(this.prefWidthProperty());
        topRow.setPadding(new Insets(10, 10, 0, 10));


//        topRow.setAlignment(Pos.CENTER);


//        topRow.prefHeightProperty().bind(this.prefHeightProperty());
//        prevButton.setTextAlignment();
//        prevButton.setAlignment(Pos.CENTER_LEFT);
//        nextButton.setAlignment(Pos.CENTER_RIGHT);
//        topRow.setSpacing(50);

        mainPane.getChildren().add(topRow);

        try{
            load();
        }catch (OldSaveVersionException e){
            try {
                io = new IO(Main.SAVE_FOLDER + File.separator + this.month + "CalendarHolder.json");
                json = io.getJsonApi();
                calendar = util.CalendarLoad(startDay, numberOfDays, json, this.month);
                System.out.println("USED OLD SAVE FILES");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            json.unloadFile();
        }

//        try {
//            calendar = util.CalendarLoad(startDay, numberOfDays, json, this.month);
//            json.unloadFile();
//            System.out.println("USED OLD SAVE FILES");
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }


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
            GridPane.setHgrow(dayLabel, Priority.ALWAYS);
            dayLabel.prefWidthProperty().bind(this.widthProperty());
        }

        //Fill in rest of the calendar
        for (CalendarBox[] aCalendar : calendar) {
            GridPane row = new GridPane();
            row.setAlignment(Pos.CENTER);
            row.setHgap(10);
            row.setPadding(new Insets(5, 5, 5, 5));
            for (int c = 0; c < aCalendar.length; c++) {
                CalendarBox tempCalendarBox;
                if (aCalendar[c] != null) {
                    tempCalendarBox = aCalendar[c];
                } else {
                    tempCalendarBox = new CalendarBox(0, 0, false, CalendarBox.generateTaskLists(null), 0, this);
                }
                tempCalendarBox.prefHeightProperty().bind(row.heightProperty());
                row.add(tempCalendarBox, c, 0);
                GridPane.setHgrow(tempCalendarBox, Priority.ALWAYS);
            }
            rows.add(row);
        }

        for (Node row:rows) {
            VBox.setVgrow(row, Priority.ALWAYS);
            GridPane tempGridPane = (GridPane)row;
            tempGridPane.setMinHeight(CalendarBox.CALENDAR_BOX_MIN_HEIGHT +10);
            tempGridPane.setMinWidth(7*CalendarBox.CALENDAR_BOX_MIN_WIDTH +10);
        }
        rows.add(0,firstRow);

        mainPane.getChildren().addAll(rows);

        this.setCenter(mainPane);

        LayoutAnimator animator = new LayoutAnimator();
        animator.observe(mainPane.getChildren());
    }

    void update(int row, int date) {
        int[] rowIDs = new int[]{1,1,1,1,1,1};
        GridPane tempPane = (GridPane) mainPane.getChildren().get(rowIDs[3]);

        if(currentDate != -1){
            if(date == currentDate) {
                changeButtonColor(getCalendarBox(currentDate).getButtonNode(), false);

                getCalendarBox(currentDate).hideLetterDay();

                removeTaskBox(taskBox);
                currentTextBoxRow = -1;
                currentDate = -1;
            }else if(currentTextBoxRow == rowIDs[row]){
                changeButtonColor(getCalendarBox(currentDate).getButtonNode(), false);

                getCalendarBox(currentDate).hideLetterDay();

                changeButtonColor(getCalendarBox(date).getButtonNode(),true);

                getCalendarBox(date).showLetterDay();

                removeTaskBox(taskBox);
                addTaskBox(currentTextBoxRow, getCalendarBox(date).getTaskBox(tempPane.widthProperty()));
                currentDate = date;
            }else{
                changeButtonColor(getCalendarBox(currentDate).getButtonNode(), false);

                getCalendarBox(currentDate).hideLetterDay();

                removeTaskBox(taskBox);
                currentTextBoxRow = rowIDs[row];
                currentDate = date;
                addTaskBox(currentTextBoxRow, getCalendarBox(date).getTaskBox(tempPane.widthProperty()));
                changeButtonColor(getCalendarBox(date).getButtonNode(),true);

                getCalendarBox(date).showLetterDay();

            }
        }else{
            currentTextBoxRow = rowIDs[row];
            currentDate = date;
            addTaskBox(currentTextBoxRow, getCalendarBox(date).getTaskBox(tempPane.widthProperty()));
            changeButtonColor(getCalendarBox(date).getButtonNode(),true);


            getCalendarBox(date).showLetterDay();
        }
        taskBox = getCalendarBox(date).getTaskBox(tempPane.widthProperty());
//        saveCalendar();
    }

    private void changeButtonColor(JFXButton button, boolean selected) {
        if(selected){
            button.getStyleClass().setAll("box-button-selected");
        }else{
            button.getStyleClass().setAll("box-button");
        }
    }

    private CalendarBox getCalendarBox(int date) {
        CalendarBox currentBox = null;

        for (CalendarBox[] aCalendar : calendar) {
            for (CalendarBox box : aCalendar) {
                if (box != null) {
                    if (box.getDate() == date) {
                        currentBox = box;
                        break;
                    }
                }
            }
            if (currentBox != null) {
                break;
            }
        }
        return currentBox;
    }

    private void addTaskBox(int row, Node taskBoxInstance) {
        mainPane.getChildren().add(row+1, taskBoxInstance);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(1250));
        fadeIn.setNode(taskBoxInstance);

        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setCycleCount(1);
        fadeIn.setAutoReverse(false);
        fadeIn.playFromStart();
    }

    public void hitAllOfTheDabs() { //Call hit that dab for all of the calendar boxes
        for (int i = 0; i < calendar.length; i++) {
            for (int j = 0; j < calendar[i].length; j++) {
                if (calendar[i][j] != null) {
                    calendar[i][j].hitThatDab();
                }
            }
        }
    }

    private void removeTaskBox(Node taskBoxInstance) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(1250));
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

    // saves calendar array as a json file at calendarHolder
    void saveCalendar() {
        try {
            File f = new File(Main.SAVE_FOLDER + File.separator + month + "CalendarHolder.json");
            f.delete();
            f.createNewFile();
            io = new IO(Main.SAVE_FOLDER + File.separator + this.month + "CalendarHolder.json");

            json = io.getJsonApi();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int dayInMonth = 1;
        try{
        //Grabs all the calendar days
        for (int i = 0; dayInMonth <= numberOfDays ; i++) {
                // Gets the active calendar day
                CalendarBox current = calendar[i / 7][i % 7];
            // finds out is the day exisits
            if (current != null) {

                // gets the week and the date of the object
                int currentWeek = current.getWeek();

                int currentDate = current.getDate();
                //gets the tasks in the calendar box
                ArrayList<ArrayList<Task>> currentTaskArrayUnparsedSquared = current.getTasks();
                int sizeOfTasksSquared = currentTaskArrayUnparsedSquared.size();
                for (int j = 0; j < sizeOfTasksSquared; j++) {

                    ArrayList<Task> currentTaskArrayUnparsed = currentTaskArrayUnparsedSquared.get(j);
                    int sizeOfTasks = currentTaskArrayUnparsed.size();

                    int index = 0;
                    for (Task currentTask : currentTaskArrayUnparsed) {

                        if (currentTask.doesExist()) {
                            System.out.println("Does save!");
                            String currentTaskClass = currentTask.getClassName();


                            // checks if class = null
                            if (currentTaskClass == null) {
                                currentTaskClass = " ";
                            }
                            String currentTaskDescription = currentTask.getDescription();

                            // checks if taskDescription = null
                            if (currentTaskDescription == null) {
                                currentTaskDescription = " ";
                            }
                            ArrayList<String> currentTaskArray = new ArrayList<>();
                            currentTaskArray.add(currentTaskClass);
                            currentTaskArray.add(currentTaskDescription);
                            System.out.println("Current Desc. = -" + currentTaskDescription);
                            json.writeArray("CalendarSaver" + dayInMonth + ":" + j + ":" + index, currentTaskArray.toArray());
                            index++;
                        }
                    }
                }
                // make an array of values to save from the current calendarbox
                ArrayList<Integer> currentBoxArray = new ArrayList<>();
                currentBoxArray.add(currentDate);
                currentBoxArray.add(currentWeek);

                json.writeArray("CalendarSaver" + dayInMonth, currentBoxArray.toArray());
                dayInMonth++;
            }
        }
        json.unloadFile();
    }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Saves the month as a json file
     */
    public void save() {
        try {
            File file = new File(filePath);
            if(file.exists()) { //Delete the save file if it exists
                file.delete();
            }
            file.createNewFile(); //Create a save file
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Init json file
        try{
            io = new IO(filePath);
            json = io.getJsonApi();
        }catch (Exception e){
            e.printStackTrace();
        }

        //Actual Save Function
        try {
            json.writePair("Version",Main.VERSION_NUMBER); //Record version number for future reference

            for (int dayIndex = 1; dayIndex <= numberOfDays; dayIndex++) { //Counter to hit every day of the month (Ex. 1-31)
                int gridIndex = dayIndex + startDay - 2; //Which number box in the calendar grid (Ex. 0 means 1st box)
                CalendarBox current = calendar[gridIndex / 7][gridIndex % 7]; //Get the CalendarBox of the corresponding date

                ArrayList<ArrayList<Task>> taskLists = current.getTasks(); //List of all the different lists of tasks (Ex. Homework, test, project, etc.)
                for (int taskListIndex = 0; taskListIndex < taskLists.size(); taskListIndex++) { //Loops through those lists
                    ArrayList<Task> taskList = taskLists.get(taskListIndex); //List of all the tasks (Ex. Task1, task2, task3, etc.)
                    for (int taskIndex = 0; taskIndex < taskList.size(); taskIndex++) { //Loops through those tasks
                        Task task = taskList.get(taskIndex); //Grabs corresponding task

                        //----SAVE METHOD----
                        //Data is stored as: CLASS, DESCRIPTION, HIDDEN DATA
                        //Tags are labeled as: M (month), D (date), L (List ID), T (Task ID), I (Data ID/)
                        String[] taskAsString = {task.getClassName(), task.getDescription(), task.generateHiddenData()};
                        json.writeArray("M" + (this.month + 1) + "-D" + current.getDate() + "-L" + taskListIndex + "-T" + taskIndex + "-I", taskAsString);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            json.unloadFile();
        }
    }


    public void load() throws OldSaveVersionException{
        try {
            File file = new File(filePath);
            if(!file.exists()) {
                throw new OldSaveVersionException();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Init json file
        try{
            io = new IO(filePath);
            json = io.getJsonApi();
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            String rawVersion = json.readPair("Version").toString();
        }catch (Exception e){
            throw new OldSaveVersionException();
        }

        calendar = new CalendarBox[6][7];

        for (int dayIndex = 1; dayIndex <= numberOfDays; dayIndex++) { //Counter to hit every day of the month (Ex. 1-31)
            int gridIndex = dayIndex + startDay - 2; //Which number box in the calendar grid (Ex. 0 means 1st box)

            //Creation of CalendarBox
            ArrayList<ArrayList<Task>> taskLists = new ArrayList<>();

            boolean finishedTaskLists = false;
            int taskListIndex = 0;

            while (!finishedTaskLists) {

                boolean finishedTaskList = false;
                int taskIndex = 0;
                ArrayList<Task> taskList = new ArrayList<>();

                while (!finishedTaskList) {
                    try {
                        String key = "@M" + (this.month + 1) + "-D" + dayIndex + "-L" + taskListIndex + "-T" + taskIndex + "-I";
                        Object[] rawTask = json.readArray(key).toArray();
                        String[] taskAsString = new String[rawTask.length];
                        for (int i = 0; i < rawTask.length; i++) {
                            String raw = (String) (rawTask[i]);
                            raw = raw.substring((key+i+": ").length()-1); //-1 because the array keys don't have an "@"
                            if(raw.equals("null")){
                                raw = "";
                            }
                            taskAsString[i] = raw;
                        }
                        Task task = new Task(taskAsString[0],taskAsString[1],taskAsString[2]);
                        System.out.println("Class:" + task.getClassName()+" Description:"+task.getDescription()+" Hidden Data:"+task.generateHiddenData());
                        taskList.add(task);
//                        String[] taskAsString = {task.getClassName(), task.getDescription(), task.generateHiddenData()};
//                        json.writeArray("M" + (this.month + 1) + "-D" + current.getDate() + "-L" + taskListIndex + "-T" + taskIndex + "-I", taskAsString);

                    } catch (Exception e) {
                        finishedTaskList = true;
                        if (taskList.size() == 0) {
                            finishedTaskLists = true;
                        }
                    }
                    taskIndex++;
                }

                taskLists.add(taskList);
                taskListIndex++;
            }

            CalendarBox box = new CalendarBox(dayIndex,gridIndex / 7,true,taskLists,this.month,this);
            calendar[gridIndex / 7][gridIndex % 7] = box; //Get the CalendarBox of the corresponding date
        }
    }
}
