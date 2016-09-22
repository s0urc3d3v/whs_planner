package WHS_planner.CoreUI;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ResourceBundle;

public class mainDocumentController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXHamburger navHamburger;

    @FXML
    private JFXDrawer navDrawer;

    private HamburgerBackArrowBasicTransition burgerTransition;


    public void initialize(URL location, ResourceBundle resources) {
        navHamburger.getStylesheets().add("/CoreUI/ButtonUI.css");
        navHamburger.getStyleClass().add("jfx-hamburger-icon");
        String[] tabs = new String[]{"Calendar", "Schedule", "Homework", "Tests", "Rip A Fat Vape", "George"}; //Need to find a way to get this from another class,
        //Create the VBox                                                                            //May need to be an array of classes that extend tab, so you can get an fxml file related to them and their name.
        JFXButton[] buttonArray = new JFXButton[tabs.length];
        for (int i = 0; i < tabs.length; i++) {
            JFXButton tempButton = new JFXButton(tabs[i].toUpperCase());
            tempButton.setPrefSize(navDrawer.getPrefWidth(),navDrawer.getPrefHeight()/tabs.length);
            tempButton.getStylesheets().add("/CoreUI/ButtonUI.css");
            tempButton.getStyleClass().add("button-raised");
            buttonArray[i] = tempButton;
        }
        VBox vBox = new VBox(buttonArray);

        navDrawer.setSidePane(vBox);

        burgerTransition = new HamburgerBackArrowBasicTransition(navHamburger);
        burgerTransition.setRate(-1);
    }

    @FXML
    void fistMeDaddy(MouseEvent event) {

        if (navDrawer.isShown()) {
            burgerTransition.setRate(-1); //Switches the transition between forward and backwards.
            navDrawer.close();
        } else {
            burgerTransition.setRate(1);
            navDrawer.open();
        }

        burgerTransition.play(); //Plays the transition


    }


}
