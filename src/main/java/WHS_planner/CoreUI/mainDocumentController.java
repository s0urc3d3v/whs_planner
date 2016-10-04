package WHS_planner.CoreUI;

import WHS_planner.Calendar.Calendar;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.events.JFXDrawerEvent;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;


import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ResourceBundle;

public class mainDocumentController implements Initializable {

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

    private HamburgerBackArrowBasicTransition burgerTransition;


    public void initialize(URL location, ResourceBundle resources) {
        topBar.setStyle("-fx-background-color: #FF9800");
        navHamburger.getStylesheets().add("/CoreUI/ButtonUI.css");
        navHamburger.getStyleClass().add("jfx-hamburger-icon");
        String[] tabs = new String[]{"Calendar", "Schedule", "Homework", "Tests", "Rip A Fat Vape", "George"}; //Need to find a way to get this from another class,
        //Create the VBox                                                                            //May need to be an array of classes that extend tab, so you can get an fxml file related to them and their name.
        JFXButton[] buttonArray = new JFXButton[tabs.length];
        for (int i = 0; i < tabs.length; i++) {
            JFXButton tempButton = new JFXButton(tabs[i].toUpperCase());
            tempButton.setPrefSize(navDrawer.getDefaultDrawerSize(),(navDrawer.getPrefHeight())/tabs.length);
            tempButton.getStylesheets().add("/CoreUI/ButtonUI.css");
            tempButton.getStyleClass().add("button-raised");
            buttonArray[i] = tempButton;
        }

        buttonArray[0].setOnMouseClicked(event -> {
            if(cal == null){
                cal = new Calendar(1, 30);
            }
            if(!anchorPane.getChildren().contains(cal)) {
                anchorPane.getChildren().add(0, cal);
                anchorPane.setTopAnchor(cal,45.0);
            }
        });
        VBox vBox = new VBox(buttonArray);

        navDrawer.setSidePane(vBox);

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
    void fistMeDaddy(MouseEvent event) {
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


}
