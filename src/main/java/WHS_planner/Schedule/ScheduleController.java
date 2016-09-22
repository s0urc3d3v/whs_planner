package WHS_planner.Schedule;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ScheduleController implements Initializable
{
    @FXML
    private GridPane grid;

    @FXML
    private Label Title;

    //Unfortunately this is necessary if we don't want to break java or reformat the fxml document
    @FXML
    private Label A1, A2, A3, A4, A5, A6;

    @FXML
    private Label B1, B2, B3, B4, B5, B6;

    @FXML
    private Label C1, C2, C3, C4, C5, C6;

    @FXML
    private Label D1, D2, D3, D4, D5, D6;

    @FXML
    private Label E1, E2, E3, E4, E5, E6;

    @FXML
    private Label F1, F2, F3, F4, F5, F6;

    @FXML
    private Label G1, G2, G3, G4, G5, G6;

    @FXML
    private Label H1, H2, H3, H4, H5, H6;

    @FXML
    private Label Time1, Time2, Time3, Time4, Time5, Time6;



    String classes[][] = new String[9][6];




    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        //we can set the day here
        Title.setText("It is A Day");

        //test subjects
        classes[1][0] = "memes";
        classes[2][0] = "dank memes";

        //examples of setting the text
        A1.setText(classes[1][0]);
        A2.setText(classes[2][0]);

    }
}
