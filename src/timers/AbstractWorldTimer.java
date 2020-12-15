/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timers;

import engine.World;

import javax.swing.*;

/**
 *
 * @author Alexey
 */
public abstract class AbstractWorldTimer {
    protected World actualWorld;
    private Timer timer;

    public AbstractWorldTimer(World world, int period) {
        this.actualWorld = world;
        timer = new Timer(period, e -> worldAction(actualWorld));
    }
    
    public void start() {
        timer.start();
    }
    public void stop() {
        timer.stop();
    }
    public void setPeriod(int delay) {
        timer.setDelay(delay);
    }
    
    abstract void worldAction(World w);
}
