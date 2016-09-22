package WHS_planner;

import WHS_planner.Core.ErrorHandler;
import junit.framework.TestCase;

/**
 * Created by matthewelbing on 21.09.16.
 */
public class ErrorHandlerTest extends TestCase{

    public void testHandleGenericError() throws Exception {

        ErrorHandler.handleGenericError("test_txt", new Exception());
    }
}
