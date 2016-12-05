package WHS_planner;

import WHS_planner.Core.JSON;
import junit.framework.Test;
import junit.framework.TestCase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by matthewelbing on 16.09.16.
 */
public class JsonTest extends TestCase{
    public void testWritePair(){
        JSON JsonApi = new JSON();
        JsonApi.loadFile("testDatabase/test.json");
        JsonApi.writePair("test", "test data");
        JsonApi.unloadFile();
    }

    public void testWriteArray(){
        JSON JsonApi = new JSON();
        JsonApi.loadFile("testDatabase/test.json");
        JsonApi.writeArray("test", new Object[] {"Hello", "Bonjour", 123, "Worked"});
        JsonApi.unloadFile();
    }

    public void testReadFile() {
        JSON JsonApi = new JSON();
        JsonApi.loadFile("testDatabase/test.json");
        HashMap<Object, Object> jsonFile = JsonApi.dropJsonDb();
        Iterator iterator = jsonFile.keySet().iterator();
        while(iterator.hasNext()) {
            Object next = iterator.next();
            System.out.print(next + " : " + jsonFile.get(next));
        }
        JsonApi.unloadFile();
    }
}
