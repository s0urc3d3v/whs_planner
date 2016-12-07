package WHS_planner.Schedule;

import WHS_planner.Main;
import WHS_planner.UI.MainPane;
import WHS_planner.Util.AesTool;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Base64;
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
                    //TODO
//                    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//                    keyGenerator.init(128);
//                    SecretKey aesKey = keyGenerator.generateKey();
//                    try {
//                        String encodedKey = Base64.getEncoder().encodeToString(aesKey.getEncoded());
//                        AesTool usernameAesTool = new AesTool(username, encodedKey);
//                        AesTool passwordAesTool = new AesTool(pass, encodedKey);
//                        username = usernameAesTool.encrypt();
//                        usernameAesTool.done();
//                        pass = passwordAesTool.encrypt();
//                        passwordAesTool.done();
//                    }
//                    catch (Exception e){
//                        e.printStackTrace();
//                    }
//                    //TODO add passwordAesTool too

                    bw.write(username);
                    bw.newLine();
                    bw.write(pass);
                    bw.close();

                    error.setText("Login successful! Please wait....");

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


            try
            {
                MainPane mp = (MainPane) Main.getMainPane();
                mp.resetSchedule();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

        }

    }
}
