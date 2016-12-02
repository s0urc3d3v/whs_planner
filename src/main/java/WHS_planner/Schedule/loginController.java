package WHS_planner.Schedule;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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

    }

    public void submit()
    {
        String username = user.getText();
        String pass = password.getText();

        if(username.equals("") || pass.equals(""))
        {
            error.setText("Please put in ipass information");
        }
        else
        {
            try
            {
                GrabDay gd = new GrabDay(username, pass);
                if(gd.testConn())
                {
                    File f = new File("Keys/ipass.key");
                    if(!f.exists())
                    {
                        f.createNewFile();
                    }
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                    bw.write(username);
                    bw.newLine();
                    bw.write(pass);
                    bw.close();

                    error.setText("Login successful! Please restart to see schedule");

                }
                else
                {
                    error.setText("Information incorrect, please try again!");
                }
            }
            catch(Exception e)
            {
                System.out.println("Error occurred during login");
            }

        }

    }
}
