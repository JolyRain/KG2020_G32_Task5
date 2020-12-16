package app;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import drawers.IWorldDrawer;
import drawers.WorldDrawer;
import engine.Space;
import engine.GravityForce;
import engine.World;
import engine.HeavenlyBody;
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
        Space space = new Space(
                new Rectangle(-2e8 * 1000, 2e8 * 1000, 4e8 * 1000, 4e8 * 1000));

        Set<HeavenlyBody> bodies = new HashSet<>();
        HeavenlyBody body1 = new HeavenlyBody(1.989e27 * 1000, space.getRectangle().getWidth()/ 20, space.getRectangle().getCenter());
        HeavenlyBody body2 = new HeavenlyBody(5.972e21 * 1000, space.getRectangle().getWidth()/ 40, new Vector2(14.95e7 * 1000, 0));
        HeavenlyBody body3 = new HeavenlyBody(7.34e19 * 1000, space.getRectangle().getWidth()/ 160, new Vector2(17e7 * 1000, 0));
        HeavenlyBody body4 = new HeavenlyBody(10.972e21 * 1000, space.getRectangle().getWidth() / 40, new Vector2(30.95e7 * 1000, 0));

        body1.setVelocity(new Vector2(0, 0));
        body2.setVelocity(new Vector2(0, 2.592e6 * 1000));
        body3.setVelocity(new Vector2(0, (881280) * 1000));
        body4.setVelocity(new Vector2(0, 1.592e6 * 1000));

//        Space space = new Space(
//                new Rectangle(-10, 10, 20, 20));
//
//        Set<HeavenlyBody> bodies = new HashSet<>();
//        HeavenlyBody body1 = new HeavenlyBody(10000, space.getRectangle().getWidth() / 20, space.getRectangle().getCenter());
//        HeavenlyBody body2 = new HeavenlyBody(1, space.getRectangle().getWidth() / 40, new Vector2(10, 0));
//        HeavenlyBody body3 = new HeavenlyBody(1, space.getRectangle().getWidth() / 40, new Vector2(6, 0));
//        HeavenlyBody body4 = new HeavenlyBody(1, space.getRectangle().getWidth() / 40, new Vector2(10, 0));
//
//        body1.setVelocity(new Vector2(0, 0));
//        body2.setVelocity(new Vector2(0, 10));
//        body3.setVelocity(new Vector2(0, 1));
//        body4.setVelocity(new Vector2(0, 1));


        System.out.println(new GravityForce(new Vector2(0, 0)).gravity(body1, body2) + "////////////////////");

        body1.setColor(Color.YELLOW);
        body2.setColor(Color.BLUE);
        body3.setColor(Color.GRAY);
        body4.setColor(Color.GREEN);


        bodies.add(body1);
        bodies.add(body2);
//        bodies.add(body3);
//        bodies.add(body4);
        this.bodies = bodies;

        world = new World(bodies, space);
        screenConverter = new ScreenConverter(space.getRectangle(), 1000, 1000); //magic number
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
        Graphics2D graphics2D = (Graphics2D) bi.getGraphics();
        IWorldDrawer worldDrawer = new WorldDrawer(graphics2D, screenConverter);
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
        for (HeavenlyBody heavenlyBody : world.getSolarSystem().getBodies()) {
            Vector2 center = heavenlyBody.getPosition();
            double r = heavenlyBody.getRadius(), centerX = center.getX(), centerY = center.getY();
            if ((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY) <= r * r)
                return new Pair<>(heavenlyBody, grabPoint);
        }
        return null;
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

    }


    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
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
}
