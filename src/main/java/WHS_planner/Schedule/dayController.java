package WHS_planner.Schedule;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class dayController implements Initializable
{

    @FXML
    private GridPane grid;

    private BorderPane[] panes;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        panes = new BorderPane[16];
        for (int i = 0; i < 8; i++) {
            panes[i] = new BorderPane();
            panes[i].setStyle("-fx-background-color: #ffc04c");
            panes[i].setBorder(new Border(new BorderStroke(new Color(1,1,1,1),
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            grid.add(panes[i],0,i);
        }
        for (int i = 0; i < 8; i++) {
            panes[i] = new BorderPane();
            panes[i].setStyle("-fx-background-color: #ffc04c");
            panes[i].setBorder(new Border(new BorderStroke(new Color(1,1,1,1),
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            grid.add(panes[i],1,i);
        }


    }
}
