package app;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import drawers.IWorldDrawer;
import drawers.WorldDrawer;
import engine.Field;
import engine.ForceSource;
import engine.World;
import engine.spaceObjects.HeavenlyBody;
import math.Rectangle;
import math.Vector2;
import screen.ScreenConverter;
import screen.ScreenPoint;
import timers.AbstractWorldTimer;
import timers.UpdateWorldTimer;
import utils.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import static defaults.Defaults.ZOOM_DECREASE;
import static defaults.Defaults.ZOOM_INCREASE;

/**
 * @author Alexey
 */
public class DrawPanel extends JPanel implements ActionListener, KeyListener,
        MouseListener, MouseMotionListener, MouseWheelListener {
    private ScreenConverter screenConverter;
    private World world;
    private AbstractWorldTimer uwt;
    private Timer drawTimer;
    private boolean shift = false;
    private ScreenPoint lastPosition = null;
    private Set<HeavenlyBody> bodies = new HashSet<>();

    public DrawPanel() {
        super();
        Field field = new Field(
                new Rectangle(-2e8 * 1000, 2e8 * 1000, 4e8 * 1000, 4e8 * 1000),
                0.1, 9.8);

        Set<HeavenlyBody> bodies = new HashSet<>();
        HeavenlyBody body1 = new HeavenlyBody(1.989e27 * 1000, field.getRectangle().getWidth() / 20, field.getRectangle().getCenter());
        HeavenlyBody body2 = new HeavenlyBody(5.972e21 * 1000, field.getRectangle().getWidth() / 40, new Vector2(14.95e7 * 1000, 0));
        HeavenlyBody body3 = new HeavenlyBody(5.972e21 * 1000, field.getRectangle().getWidth() / 40, new Vector2(16.95e7 * 1000, 0));

        body1.setVelocity(new Vector2(0, 0));
        body2.setVelocity(new Vector2(0, 2.592e6 * 1000));
        body3.setVelocity(new Vector2(0, 2.592e6 * 1000));


        System.out.println(new ForceSource(new Vector2(0, 0)).gravity(body1, body2) + "////////////////////");

        body1.setColor(Color.YELLOW);
        body2.setColor(Color.BLUE);
        body3.setColor(Color.RED);


        bodies.add(body1);
        bodies.add(body2);
        bodies.add(body3);
        this.bodies = bodies;

        world = new World(bodies, field);
        screenConverter = new ScreenConverter(field.getRectangle(), 1000, 1000); //magic number
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);

        (uwt = new UpdateWorldTimer(world, 10)).start();
        drawTimer = new Timer(40, this);
        drawTimer.start();
    }

    @Override
    public void paint(Graphics g) {
        screenConverter.setWs(getHeight());  //че нибудь придумать
        screenConverter.setHs(getHeight());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        IWorldDrawer worldDrawer = new WorldDrawer((Graphics2D) bi.getGraphics(), screenConverter);
        worldDrawer.draw(world);
        g.drawImage(bi, 0, 0, null);
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public AbstractWorldTimer getUwt() {
        return uwt;
    }

    public void setUwt(AbstractWorldTimer uwt) {
        this.uwt = uwt;
    }

    public Timer getDrawTimer() {
        return drawTimer;
    }

    public void setDrawTimer(Timer drawTimer) {
        this.drawTimer = drawTimer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

        lastPosition = new ScreenPoint(e.getX(), e.getY());
        if (e.getButton() == MouseEvent.BUTTON1) {
            grab = true;
        }

        repaint();
    }

    private boolean grab = false;

    @Override
    public void mouseReleased(MouseEvent e) {

        lastPosition = null;
        if (e.getButton() == MouseEvent.BUTTON1) {
            grab = false;
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private Pair<HeavenlyBody, ScreenPoint> getGrab(ScreenPoint grabPoint) {
        Vector2 grab = screenConverter.s2r(grabPoint);
        double x = grab.getX(), y = grab.getY();
        for (HeavenlyBody heavenlyBody : world.getHeavenlyBodies()) {
            Vector2 center = heavenlyBody.getPosition();
            double r = heavenlyBody.getRadius(), centerX = center.getX(), centerY = center.getY();
            if ((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY) <= r * r)
                return new Pair<>(heavenlyBody, grabPoint);
        }
        return null;
    }

    public void clear() {
        world.setHeavenlyBodies(bodies);

    }

    @Override
    public void mouseDragged(MouseEvent e) {

        if (grab) {
            System.out.println("a");
            ScreenPoint currentPosition = new ScreenPoint(e.getX(), e.getY());
            if (getGrab(currentPosition) != null && lastPosition != null) {
                System.out.println("s");
                ScreenPoint position = getGrab(currentPosition).getSecond();
                HeavenlyBody body = getGrab(currentPosition).getFirst();
                Vector2 velocity = body.getVelocity();
                body.setVelocity(new Vector2(0,0));
                ScreenPoint deltaScreen = new ScreenPoint(
                        currentPosition.getI() - lastPosition.getI(),
                        currentPosition.getJ() - lastPosition.getJ());
                System.out.println(deltaScreen + " ds");
                ScreenPoint vector = new ScreenPoint(position.getI() + deltaScreen.getI(), position.getJ() + deltaScreen.getJ());
                Vector2 deltaReal = screenConverter.s2r(vector);
                System.out.println(deltaReal + " dr");
//                Vector2 vector = new Vector2(
//                        deltaReal.getX() + body.getPosition().getX(),
//                        deltaReal.getY() + body.getPosition().getY());
                System.out.println(vector + " v");
                lastPosition = currentPosition;
                body.setPosition(deltaReal);
                body.setVelocity(velocity);
                repaint();
            }
            return;
        }
            ScreenPoint currentPosition = new ScreenPoint(e.getX(), e.getY());
            if (lastPosition != null) {
                ScreenPoint deltaScreen = new ScreenPoint(
                        currentPosition.getI() - lastPosition.getI(),
                        currentPosition.getJ() - lastPosition.getJ());
                Vector2 deltaReal = screenConverter.s2r(deltaScreen);
                Vector2 zeroReal = screenConverter.s2r(new ScreenPoint(0, 0));
                Vector2 vector = new Vector2(
                        deltaReal.getX() - zeroReal.getX(),
                        deltaReal.getY() - zeroReal.getY());
                screenConverter.setXr(screenConverter.getXr() - vector.getX());
                screenConverter.setYr(screenConverter.getYr() - vector.getY());
                lastPosition = currentPosition;
            }
            repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (shift) {
            mouseMovedForce(e);
        }
    }


    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (shift) {
            changeMu(e);
            return;
        }

        int clicks = e.getWheelRotation();
        double zoom = 1;
        double coefficient = clicks < 0 ? ZOOM_INCREASE : ZOOM_DECREASE;
        for (int i = 0; i < Math.abs(clicks); i++) {
            zoom *= coefficient;
        }
//        Rectangle rect = world.getField().getRectangle();
//        world.getField().setRectangle(rect.scale(zoom));

        screenConverter.scale(zoom);
        System.out.println(zoom);

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            shift = true;
            repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            shift = false;
            repaint();
        }
    }


    public void mousePressedForce(MouseEvent e) {
        int direction = 0;
        if (e.getButton() == MouseEvent.BUTTON1)
            direction = 1;
        else if (e.getButton() == MouseEvent.BUTTON3)
            direction = -1;
        world.getExternalForce().setValue(1000000 * direction);
    }


    public void mouseReleasedForce(MouseEvent e) {
        world.getExternalForce().setValue(0);
    }


    public void mouseDraggedForce(MouseEvent e) {
        world.getExternalForce().setLocation(screenConverter.s2r(new ScreenPoint(e.getX(), e.getY())));
    }


    public void mouseMovedForce(MouseEvent e) {
        world.getExternalForce().setLocation(screenConverter.s2r(new ScreenPoint(e.getX(), e.getY())));
    }


    public void changeMu(MouseWheelEvent e) {
        double oldMu = world.getField().getMu();
        oldMu = Math.round(oldMu * 100 + e.getWheelRotation()) * 0.01;

        if (oldMu < -1)
            oldMu = -1;
        else if (oldMu > 1)
            oldMu = 1;
        else if (Math.abs(oldMu) < 0.005)
            oldMu = 0;
        world.getField().setMu(oldMu);
    }


}
