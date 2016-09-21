package WHS_planner.CoreUI;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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
        burgerTransition = new HamburgerBackArrowBasicTransition(navHamburger);
        burgerTransition.setRate(-1);
    }

    @FXML
    void fistMeDaddy(MouseEvent event) {
        burgerTransition.setRate(burgerTransition.getRate() * -1); //Switches the transition between forward and backwards.
        burgerTransition.play(); //Plays the transition

        if (navDrawer.isShown()) {
            navDrawer.close();
        } else {
            navDrawer.open();
        }
    }


}
