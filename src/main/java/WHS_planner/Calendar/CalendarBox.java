package WHS_planner.Calendar;

import WHS_planner.Main;
import WHS_planner.Schedule.ParseCalendar;
import WHS_planner.Schedule.Schedule;
import WHS_planner.Schedule.ScheduleBlock;
import WHS_planner.UI.GlobalTime;
import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by geoffrey_wang on 12/30/16.
 */
public class CalendarBox extends Pane {
    public static final int CALENDAR_BOX_MIN_HEIGHT = 80, CALENDAR_BOX_MIN_WIDTH = 110; //Constant that defines the min size of a CalendarBox
    private static final int HOMEWORK = 0; //List IDs (Default)
    private static final String[] ICONS_UNICODE = new String[]{"\uf0f6","\uf21b"}; //File Icon, Check Icon (Font UNICODE)

    private static final String LOGIN_PROMPT_STRING = "Log in to add Classes!";

    private int date; //The date of the box
    private int week; //The week this box is in

    private ArrayList<ArrayList<Task>> tasks; //List of the lists of tasks
    private StackPane mainPane;
    private VBox taskBar;
    private ScrollPane tasksPane;

    private JFXButton button;
    private VBox vBox;
    private StackPane dateLabelStackPane;
    private Circle dayCircle;
    private Label dateLabel;
    private HBox iconContainer;
    private int month;

    private JFXCheckBox bell2;
//    private JFXCheckBox override;
    private JFXComboBox dropSelect;
    private GlobalTime globalTime;
    private Schedule schedule;
    private Calendar calendar;

    private ParseCalendar pc = new ParseCalendar();
    private File day = new File(Main.SAVE_FOLDER+ File.separator +"DayArray.json");


    public CalendarBox(int date, int week, boolean active, ArrayList<Task> tasks, int month, Calendar cal){

        if(day.exists() && day.length() > 0) { //and if it isnt blank
            pc.readData();
        }


        this.calendar = cal;
        this.schedule = calendar.getSchedule();
        this.bell2 = calendar.getSchedule().getCheck();


        this.date = date; //This box's date
        this.week = week; //The week (row) this box is in
        this.month = month;
        this.globalTime = new GlobalTime(bell2);

        if(tasks == null){
            this.tasks = new ArrayList<>(); //Used to hold lists of tasks (Ex. List of homeworks, list of tests, etc)

            //Creates and fills in tasks with correct amount of lists according to NUMBER_OF_TASKLISTS
            for (String aICONS_UNICODE : ICONS_UNICODE) {
                this.tasks.add(new ArrayList<>()); //Create a new list
            }
        }else{
            this.tasks = new ArrayList<>(); //Used to hold lists of tasks (Ex. List of homeworks, list of tests, etc)

            this.tasks.add(tasks);
        }

        //Creates the entire pane
        mainPane = new StackPane();
        mainPane.setId("stackPane");
        mainPane.setMinSize(CALENDAR_BOX_MIN_WIDTH,CALENDAR_BOX_MIN_HEIGHT);

        button = new JFXButton();
        button.setId("button");
        button.getStyleClass().setAll("box-button");
        button.prefHeightProperty().bind(mainPane.heightProperty());
        button.prefWidthProperty().bind(mainPane.widthProperty());

        mainPane.getChildren().add(button);

        vBox = new VBox();
        vBox.setId("vbox");
        vBox.setMouseTransparent(true);
        vBox.prefHeightProperty().bind(mainPane.heightProperty());
        vBox.prefWidthProperty().bind(mainPane.widthProperty());

        mainPane.getChildren().add(vBox);

        dateLabelStackPane = new StackPane();
        dateLabelStackPane.setId("dateLabelStackPane");
        dateLabelStackPane.setAlignment(Pos.TOP_LEFT);
        dateLabelStackPane.setMouseTransparent(true);

        vBox.getChildren().add(dateLabelStackPane);

        dayCircle = new Circle();
        dayCircle.setId("dayCircle");
        dayCircle.fillProperty().set(Color.WHITE);
        dayCircle.radiusProperty().setValue(10);
        dayCircle.setMouseTransparent(true);
        dayCircle.getStyleClass().setAll("date-circle");

        dateLabelStackPane.getChildren().add(dayCircle);

        dateLabel = new Label();
        dateLabel.setId("dateLabel");
        dateLabel.setMouseTransparent(true);
        dateLabel.getStyleClass().setAll("date-label");

        dateLabelStackPane.getChildren().add(dateLabel);
        StackPane.setMargin(dateLabel, new Insets(-1, 0, 0, 6));

        iconContainer = new HBox();
        iconContainer.setId("iconContainer");
        iconContainer.setAlignment(Pos.CENTER);
        iconContainer.setMouseTransparent(true);
        iconContainer.prefWidthProperty().bind(vBox.widthProperty());
        iconContainer.prefHeightProperty().bind(vBox.heightProperty());

        vBox.getChildren().add(iconContainer);

        this.getChildren().setAll(mainPane);
        mainPane.prefWidthProperty().bind(this.widthProperty());
        mainPane.prefHeightProperty().bind(this.heightProperty());

        //Set up the calendar box
        initFXMLBox();

//        if(this.getDate()==1&&month==8){
//            ArrayList<Task> tempList = new ArrayList<>();
//            tempList.add(new Task("","",""));
//            this.tasks.add(tempList);
//        }

        //Make the button inactive if required
        if(!active){
            button.setDisable(true);
            button.getStyleClass().setAll("box-button-disabled");
            dateLabel.setText("");
        }
        update();
    }

