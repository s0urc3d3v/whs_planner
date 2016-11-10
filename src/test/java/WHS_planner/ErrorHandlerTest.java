package WHS_planner;

import WHS_planner.Core.ErrorHandler;
import javafx.stage.Stage;
import junit.framework.TestCase;

/**
 * Created by matthewelbing on 21.09.16.
 */
public class ErrorHandlerTest extends TestCase{

    public void testHandleGenericError() throws Exception {
        //Cant test this error handler because the program needs to be running.
        //The Program runs until the user stops it... kinda stinks
//        ErrorHandler.handleGenericError("test_txt", new Exception());
    }
}
