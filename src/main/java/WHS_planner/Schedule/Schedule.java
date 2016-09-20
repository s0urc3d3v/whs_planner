package WHS_planner.Schedule;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.stage.Stage;

import static javafx.application.Application.launch;


public class Schedule extends Application
{
    public Schedule()
    {

    }

    public static void main(String[] args) {
        launch(args);
    }



    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("Drawing Operations Test");
        Group root = new Group();
        Canvas canvas = drawSchedule(800, 800);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public Canvas drawSchedule(int w, int h)
    {
        Canvas canvas = new Canvas(w, h);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //gc.setLineWidth(2);

        //makes horizontal lines
        for (int i = 0; i < 9; i++)
        {
            gc.strokeLine(0, (i*(h/8)), w, (i*(h/8)));
        }

        //vertical lines
        for (int j = 0; j < 10; j++)
        {
            gc.strokeLine((j*(w/9)), 0, (j*(w/9)), h);
        }


        return canvas;
    }

}
