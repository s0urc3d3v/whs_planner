package WHS_planner;

import WHS_planner.Core.AutoSave;
import junit.framework.TestCase;

/**
 * Created by matthewelbing on 28.09.16.
 */
public class AutoSaveTest extends TestCase {
    public void testAutoSave(){
        AutoSave autoSave = new AutoSave();
        autoSave.run();
    }
}
