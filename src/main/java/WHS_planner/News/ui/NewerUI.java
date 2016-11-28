package WHS_planner.News.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class NewerUI extends Application {

    @Override
    public void start(Stage stage) {
        News news = new News();

        Scene scene = new Scene(news.getPane());
        scene.getStylesheets().add(File.separator + "News" + File.separator + "ButtonStyle.css");
        stage.setScene(scene);
        stage.show();
    }
}
