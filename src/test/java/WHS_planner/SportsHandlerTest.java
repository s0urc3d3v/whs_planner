package WHS_planner;

import WHS_planner.Core.SportsHandler;
import junit.framework.TestCase;

import java.util.Arrays;

/**
 * Created by jack on 10/31/16.
 */
public class SportsHandlerTest extends TestCase {
    public void testSportsHandler() {
        SportsHandler sportsHandler = new SportsHandler();
        System.out.println(Arrays.toString(sportsHandler.getSports()));
        sportsHandler.close();
    }
}
