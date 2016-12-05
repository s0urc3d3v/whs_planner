package WHS_planner.CoreUI;


import javafx.scene.control.Alert;


/**
 * Created by andrew_eggleston on 9/16/16.
 */
public class ErrorPage{

    private static void infoBox(String infoMessage, String titleBar)
    {
        /* By specifying a null headerMessage String, we cause the dialog to
           not have a header */
        infoBox(infoMessage, titleBar, null);
    }

    private static void infoBox(String infoMessage, String titleBar, String headerMessage)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

    public static void displayErrorWithMsg(String infoMessage){
        infoBox(infoMessage, "Unhandled Exception");
    }







}
