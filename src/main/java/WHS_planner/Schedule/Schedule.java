package WHS_planner.Schedule;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

import java.util.Map;

public class Schedule extends Application
{
    @FXML
    Pane rootLayout;

    private Map<String, Object> labels;

    public static void main(String[] args)
    {
        launch(args);
    }


    public void start(Stage PrimaryStage)
    {
        PrimaryStage.setTitle("src/main/resources/Schedule");

        try
        {
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClass().getResource("/Schedule/scheduletest.fxml"));

            rootLayout = loader.load();
            generateSchedule(loader);

            Scene scene = new Scene(rootLayout);

            PrimaryStage.setResizable(true);
            PrimaryStage.setMinHeight(520);
            PrimaryStage.setMinWidth(573);
            PrimaryStage.setScene(scene);
            PrimaryStage.show();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }




    private void generateSchedule(FXMLLoader loader)
    {
        labels = loader.getNamespace();

        for (int i = 0; i < 9; i++)
        {
            String letter;
            switch(i)
            {
                case 0: letter = "A";
                    break;
                case 1: letter = "B";
                    break;
                case 2: letter = "C";
                    break;
                case 3: letter = "D";
                    break;
                case 4: letter = "E";
                    break;
                case 5: letter = "F";
                    break;
                case 6: letter = "G";
                    break;
                case 7: letter = "H";
                    break;
                default: letter = "Time";
                    break;
            }

            for (int j = 1; j <= 6; j++)
            {
                String s = Integer.toString(j);
                Label l = (Label) labels.get(letter+s);
                l.setText(letter+s);
            }
        }
    }
}
