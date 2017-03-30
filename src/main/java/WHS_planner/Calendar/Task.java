package WHS_planner.Calendar;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import java.io.File;

public class Task {
    private final static String ICON_DELETE = "\uf057";
    private final static String ICON_UNDO = "\uf0e2";
    private final static String ICON_BACK = "\uf137";
    private final static String ICON_SQUARE = "\uf096";
    private final static String ICON_CHECK = "\uf046";

    private String className = "", description = "";
    private Boolean doesExist = true, isEditing = false;

    public Task(String className, String description){
        this.className = className;
        this.description = description;
    }

    public Task(String className, String description, String hiddenData){
        this.className = className;
        this.description = description;

        //Checks if task is deleted
        if(hiddenData.substring(0,1).equalsIgnoreCase("d")){
            doesExist = false;
        }
    }

    Pane getPane(CalendarBox box){
        HBox pane = new HBox();

        pane.setMinHeight(30);
        pane.getStylesheets().add("Calendar" + File.separator + "MainUI.css");
        pane.getStyleClass().add("task-pane");
        pane.setAlignment(Pos.CENTER_LEFT);

        Text label;

        Text spaces = new Text("  ");
        Text spaces2 = new Text("  ");

        String tester = className;

        if (className == null|| className.isEmpty()||tester.replaceAll(" ", "").length() == 0|| className.equals("")) { //If there is no class
            description = replaceBeginningSpace(description);
            label = new Text(description);
            label.setBoundsType(TextBoundsType.VISUAL);
        } else { //If there is a class
            description = replaceBeginningSpace(description);
            className = replaceBeginningSpace(className);
            label = new Text(className + ": " + description);
            label.setBoundsType(TextBoundsType.VISUAL);
        }

        isEditing = false;

        JFXButton deleteButton = new JFXButton();
        setButtonToDelete(deleteButton);
        deleteButton.setMinHeight(20);

        JFXButton cancelButton = new JFXButton(ICON_BACK);

        cancelButton.setPrefWidth(20);
        cancelButton.setMinWidth(20);

        cancelButton.setMinHeight(20);
        cancelButton.setStyle("-fx-background-color: transparent; -fx-font-family: 'FontAwesome Regular'; -fx-font-size: 20px;");

        if (!doesExist) {
            setButtonToUndo(deleteButton);
            label.setStrikethrough(true);
        } else {
            setButtonToDelete(deleteButton);
            label.setStrikethrough(false);
        }

        deleteButton.setOnMouseClicked((event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (doesExist) {
                    doesExist = false;
                    setButtonToUndo(deleteButton);
                    label.setStrikethrough(true);
                } else {
                    doesExist = true;
                    setButtonToDelete(deleteButton);
                    label.setStrikethrough(false);
                }
                box.update();
            }
        }));

        pane.setOnMouseClicked((event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if(doesExist) {
                    if (!isEditing) {
                        isEditing = true;
                        JFXTextField textbox = new JFXTextField();
                        HBox.setHgrow(textbox, Priority.ALWAYS);
                        textbox.setText(label.getText());
                        pane.getChildren().setAll(spaces, cancelButton, spaces2, textbox);

                        textbox.setOnKeyPressed(textBoxEvent -> {
                            if (textBoxEvent.getCode() == KeyCode.ENTER) {
                                String textBoxText = textbox.getText();
                                if (textBoxText.trim().length() > 0) {
                                    description = replaceBeginningSpace(textBoxText);
                                    className = null;
                                    label.setText(description);
                                    isEditing = false;
                                    pane.getChildren().setAll(spaces, deleteButton, spaces2, label);
                                }
                            }
                        });
                    }
                }
            }
        }));

        cancelButton.setOnMouseClicked((event -> {
            if(event.getButton() == MouseButton.PRIMARY){
                isEditing = false;
                pane.getChildren().setAll(spaces,deleteButton,spaces2,label);
            }
        }));

        pane.getChildren().addAll(spaces,deleteButton,spaces2,label);

        return pane;
    }

    public Pane getPane(){
        HBox pane = new HBox();

        pane.setMinHeight(30);
        pane.getStylesheets().add("Calendar" + File.separator + "MainUI.css");
        pane.getStyleClass().add("task-pane");
        pane.setAlignment(Pos.CENTER_LEFT);

        Text label;

        Text spaces = new Text("  ");
        Text spaces2 = new Text("  ");

        String tester = className;

        if (className == null|| className.isEmpty()||tester.replaceAll(" ", "").length() == 0|| className.equals("")) { //If there is no class
            description = replaceBeginningSpace(description);
            label = new Text(description);
            label.setBoundsType(TextBoundsType.VISUAL);
        } else { //If there is a class
            description = replaceBeginningSpace(description);
            className = replaceBeginningSpace(className);
            label = new Text(className + ": " + description);
            label.setBoundsType(TextBoundsType.VISUAL);
        }

        isEditing = false;

        JFXButton deleteButton = new JFXButton();
        setButtonToDelete(deleteButton);
        deleteButton.setMinHeight(20);

        JFXButton cancelButton = new JFXButton(ICON_BACK);

        cancelButton.setPrefWidth(20);
        cancelButton.setMinWidth(20);

        cancelButton.setMinHeight(20);
        cancelButton.setStyle("-fx-background-color: transparent; -fx-font-family: 'FontAwesome Regular'; -fx-font-size: 20px;");

        if (!doesExist) {
            setButtonToUndo(deleteButton);
            label.setStrikethrough(true);
        } else {
            setButtonToDelete(deleteButton);
            label.setStrikethrough(false);
        }

        deleteButton.setOnMouseClicked((event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (doesExist) {
                    doesExist = false;
                    setButtonToUndo(deleteButton);
                    label.setStrikethrough(true);
                } else {
                    doesExist = true;
                    setButtonToDelete(deleteButton);
                    label.setStrikethrough(false);
                }
            }
        }));

        pane.setOnMouseClicked((event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if(doesExist) {
                    if (!isEditing) {
                        isEditing = true;
                        JFXTextField textbox = new JFXTextField();
                        HBox.setHgrow(textbox, Priority.ALWAYS);
                        textbox.setText(label.getText());
                        pane.getChildren().setAll(spaces, cancelButton, spaces2, textbox);

                        textbox.setOnKeyPressed(textBoxEvent -> {
                            if (textBoxEvent.getCode() == KeyCode.ENTER) {
                                String textBoxText = textbox.getText();
                                if (textBoxText.trim().length() > 0) {
                                    description = replaceBeginningSpace(textBoxText);
                                    className = null;
                                    label.setText(description);
                                    isEditing = false;
                                    pane.getChildren().setAll(spaces, deleteButton, spaces2, label);
                                }
                            }
                        });
                    }
                }
            }
        }));

        cancelButton.setOnMouseClicked((event -> {
            if(event.getButton() == MouseButton.PRIMARY){
                isEditing = false;
                pane.getChildren().setAll(spaces,deleteButton,spaces2,label);
            }
        }));

        pane.getChildren().addAll(spaces,deleteButton,spaces2,label);

        return pane;
    }

    private String replaceBeginningSpace(String string){
        for (int i = 0; i < string.length(); i++) {
            if (string.substring(0,1).equals(" ")) {
                string = string.substring(1, string.length());
            } else {
                break;
            }
        }
        return string;
    }

    public Boolean doesExist() {
        return doesExist;
    }

    private void setButtonToDelete(JFXButton button){
        button.setText(ICON_SQUARE);
        button.setStyle("-fx-background-color: transparent; -fx-font-family: 'FontAwesome Regular'; -fx-font-size: 20px;");
    }

    private void setButtonToUndo(JFXButton button){
        button.setText(ICON_CHECK);
        button.setStyle("-fx-background-color: transparent; -fx-font-family: 'FontAwesome Regular'; -fx-font-size: 18px;");
    }

    String getClassName() {
        return className;
    }

    String getDescription() {
        return description;
    }

    String generateHiddenData(){
        String hiddenData = "";
        if(doesExist){
            hiddenData += "e";
        }else{
            hiddenData += "d";
        }
        return hiddenData;
    }
}
