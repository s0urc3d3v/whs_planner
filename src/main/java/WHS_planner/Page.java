package main.java.WHS_planner;
import javafx.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by tyler_brient on 9/15/16.
 */
public abstract class Page {
    private int WIDTH, HEIGHT;
    private Scene PageScene = new Scene(null, WIDTH, HEIGHT); //null needs to be the parent. Not really sure what this means yet...

    public Page(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
    }

    public abstract void draw(Stage PrimaryStage);

    public void refresh(){
        //Refresh the page
    }

}
