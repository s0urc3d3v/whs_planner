package WHS_planner.Calendar;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * Created by geoffrey_wang on 9/20/16.
 */
public class Task {
    public String Class, Title, Description;

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
        pane.getChildren().add(new Label(Title + " " + Class + " " + Description));
        return pane;
    }
}
