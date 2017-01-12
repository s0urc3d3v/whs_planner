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
            JFXButton nextButton = new JFXButton(">");

//            JFXButton nextButton = createIconButton("\uf054");
            nextButton.setOnMouseClicked(event -> {
                if(month == 11) {
                    month = 0;
                }else{
                    month ++;
                }
                changeMonth(month);
            });

            JFXButton prevButton = new JFXButton("<");
//            JFXButton prevButton = createIconButton("\uf053");
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

    public static JFXButton createIconButton(String iconName) {
        return createIconButton(iconName, "", 16);
    }

    public static JFXButton createIconButton(String iconName, String text, int iconSize) {
        InputStream font = MainUI.class.getResourceAsStream("/FontAwesome/fontawesome.ttf");
        Font.loadFont(font, 10);

        Label icon = createIconLabel(iconName, 16);
        icon.setStyle("-fx-font-size: " + iconSize + "px;");
        JFXButton button = new JFXButton();
        button.setGraphic(icon);
        return button;
    }

    public static Label createIconLabel(String iconName, int iconSize) {

        Label label = new Label();
        label.setText(iconName);
        label.getStyleClass().add("icons");
        label.setStyle("-fx-font-size: " + iconSize + "px;");

        return label;

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
