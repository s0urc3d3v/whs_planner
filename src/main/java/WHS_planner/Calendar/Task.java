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

/**
 * Created by geoffrey_wang on 9/20/16.
 */
public class Task {
    private final static String ICON_DELETE = "\uf057";
    private final static String ICON_BACK = "\uf137";
    private final static String ICON_UNDO = "\uf0e2";


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

        isEditing = false;

        JFXButton deleteButton = new JFXButton();
//        deleteButton.setPadding(new Insets(0,3,2,0));
        setButtonToDelete(deleteButton);
        deleteButton.setMinHeight(20);

//        JFXButton cancelButton = new JFXButton("Cancel");
        JFXButton cancelButton = new JFXButton(ICON_BACK);

//        cancelButton.setPrefWidth(50);
        cancelButton.setPrefWidth(20);
//        cancelButton.setMinWidth(50);
        cancelButton.setMinWidth(20);

        cancelButton.setMinHeight(20);
//        cancelButton.setStyle("-fx-background-color: #F26650");
        cancelButton.setStyle("-fx-background-color: transparent; -fx-font-family: 'FontAwesome Regular'; -fx-font-size: 20px;");

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
                                    Description = replaceBeginingSpace(textBoxText);
                                    Class = null;
                                    label.setText(Description);
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

    public void setButtonToDelete(JFXButton button){
//        button.setText("\uf00d");
        button.setText(ICON_DELETE);
//        button.setAlignment(Pos.BASELINE_CENTER);

        button.setPrefWidth(20);
        button.setMinWidth(20);
//        button.setStyle("-fx-background-color: rgb(56, 118, 237); -fx-font-family: 'FontAwesome Regular'; -fx-font-size: 18px;");
        button.setStyle("-fx-background-color: transparent; -fx-font-family: 'FontAwesome Regular'; -fx-font-size: 20px;");


    }

    public void setButtonToUndo(JFXButton button){
//        button.setText("Undo");
        button.setText(ICON_UNDO);
//        button.setPrefWidth(50);
//        button.setMinWidth(50);
        button.setPrefWidth(20);
        button.setMinWidth(20);
        button.setStyle("-fx-background-color: transparent; -fx-font-family: 'FontAwesome Regular'; -fx-font-size: 18px;");

//        button.setStyle("-fx-background-color: #F26650;-fx-font-family: 'Roboto'; -fx-font-size: 14px;");
    }
}
