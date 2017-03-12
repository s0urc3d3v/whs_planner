package WHS_planner.Calendar;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import java.io.File;

/**
 * Created by geoffrey_wang on 9/20/16.
 */
public class Task {
    String Class, Title, Description;
    private Boolean doesExist = true;
    private Boolean isEditing = false;

    public Task(String class1,String title1, String description1){
        Class = class1;
        Title = title1;
        Description = description1;
    }

    public void changeClass(String class1){
        Class = class1;
    }
    public void changeTitle(String title1){
        Title = title1;
    }
    public void changeDescription(String description1){
        Description = description1;
    }

    public Pane getPane(CalendarBox box){
        HBox pane = new HBox();
//        StackPane pane = new StackPane();

        pane.setMinHeight(30);
        pane.getStylesheets().add("Calendar" + File.separator + "MainUI.css");
        pane.getStyleClass().add("task-pane");
        pane.setAlignment(Pos.CENTER_LEFT);

        Text label;

        Text spaces = new Text("  ");
        Text spaces2 = new Text("  ");

        System.out.println("CLASS: " + Class);
        String tester = Class;
        if (Class == null||Class.isEmpty()||tester.replaceAll(" ", "").length() == 0||Class.equals("")) //If there is no class
        {
            Description = replaceBeginingSpace(Description);
            label = new Text(Description);
            label.setBoundsType(TextBoundsType.VISUAL);
        }
        else //If there is a class
        {
            Description = replaceBeginingSpace(Description);
            Class = replaceBeginingSpace(Class);
            label = new Text(Class + ": " + Description);
            label.setBoundsType(TextBoundsType.VISUAL);
        }

        JFXButton editButton = new JFXButton("Edit");
        isEditing = false;
        editButton.setPrefWidth(50);
        editButton.setMinWidth(50);
        editButton.setOnMouseClicked((event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                isEditing = !isEditing;

                if (isEditing) {
                    editButton.setText("Cancel");
                    pane.getChildren().remove(pane.getChildren().size() - 1);
                    JFXTextField textbox = new JFXTextField();
                    pane.setHgrow(textbox, Priority.ALWAYS);
                    textbox.setText(label.getText());
                    pane.getChildren().add(textbox);

                    textbox.setOnKeyPressed(textBoxEvent -> {
                        if (textBoxEvent.getCode() == KeyCode.ENTER) {
                            String textBoxText = textbox.getText();
                            if (textBoxText.trim().length() > 0) {
                                isEditing = false;
                                Description = replaceBeginingSpace(textBoxText);
                                Class = null;
                                label.setText(Description);
                                pane.getChildren().remove(pane.getChildren().size() - 1);
                                pane.getChildren().add(label);
                                editButton.setText("Edit");
                            }
                        }
                    });
                }else{
                    pane.getChildren().remove(pane.getChildren().size() - 1);
                    pane.getChildren().add(label);
                    editButton.setText("Edit");
                }

            }
        }));

        editButton.setStyle("-fx-background-color: #FF9800");

        pane.getChildren().add(spaces);
        pane.getChildren().add(editButton);
        pane.getChildren().add(spaces2);
        pane.getChildren().add(label);

        pane.setOnMouseClicked((event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if(!isEditing) {
                    if (doesExist) {
                        doesExist = false;
                        pane.getChildren().remove(editButton);
                        pane.getChildren().remove(spaces2);
                        label.setStrikethrough(true);
                    } else {
                        doesExist = true;
                        pane.getChildren().add(1, editButton);
                        pane.getChildren().add(2, spaces2);
                        label.setStrikethrough(false);
                    }
                }
                box.update();
            }
        }));

//        pane.setOnMouseEntered((event -> {
//            if (event.getButton() == MouseButton.PRIMARY) {
//                label.setStrikethrough(true);
//            }
//        }));
//        pane.setOnMouseExited((event -> {
//            if (event.getButton() == MouseButton.PRIMARY) {
//                label.setStrikethrough(true);
//            }
//        }));


        return pane;
    }

    public String replaceBeginingSpace(String string){
        for (int i = 0; i < string.length(); i++) {
            if (string.substring(0,1).equals(" ")) {
                string = string.substring(1, string.length());
            } else {
                break;
            }
        }
        return string;
    }

    Boolean doesExist() {
        return doesExist;
    }
}
