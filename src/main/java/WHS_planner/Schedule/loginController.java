package WHS_planner.Schedule;

import WHS_planner.Main;
import WHS_planner.UI.MainPane;
import WHS_planner.Util.XorTool;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class loginController implements Initializable
{
    @FXML
    private JFXButton button;
    @FXML
    private PasswordField password;
    @FXML
    private TextField user;
    @FXML
    private Label error;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        //Initializes the "submit" button style
        button.setButtonType(JFXButton.ButtonType.RAISED);
        button.getStyleClass().setAll("button-raised");
        button.getStylesheets().add("Schedule" + File.separator + "ButtonStyle.css");
    }

    public void submit()
    {
        String username = user.getText();
        String pass = password.getText();

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
                    File f = new File("Keys/ipass.key"); //TODO File.seperator?
                    if(!f.exists())
                    {
                        f.createNewFile();
                    }
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                    String xorKey = Main.getXorKey();
                    username = XorTool.encode(username, xorKey);
                    pass = XorTool.encode(pass, xorKey);

                    //System.out.println(username);
                    //System.out.println(pass);

                    bw.write(username);
                    bw.newLine();
                    bw.write(pass);
                    bw.close();

                    error.setTextFill(Color.RED);
                    error.setText("iPass internal error. Please restart, run clean.sh and try again.");

                    try
                    {
                        MainPane mp = (MainPane) Main.getMainPane();
                        mp.resetSchedule();
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
