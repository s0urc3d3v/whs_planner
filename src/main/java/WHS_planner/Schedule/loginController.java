package WHS_planner.Schedule;

import WHS_planner.Main;
import WHS_planner.UI.MainPane;
import WHS_planner.Util.XorTool;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class loginController implements Initializable
{
    @FXML
    private Button button;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXTextField user;
    @FXML
    private Label error;
    @FXML
    private Pane loginPane;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        //Initializes the "submit" button style
//        button.setButtonType(JFXButton.ButtonType.RAISED);
//        button.getStyleClass().setAll("button-raised");
//        button.getStylesheets().add("Schedule" + File.separator + "ButtonStyle.css");

//        loginPane.getStyleClass().add("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.25), 15, 0, 1, 2, 0);");
    }

    public void submit() throws Exception
    {
        String username = user.getText();
        String pass = password.getText();
        error.setVisible(true);

        if(username.equals("") || pass.equals(""))
        {
            error.setTextFill(Color.BLACK);
            error.setText("Please enter your iPass information");
        }
        else
        {
            try
            {
                GrabDay gd = new GrabDay(username, pass);

                if(gd.testConn())
                {
                    error.setTextFill(Color.GREEN);
                    error.setText("Logging in...");
                    loginPane.requestLayout();
                    button.setDisable(true);
                    File f = new File("Keys/ipass.key"); //TODO File.seperator?
                    if(!f.exists())
                    {
                        f.createNewFile();
                    }
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                    String xorKey = Main.getXorKey();
                    username = XorTool.encode(username, xorKey);
                    pass = XorTool.encode(pass, xorKey);

                    bw.write(username);
                    bw.newLine();
                    bw.write(pass);
                    bw.close();

//                    error.setTextFill(Color.RED);
                    //error.setText("iPass internal error. Restart and run clean.sh");

                    /*
                    //TODO
                    show button,
                    error.setText("iPass internal error. Restart and try again");
                    button calls logout method in ScheduleController and quits to program
                     */

                    try {
//                       while(true) {
//                           if(error.isVisible()) {
//                               System.out.println(error.getText());
//                               MainPane mp = (MainPane) Main.getMainPane();
//                               mp.resetSchedule();
//                               break;
//                           }
//                       }


                        PauseTransition ps = new PauseTransition(Duration.seconds(1));
                        ps.setOnFinished(event -> {
                            try {
                                MainPane mp = (MainPane) Main.getMainPane();
                                mp.resetSchedule();
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }

                        });
                        ps.play();





                    }
                    catch(Exception e)
                    {
                        System.out.println("Error in refreshing schedule pane...");
                    }
                }
                else
                {
                    error.setTextFill(Color.RED);
//                    error.setText("Information incorrect, please try again!");
                    error.setText("Incorrect username or password. Please try again.");
//                    error.setTextFill(Color.BLACK);
                    password.clear();
                }
            }
            catch(Exception e)
            {
                System.out.println("Error occurred during login");
            }




        }

    }
}
