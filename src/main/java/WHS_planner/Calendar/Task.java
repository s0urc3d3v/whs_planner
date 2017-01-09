package WHS_planner.Calendar;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * Created by geoffrey_wang on 9/20/16.
 */
public class Task {
    public String Class, Title, Description;
    private Boolean doesExist = true;

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

    public Pane getPane(){
        HBox pane = new HBox();
        pane.setMinHeight(30);
        pane.setStyle("-fx-background-color:#c2d7f9;");
        pane.setAlignment(Pos.CENTER_LEFT);
        Text label = new Text(Title + " " + Class + " " + Description);
        pane.getChildren().add(label);

        pane.setOnMouseClicked((event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                doesExist = false;
                label.setStrikethrough(true);
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

    public Boolean doesExist() {
        return doesExist;
    }
}
