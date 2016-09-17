package WHS_planner.CoreUI;
import javafx.scene.*;
import javafx.stage.*;

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
