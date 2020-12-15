package drawers;

import engine.Field;
import engine.ForceSource;
import engine.World;
import engine.spaceObjects.HeavenlyBody;
import engine.spaceObjects.Orbit;
import math.Vector2;
import screen.ScreenConverter;
import screen.ScreenCoordinates;
import screen.ScreenPoint;

import java.awt.*;
import java.util.List;
import java.util.*;


public class WorldDrawer extends GraphicsDrawer implements IWorldDrawer {
    private ISpaceBodyDrawer spaceBodyDrawer;

    public WorldDrawer(Graphics2D graphics2D, ScreenConverter screenConverter) {
        super(graphics2D, screenConverter);
        spaceBodyDrawer = new SpaceBodyDrawer(graphics2D, screenConverter);
    }


    public WorldDrawer() {

    }

    public ISpaceBodyDrawer getSpaceBodyDrawer() {
        return spaceBodyDrawer;
    }

    public void setSpaceBodyDrawer(ISpaceBodyDrawer spaceBodyDrawer) {
        this.spaceBodyDrawer = spaceBodyDrawer;
    }

    @Override
    public void draw(World world) {
        drawSpace(world);
        drawSpaceObjects(world.getHeavenlyBodies());
        drawLegend(world);
    }

    private void drawLegend(World world) {
        Field field = world.getField();
        ForceSource externalForce = world.getExternalForce();
        getGraphics2D().setColor(Color.WHITE);
        getGraphics2D().drawString(String.format("Mu=%.2f", field.getMu()), 10, 30);
        getGraphics2D().drawString(String.format("F=%.0f", externalForce.getValue()), 10, 50);
    }

    private void drawSpace(World world) {
        Field field = world.getField();
        Graphics2D graphics2D = getGraphics2D();
        ScreenConverter screenConverter = getScreenConverter();
        ScreenPoint tl = screenConverter.r2s(field.getRectangle().getTopLeft());
        int w = screenConverter.r2sDistanceH(field.getRectangle().getWidth());
        int h = screenConverter.r2sDistanceV(field.getRectangle().getHeight());
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, screenConverter.getWs(), screenConverter.getHs());
//        graphics2D.setColor(Color.RED);
//        graphics2D.drawRect(tl.getI(), tl.getJ(), w, h);
//        drawStars();
        drawPath(world);
    }

    private void drawSpaceObjects(Collection<HeavenlyBody> heavenlyBodies) {
        for (HeavenlyBody body : heavenlyBodies) {
            spaceBodyDrawer.drawSpaceObject(body);
        }
    }

    private void drawPath(World world) {
        Graphics2D graphics2D = getGraphics2D();
        ScreenConverter screenConverter = getScreenConverter();
        for (Map.Entry<HeavenlyBody, Orbit> path : world.getOrbits().entrySet()) {
            graphics2D.setColor(path.getKey().getColor());
            List<ScreenPoint> points = screenConverter.r2s(path.getValue().getPath());
            ScreenCoordinates screenCoordinates = new ScreenCoordinates(points);
            graphics2D.drawPolyline(screenCoordinates.getXx(), screenCoordinates.getYy(), screenCoordinates.size());
        }
    }

    private void drawStars() {
        Graphics2D graphics2D = getGraphics2D();
        ScreenConverter screenConverter = getScreenConverter();
        for (int i = 0; i < 100; i++) {
            Vector2 star = new Vector2(
                    Math.random() * (Math.abs(screenConverter.getXr()) + screenConverter.getWr()) - screenConverter.getWr() / 2,
                    Math.random() * (Math.abs(screenConverter.getYr()) + screenConverter.getHr()) - screenConverter.getHr() / 2);
            ScreenPoint point = screenConverter.r2s(star);
            graphics2D.setColor(Color.WHITE);
            graphics2D.fillRect(point.getI(),point.getJ(), 1,1);
        }
    }


}
