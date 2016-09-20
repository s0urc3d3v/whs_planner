package WHS_planner.CoreUI;
//import javafx.*;
import javafx.scene.*;
import javafx.stage.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

/**
 * shut up git theres nothing wrong with the default file template
 */
public abstract class Page {
    private int WIDTH, HEIGHT;
    private Scene PageScene = new Scene(null, WIDTH, HEIGHT); //null needs to be the root.

    public Page(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
    }

    public abstract void draw(Stage PrimaryStage);

    public void refresh(){
        //Refresh the page

    }

}
