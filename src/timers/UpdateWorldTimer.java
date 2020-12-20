/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timers;


import engine.World;

/**
 * @author Alexey
 */
public class UpdateWorldTimer extends AbstractWorldTimer {

    private long last;
    private int speedAnimation = 10;

    public UpdateWorldTimer(World world, int period) {
        super(world, period);
    }

    public int getSpeedAnimation() {
        return speedAnimation;
    }

    public void setSpeedAnimation(int speedAnimation) {
        this.speedAnimation = speedAnimation;
    }

    @Override
    public void worldAction(World w) {
        long time = System.currentTimeMillis();
        actualWorld.allUpdate(speedAnimation, (time - last) * 0.001 * speedAnimation / 2);
        last = time;
        actualWorld.setLastTime(last);
    }


    @Override
    public void start() {
        last = System.currentTimeMillis();
        super.start();
    }

}