    /*-----METHODS-----*/

    //Initializes this box
    private void initFXMLBox() {
        String dateString = date + ""; //Creates a string version of the date value
        dateLabel.setText(dateString); //Set the dateLabel text = to the date

        //Set the buttonClicked action
        button.setOnMouseClicked((event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if(tasks.size()>1) {
                    if (tasks.get(1).size() == 1) {
                        tasks.get(1).remove(0);
                    }
                }
                update();
                Calendar calendar = (Calendar)this.getParent().getParent().getParent();
                calendar.update(week,date);
                updateTaskBox();

            }
        }));
//        java.util.Calendar javaCalendar = java.util.Calendar.getInstance();
        hitThatDab();
//        else {
//            this.setStyle("-fx-background-color: #FFFFFF");
//        }

        this.getStyleClass().add("box"); //Set the CSS style class to be box
        this.getChildren().setAll(mainPane); //Set this pane to contain the mainPane
        this.setId("calendar-box"); //Set the id of this box to be "calendar-box"

        //Initiate update sequence
        update();
    }

    void hitThatDab() { //dayCircle update
        java.util.Calendar javaCalendar = java.util.Calendar.getInstance();

        int day = javaCalendar.get(java.util.Calendar.DAY_OF_MONTH);
        int month = javaCalendar.get(java.util.Calendar.MONTH);
        if (day == this.getDate() && month == this.month) {
            dayCircle.setFill(new Color(255/255, 152/255.0, 0, 100/100));
        } else {
            dayCircle.setFill(new Color(255/255, 152/255, 0, 0));
        }

        if (this.getDate() >= 10) {
            StackPane sp = dateLabelStackPane;
            StackPane.setMargin(dayCircle, new Insets(0, 0, 0, 4.5));
        }
    }

    //Updates the iconContainer
    void update() {
        ArrayList<Node> icons = new ArrayList<>(); //Create a list for all the icons

        //Loop through all the task lists and add icons according to the content
        for (int listID = 0; listID < tasks.size(); listID++) {
            if(getTaskCount(listID) != 0) { //Only add an icon if there is more than one task
                //Create the ICON
                Label icon = new Label();
                icon.getStyleClass().add("icon");
                icon.setId("icon");
                icon.setText(ICONS_UNICODE[listID]);

                if(!ICONS_UNICODE[listID].equals("\uf21b")) {
                    //Create the badge on the Label
                    JFXBadge badge = new JFXBadge(icon, Pos.TOP_RIGHT);
                    badge.getStyleClass().add("icon-badge");
//                badge.getChildren().get(0).getStyleClass().setAll("testsefd");
                    badge.setText("" + getTaskCount(listID)); //Set the badge number
                    icons.add(badge);
                }else{
                    icons.add(icon);
                }
            }
        }

        iconContainer.getChildren().setAll(icons); //Add all the "icons" into "iconContainer"
    }


    //Create a taskBox
    Node getTaskBox(ReadOnlyDoubleProperty widthProperty) {
        //If there is no taskBox create one
        if(taskBar == null) {
//            FXMLLoader loader = new FXMLLoader(); //Create a new FXML Loader
//            loader.setLocation(getClass().getResource("/Calendar/taskBox.fxml")); //Set location of taskbox FXML file

//            taskBar = new VBox(); //Creates a return taskbox
            taskBar = crankOutTheTaskBox();

            try {
//                taskBar = loader.load(); //Load from FXML
                taskBar.prefWidthProperty().bind(widthProperty); //Set the width of the taskbox to be the same as the width passed in

                VBox vbox = new VBox();
                tasksPane = new ScrollPane();
                tasksPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                vbox.prefWidthProperty().bind(tasksPane.widthProperty());
                tasksPane.setContent(vbox);
                tasksPane.prefWidthProperty().bind(widthProperty);
                tasksPane.setMinHeight(0);
                tasksPane.setMaxHeight(0);
                tasksPane.setStyle("-fx-background-color: transparent;");

                tasksPane.getStylesheets().add("News" + File.separator + "NewsUI.css");
                tasksPane.getStyleClass().setAll("scroll-bar");

//                tasksPane.getStyleClass().addAll("clear-select");

                if(taskBar.getChildren().size() != 2){
                    taskBar.getChildren().add(1,tasksPane);
                }

                //Get the JFXTextField and set the width to grow
                HBox hBox = (HBox) taskBar.getChildren().get(0);
                JFXTextField textBox = (JFXTextField) hBox.getChildren().get(0);

                HBox.setHgrow(textBox, Priority.ALWAYS);

                //Code for the Checkbox
//                override = (JFXCheckBox) hBox.getChildren().get(1);
                dropSelect = (JFXComboBox) hBox.getChildren().get(1);
//                System.out.println(dropSelect);

                //Set pressing enter to clear the box text
                textBox.setOnKeyPressed(event -> {

                    if (event.getCode() == KeyCode.ENTER) {


                        String textBoxText = textBox.getText();
                        if (textBoxText.trim().length() > 0){
                            int classIndex = globalTime.getClassIndex();
                            String today = (java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1) + "/" + java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH);
//                            System.out.println("length" + pc.getDay(today).length());
//                            System.out.println(override.isSelected());
//                            System.out.println("Class Index: " + classIndex);
//                            System.out.println("Logged in: " + schedule.isLoggedIn());
                            day = new File(Main.SAVE_FOLDER + File.separator + "DayArray.json");
                            System.out.println("dropSelect value: "+ dropSelect.getValue());
//                            if(!dropSelect.getValue().equals("None") || !dropSelect.getValue().equals(LOGIN_PROMPT_STRING)|| !dropSelect.getValue().equals("Current Class")) {
//                            System.out.println("INDEX: " + dropSelect.getSelectionModel().getSelectedIndex());
//                            if(!(dropSelect.getSelectionModel().getSelectedIndex() == 0 )|| !(dropSelect.getSelectionModel().getSelectedIndex() == 1)) {
                            if(dropSelect.getSelectionModel().getSelectedIndex() > 1) {
//                                System.out.println("CASE 1");

                                String currentClass = dropSelect.getValue().toString();
//                                schedule = calendar.getSchedule();
//                                currentClass = schedule.getToday(pc.getDay(today))[classIndex].getClassName();
                                addTask(HOMEWORK, new Task(currentClass, "", textBoxText));
                                update();
                                updateTaskBox();
//                                String currentClass = schedule.getData()[classIndex].getClassName();
//                                String currentClass = schedule.getToday(globalTime.getLetterDay())[classIndex].getClassName();

                            } else {
                                if (day.exists() && day.length() > 0 && schedule.isLoggedIn()) { //day exists even when you log out
//                                     pc.readData();
                                    pc.readData();
                                    System.out.println("day length: " + day.length());
                                    //There is school              dropdown isnt none        during school hours (unreliable when there's no school)
//                                    if ((pc.getDay(today).length() == 1 && override.isSelected() && classIndex != -1)) {
                                    System.out.println(dropSelect.getValue().toString());
                                    if (classIndex != -1 && pc.getDay(today).length() == 1 && (!dropSelect.getValue().equals("None") && !dropSelect.getValue().equals("Login to add Classes!"))) {

//                                        System.out.println(pc.getDay(today));
//                                        System.out.println(override.isSelected());
//                                        System.out.println(classIndex);


                                        if (classIndex == -2) { //wednesday advisory
                                            addTask(HOMEWORK, new Task("Advisory", "", textBoxText));
                                            update();
                                            updateTaskBox();
                                        } else if (classIndex == -3) { // bell 2 class meeting
                                            addTask(HOMEWORK, new Task("Class Meeting", "", textBoxText));
                                            update();
                                            updateTaskBox();
                                        } else if (dropSelect.getValue().equals("Current Class")) { //Current class
                                            System.out.println(dropSelect.getValue().toString());
//                                            System.out.println("CASE 2: CURRENT CLASS");

                                            String currentClass = "";
                                            schedule = calendar.getSchedule();
                                            currentClass = schedule.getToday(pc.getDay(today))[classIndex].getClassName();
                                            addTask(HOMEWORK, new Task(currentClass, "", textBoxText));
                                            update();
                                            updateTaskBox();
                                        }
                                    } else //add it without class!
                                    {
//                                        System.out.println("CASE 3: no class 1");

                                        addTask(HOMEWORK, new Task(null , "", textBoxText));
                                        update();
                                        updateTaskBox();
                                    }
                                } else {
//                                    System.out.println("CASE 4: no class 2");

                                    addTask(HOMEWORK, new Task(null, "", textBoxText));
                                    update();
                                    updateTaskBox();
                                }
                            }

                        }
                        textBox.clear();
                    } else if (event.getCode() == KeyCode.ESCAPE) {
                        textBox.clear();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return taskBar;
    }

    private VBox crankOutTheTaskBox()
    {
        HBox hungryBox = new HBox();
        VBox taskVBox = new VBox(hungryBox);

//        dropDown.getItems().addAll("Current Class", "None");
        ObservableList<String> menuItems = FXCollections.observableArrayList(LOGIN_PROMPT_STRING);

//        ComboBox dropDown = new ComboBox(menuItems);
        JFXComboBox dropDown = new JFXComboBox(menuItems);
//        dropDown.setMaxWidth(200);
        dropDown.setMinWidth(250);
//        dropDown.setPrefHeight(480);
        dropDown.setVisibleRowCount(8);

//            String[] s = pc.getClassArray();




        String today0 = (java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1) + "/" + java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH);
        schedule = calendar.getSchedule();
        if(day.exists() && day.length() > 0 && schedule.isLoggedIn()){
//            ScheduleBlock[] sb = schedule.getToday(pc.getDay(today0));
            ScheduleBlock[] fullSchedule = schedule.getData();
            String[] sb = new String[fullSchedule.length];
            for (int i = 0; i < fullSchedule.length; i++) {
                sb[i] = fullSchedule[i].getClassName();
            }
            String[] allClasses = Arrays.stream(sb).distinct().toArray(String[]::new);
//            List<String> allClasses = Arrays.asList(Arrays.stream(sb).distinct().toArray(String[]::new));
//            allClasses.remove(" Free");
            menuItems.clear();
            menuItems.add("Current Class");
            menuItems.add("None");
            for (String value : allClasses) {
                //removes the random space before every class
                if(!value.substring(1,value.length()).equalsIgnoreCase("free")){
                    menuItems.add(value.substring(1,value.length()));
                }
            }
            System.out.println(menuItems);
        } else {
            menuItems.clear();
            menuItems.add(LOGIN_PROMPT_STRING);
        }

        dropDown.getSelectionModel().selectFirst();

        dropDown.getStylesheets().setAll("UI" + File.separator + "comboBox2.css");
        dropDown.getStyleClass().setAll("combo-box-popup");
//        dropDown.getStyleClass().addAll("combo-box-base");

        dropDown.setOnMouseClicked(event -> {




//            String[] s = pc.getClassArray();
            String today = (java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1) + "/" + java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH);
            schedule = calendar.getSchedule();

            if(day.exists() && day.length() > 0 && schedule.isLoggedIn()) {
//                ScheduleBlock[] s = schedule.getToday(pc.getDay(today));
//                ScheduleBlock[] s = schedule.getData();
                ScheduleBlock[] fullSchedule = schedule.getData();
                String[] sb = new String[fullSchedule.length];
                for (int i = 0; i < fullSchedule.length; i++) {
                    sb[i] = fullSchedule[i].getClassName();
                }

                String[] allClasses = Arrays.stream(sb).distinct().toArray(String[]::new);
//                List<String> allClasses = Arrays.asList(Arrays.stream(sb).distinct().toArray(String[]::new));
//                allClasses.remove(" Free");

                menuItems.clear();
                menuItems.add("Current Class");
                menuItems.add("None");
                for (String value : allClasses) {
                    //removes the random space before every class
                    if(!value.substring(1,value.length()).equalsIgnoreCase("free")) {
                        menuItems.add(value.substring(1, value.length()));
                    }
                }
                dropDown.setVisibleRowCount(8);

            } else {
                menuItems.clear();
                menuItems.add(LOGIN_PROMPT_STRING);
            }


            dropDown.getSelectionModel().selectFirst();


            //Cool code that doesn't work :(
//            final Node listView = dropDown.lookup(".list-view");
//            System.out.println(dropDown.lookup(".list-view"));
//            Platform.runLater(()-> listView.setStyle("-fx-translate-y: -"+ (dropDown.getSelectionModel().getSelectedIndex() +1)*25 + ";"));
        });


//        override = new JFXCheckBox();
//        override.setText("Use Current Class");
        hungryBox.getStylesheets().setAll("UI" + File.separator + "dropDown.css");


//        dropDown.getStyleClass().set()
//        override.getStyleClass().setAll("label-button");
//        override.setCheckedColor(Paint.valueOf("#0066FF"));
//        override.setSelected(true);
//        override.setCursor(Cursor.HAND);
//        override.setPrefSize(155,24);

        JFXTextField textBox = new JFXTextField();
        textBox.setPromptText("Enter Task...");
        Platform.runLater(textBox::requestFocus);
        textBox.setCursor(Cursor.TEXT);
        textBox.getStyleClass().setAll("roboto");
        try {
            textBox.setOnKeyPressed(keyEvent -> {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    textBox.clear();
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }

//        hungryBox.getChildren().addAll(override,textBox);
        hungryBox.getChildren().addAll(textBox,/*override,*/dropDown);


        return taskVBox;
    }



    private void updateTaskBox() {
        VBox vbox = (VBox)tasksPane.getContent();
        vbox.getChildren().clear();
        int height = 0;
        for (int i = 0; i < tasks.get(0).size(); i++) {
            if (tasks.get(0).get(i).doesExist()){
                Pane tempPane = tasks.get(0).get(i).getPane(this);
                vbox.getChildren().add(0, tempPane);

//                if(getTaskCount(0)<4) {
//                    FadeTransition fadeIn = new FadeTransition(Duration.millis(1250));
//                    fadeIn.setNode(tempPane);
//
//                    fadeIn.setFromValue(0.0);
//                    fadeIn.setToValue(1.0);
//                    fadeIn.setCycleCount(1);
//                    fadeIn.setAutoReverse(false);
//                    fadeIn.playFromStart();
//                }

                if (height < 90) {
                    height+= 30;
                }
            }
        }
        tasksPane.setMinHeight(height);
        tasksPane.setMaxHeight(height);
    }

    /*-----ID RELATED-----*/
    //Get the date of this box
    public int getDate() {
        return date;
    }

    //Get the week this box is in (row)
    int getWeek() {
        return week;
    }

    /*-----TASK RELATED-----*/
    //Used to get the number of tasks in a certain list
    private int getTaskCount(int listID) {
        int returnValue = 0;
        for (int i = 0; i < tasks.get(listID).size(); i++) {
            if (tasks.get(listID).get(i).doesExist()) {
                returnValue++;
            }
        }
        return returnValue;
    }

    //Adds a task in a certain list based on the listID
    private void addTask(int listID, Task task) {
        tasks.get(listID).add(task);
    }

    //Removes a task in a certain list based on the listID
    public void removeTask(int listID, Task task){
        tasks.get(listID).remove(task);
    }

    /*-----NODE RELATED-----*/
    //Get the button
    JFXButton getButtonNode() {
        return button;
    }

    //Get the date Label
    public Label getDateLabel(){
        return dateLabel;
    }


    // Tzurs Code
    // Restart related

    ArrayList<ArrayList<Task>> getTasks() {
        return tasks;
    }
}
