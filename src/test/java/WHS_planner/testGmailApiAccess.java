package WHS_planner;

import WHS_planner.Core.GmailApiAccess;
import junit.framework.TestCase;

/**
 * Created by matthewelbing on 06.10.16.
 */
public class testGmailApiAccess extends TestCase {
    public void testSendEmail(){
        try {
            GmailApiAccess.sendEmail("matthewelbing@gmail.com", "whsplannerhandle@gmail.com", "test", "test content");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
