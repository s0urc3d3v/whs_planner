package WHS_planner.CoreUI;

import WHS_planner.Calendar.Calendar;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MainDocumentController implements Initializable {

    Calendar cal;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Pane topBar;

    @FXML
    private JFXHamburger navHamburger;

    @FXML
    private JFXDrawer navDrawer;

    @FXML
    private Rectangle rect;

    @FXML
    private JFXButton sportsScheduleButton;

    private HamburgerBackArrowBasicTransition burgerTransition;

    private NavigationBar main;

    private boolean sportsScheduleOpen;

    public MainDocumentController(){
        main = new NavigationBar();
    }


    public void initialize(URL location, ResourceBundle resources) {
        cal = new Calendar(1, 30);
        cal.setPrefSize(anchorPane.getPrefWidth(),anchorPane.getPrefHeight());

        topBar.setStyle("-fx-background-color: #FF9800");
        navHamburger.getStylesheets().add("/CoreUI/ButtonUI.css");
        navHamburger.getStyleClass().add("jfx-hamburger-icon");
        String[] tabs = new String[]{"Calendar", "Schedule", "Homework", "Tests", "Schedule", "George"}; //Need to find a way to get this from another class,
        //Create the VBox                                                                            //May need to be an array of classes that extend tab, so you can get an fxml file related to them and their name.
        JFXButton[] buttonArray = new JFXButton[tabs.length];
        for (int i = 0; i < tabs.length; i++) {
            JFXButton tempButton = new JFXButton(tabs[i].toUpperCase());
            tempButton.setPrefSize(navDrawer.getDefaultDrawerSize(), (navDrawer.getPrefHeight()) / tabs.length);
            tempButton.getStylesheets().add("/CoreUI/ButtonUI.css");
            tempButton.getStyleClass().add("button-raised");
            buttonArray[i] = tempButton;
        }

        buttonArray[0].setOnMouseClicked(event -> {
            if(!anchorPane.getChildren().contains(cal)) {
                anchorPane.getChildren().add(0, cal);
                anchorPane.setTopAnchor(cal, 45.0);
            }
        });
        VBox vBox = new VBox(buttonArray);

        navDrawer.setSidePane(vBox);
        navDrawer.setPickOnBounds(false);

        burgerTransition = new HamburgerBackArrowBasicTransition(navHamburger);
        burgerTransition.setRate(-1);

        navDrawer.setOnMouseClicked(event -> {
            System.out.println("Test");
            if (navDrawer.isShown()) {
                navDrawer.setMouseTransparent(false);
                burgerTransition.setRate(1); //Switches the transition between forward and backwards.
                burgerTransition.play(); //Plays the transition
//                navDrawer.close();
            } else {
                navDrawer.setMouseTransparent(true);
                burgerTransition.setRate(-1);
                burgerTransition.play(); //Plays the transition

            }

        });
    }

    @FXML
    void openDrawer(MouseEvent event) {
        if (navDrawer.isShown()) {
            navDrawer.setMouseTransparent(true);
            burgerTransition.setRate(-1); //Switches the transition between forward and backwards.
            navDrawer.close();
        } else {
            navDrawer.setMouseTransparent(false);
            burgerTransition.setRate(1);
            navDrawer.open();
        }

        burgerTransition.play(); //Plays the transition
    }

//    @FXML
//    private HashMap<String, Object> openSportsDialogue() {
//        HashMap<String, Object> resultMap = new HashMap<String, Object>();
//
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource("/CoreUI/SportsPopup.fxml"));
//        // initializing the controller
//        popupController popupController = new popupController();
//        loader.setController(popupController);
//        Parent layout;
//        try {
//            layout = loader.load();
//            Scene scene = new Scene(layout);
//            // this is the popup stage
//            Stage popupStage = new Stage();
//            // Giving the popup controller access to the popup stage (to allow the controller to close the stage)
//            popupController.setStage(popupStage);
//            if(this.main!=null) {
//                popupStage.initOwner(main.getStage());
//            }
//            popupStage.initModality(Modality.WINDOW_MODAL);
//            popupStage.setScene(scene);
//            popupStage.showAndWait();
//            sportsScheduleOpen = true;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return popupController.getResult();
//    }


}
