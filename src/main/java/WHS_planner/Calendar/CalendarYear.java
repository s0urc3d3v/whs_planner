package WHS_planner.Calendar;

import com.jfoenix.controls.JFXButton;
import javafx.scene.layout.Pane;
import WHS_planner.Schedule.Schedule;

/**
 * Created by geoffrey_wang on 1/9/17.
 */

public class CalendarYear extends Pane {
    private Calendar[] months = new Calendar[12];
    private int month = new CalendarHelper().getMonth()-1;
    private Schedule sc;

    public CalendarYear(Schedule schedule){
        this.sc = schedule;
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
            months[i] = new Calendar(i, nextButton, prevButton, sc);
        }
        for(Calendar month: months) {
            month.prefHeightProperty().bind(this.heightProperty());
            month.prefWidthProperty().bind(this.widthProperty());
            this.getChildren().setAll(month);
        }

        this.getChildren().setAll(months[month]);
    }

    public Schedule getSchedule()
    {
        return sc;
    }


    private void changeMonth(int month) {
        this.getChildren().setAll(months[month]);
    }

    public void saveCalendar(){
        for(Calendar month: months){
            month.saveCalendar();
        }
    }

//    public void deleteCalendarData()
//    {
//        System.out.println("calendar data called!");
//        File json0 = new File("src"+File.separator+"main"+File.separator+"resources"+File.separator+"Calendar" + File.separator + "0CalendarHolder.json");
//        File json1 = new File("src"+File.separator+"main"+File.separator+"resources"+File.separator+"Calendar" + File.separator + "0CalendarHolder.json");
//        File json2 = new File("src"+File.separator+"main"+File.separator+"resources"+File.separator+"Calendar" + File.separator + "0CalendarHolder.json");
//        File json3 = new File("src"+File.separator+"main"+File.separator+"resources"+File.separator+"Calendar" + File.separator + "0CalendarHolder.json");
//        File json4 = new File("src"+File.separator+"main"+File.separator+"resources"+File.separator+"Calendar" + File.separator + "0CalendarHolder.json");
//        File json5 = new File("src"+File.separator+"main"+File.separator+"resources"+File.separator+"Calendar" + File.separator + "0CalendarHolder.json");
//        File json6 = new File("src"+File.separator+"main"+File.separator+"resources"+File.separator+"Calendar" + File.separator + "0CalendarHolder.json");
//        File json7 = new File("src"+File.separator+"main"+File.separator+"resources"+File.separator+"Calendar" + File.separator + "0CalendarHolder.json");
//        File json8 = new File("src"+File.separator+"main"+File.separator+"resources"+File.separator+"Calendar" + File.separator + "0CalendarHolder.json");
//        File json9 = new File("src"+File.separator+"main"+File.separator+"resources"+File.separator+"Calendar" + File.separator + "0CalendarHolder.json");
//        File json10 = new File("src"+File.separator+"main"+File.separator+"resources"+File.separator+"Calendar" + File.separator + "0CalendarHolder.json");
//        File json11 = new File("src"+File.separator+"main"+File.separator+"resources"+File.separator+"Calendar" + File.separator + "0CalendarHolder.json");
//
//        if(json0.exists()) {
//            System.out.println("json 0 exists!");
//           json0.delete();}
//        if(json1.exists()) {
//            json1.delete();}
//        if(json2.exists()) {
//           json2.delete();}
//        if(json3.exists()) {
//            json3.delete();}
//        if(json4.exists()) {
//            json4.delete();}
//        if(json5.exists()) {
//            json5.delete();}
//        if(json6.exists()) {
//            json6.delete();}
//        if(json7.exists()) {
//            json7.delete();}
//        if(json8.exists()) {
//            json8.delete();}
//        if(json9.exists()) {
//            json9.delete();}
//        if(json10.exists()) {
//            json10.delete();}
//        if(json11.exists()) {
//            json11.delete();}
//        MainPane mp = (MainPane) Main.getMainPane();
//        mp.removeCalendar();
//    }
}
