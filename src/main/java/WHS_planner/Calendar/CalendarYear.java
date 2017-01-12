package WHS_planner.Calendar;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.io.InputStream;

/**
 * Created by geoffrey_wang on 1/9/17.
 */

public class CalendarYear extends Pane {
    private Calendar[] months = new Calendar[12];
    private int month = new CalendarHelper().getMonth()-1;

    public CalendarYear(){
        for (int i = 0; i < 12; i++) {
            JFXButton nextButton = new JFXButton("\uf054");

            nextButton.setOnMouseClicked(event -> {
                if(month == 11) {
                    month = 0;
                }else{
                    month ++;
                }
                changeMonth(month);
            });

            JFXButton prevButton = new JFXButton("\uf053");
            prevButton.setOnMouseClicked(event -> {
                if (month == 0) {
                    month = 11;
                } else {
                    month--;
                }
                changeMonth(month);
            });
            months[i] = new Calendar(i, nextButton, prevButton);
        }
        for(Calendar month: months) {
            month.prefHeightProperty().bind(this.heightProperty());
            month.prefWidthProperty().bind(this.widthProperty());
            this.getChildren().setAll(month);
        }

        this.getChildren().setAll(months[month]);
    }

    private void changeMonth(int month) {
        this.getChildren().setAll(months[month]);
    }

    public void saveCalendar(){
        for(Calendar month: months){
            month.saveCalendar();
        }
    }
}
