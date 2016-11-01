package WHS_planner;

import WHS_planner.Core.SportsHandler;
import junit.framework.TestCase;

import java.util.Arrays;

/**
 * Created by jack on 10/31/16.
 */
public class SportsHandlerTest extends TestCase {
    public void SportsHandlerTest() {
        SportsHandler sportsHandler = new SportsHandler();
        sportsHandler.setup();
        System.out.println(Arrays.toString(sportsHandler.getSports()));
        sportsHandler.close();
    }
}
