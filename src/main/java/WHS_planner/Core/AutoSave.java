package WHS_planner.Core;

import java.awt.event.ActionListener;
import java.util.Timer;

/**
 * Created by spam on 16.09.2016.
 */
public class AutoSave extends Thread {
    private long tickrate;
    public AutoSave (long tickRate){
        this.tickrate = tickRate;
        Runnable r = () -> {
            Timer tick = new Timer();
        };
        Thread t = new Thread(r);
        t.start();
    }



}
