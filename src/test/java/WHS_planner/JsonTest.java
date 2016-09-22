package WHS_planner;

import WHS_planner.Core.JSON;
import junit.framework.Test;
import junit.framework.TestCase;

import java.io.IOException;

/**
 * Created by matthewelbing on 16.09.16.
 */
public class JsonTest extends TestCase{
    public void testWritePair(){
        try {
            JSON JsonApi = new JSON();
            JsonApi.writePair("test", "test data");
            JsonApi.unloadFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
