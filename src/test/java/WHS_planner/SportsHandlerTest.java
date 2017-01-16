package WHS_planner;

import WHS_planner.Core.SportsHandler;
import junit.framework.TestCase;

/**
 * Created by jack on 10/31/16.
 */
public class SportsHandlerTest extends TestCase {
    public void testSportsHandler() {
        SportsHandler sportsHandler = new SportsHandler();
        sportsHandler.getEvents(0).forEach(System.out::println);
    }
}
