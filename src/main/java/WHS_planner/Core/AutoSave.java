package WHS_planner.Core;

import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by spam on 16.09.2016.
 */
public class AutoSave extends Thread {
    private long tickrate = 100;
    public AutoSave (long tickRate) {
        tickRate = this.tickrate;
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                save();
            }
        }, 0, tickrate);
    }
    public void save(){
        //Override this with your save method if you want to use it
    }
}