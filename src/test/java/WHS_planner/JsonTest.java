package WHS_planner;

import WHS_planner.Core.JSON;
import junit.framework.TestCase;

import java.io.IOException;

/**
 * Created by matthewelbing on 16.09.16.
 */
public class JsonTest extends TestCase {
    public void testWritePair(){
        try {
            JSON JsonApi = new JSON("/Users/john_bachman/IdeaProjects/whs_planner/src/test/java/WHS_planner/testDatabase/test.json");
            JsonApi.writePair("test", "test data");
            JsonApi.updateFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
