package WHS_planner.Core;

import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;
import java.util.*;
/**
 * Created by spam on 16.09.2016.
 */
public class AutoSave extends TimerTask{

    public void save(){
        //override this method with your save code after extending this class
    }
    public void start(int interval){
        //call this to start the auto save ticker
        TimerTask timerTask = new AutoSave();
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 0, (interval/2)*1000);
    }

    @Override
    public void run() {
        save();
    }
}