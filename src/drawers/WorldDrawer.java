package drawers;

import engine.*;
import math.Vector2;
import screen.ScreenConverter;
import screen.ScreenCoordinates;
import screen.ScreenPoint;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class WorldDrawer extends GraphicsDrawer implements IWorldDrawer {
    private ISpaceBodyDrawer spaceBodyDrawer;
    private boolean showOrbits = true;
    private boolean showNames = false;

    public void setShowNames(boolean showNames) {
        this.showNames = showNames;
    }

    public void setShowOrbits(boolean showOrbits) {
        this.showOrbits = showOrbits;
    }

    public WorldDrawer(Graphics2D graphics2D, ScreenConverter screenConverter, ImageObserver observer) {
        super(graphics2D, screenConverter);
        spaceBodyDrawer = new SpaceBodyDrawer(graphics2D, screenConverter, observer);
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
        drawSpaceObjects(world.getSolarSystem().getBodies());
        if (showNames) drawLegend(world);
        drawExplosions(world);
    }

    private void drawLegend(World world) {
        ScreenConverter screenConverter = getScreenConverter();
        Graphics2D graphics2D = getGraphics2D();
        graphics2D.setColor(Color.WHITE);
        for (HeavenlyBody body : world.getSolarSystem().getBodies()) {
            ScreenPoint position = screenConverter.r2s(body.getPosition());
            int radiusHoriz = screenConverter.r2sDistanceH(body.getRadius());
            int radiusVert = screenConverter.r2sDistanceV(body.getRadius());
            graphics2D.drawString(body.getName(), position.getI() - radiusHoriz, position.getJ() - radiusVert);
        }
    }

    private void drawSpace(World world) {
        Space space = world.getSpace();
        Graphics2D graphics2D = getGraphics2D();
        ScreenConverter screenConverter = getScreenConverter();
        ScreenPoint tl = screenConverter.r2s(space.getRectangle().getTopLeft());
        int w = screenConverter.r2sDistanceH(space.getRectangle().getWidth());
        int h = screenConverter.r2sDistanceV(space.getRectangle().getHeight());
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, screenConverter.getWs(), screenConverter.getHs());
//        graphics2D.setColor(Color.RED);
//        graphics2D.drawRect(tl.getI(), tl.getJ(), w, h);
//        drawStars();
        if (showOrbits) {
            drawPath(world);
        } else {
            world.clearOrbits();
        }
    }

    private void drawSpaceObjects(Collection<HeavenlyBody> heavenlyBodies) {
        for (HeavenlyBody body : heavenlyBodies) {
            spaceBodyDrawer.drawSpaceObject(body);
        }
    }

    private void drawExplosions(World world) {
        for (Map.Entry<HeavenlyBody, Explosion> exp : world.getExplosions().entrySet()) {
            if (exp.getValue().getRadius() > exp.getKey().getRadius() / 10) {
                Explosion big = exp.getValue();
                drawExplosion(big, Color.RED);
                big.setRadius(big.getRadius() / 1.01);
                drawExplosion(big, Color.YELLOW);
            }
        }
    }

    private void drawExplosion(Explosion explosion, Color color) {
        Graphics2D graphics2D = getGraphics2D();
        ScreenConverter screenConverter = getScreenConverter();
        List<ScreenPoint> screenPoints = screenConverter.r2s(explosion.getPoints());
        ScreenCoordinates screenCoordinates = new ScreenCoordinates(screenPoints);
        graphics2D.setColor(color);
        graphics2D.fillPolygon(screenCoordinates.getXx(), screenCoordinates.getYy(), screenCoordinates.size());
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
                    Math.random() * (Math.abs(screenConverter.getXr()) + screenConverter.getWr()) - screenConverter.getWr(),
                    Math.random() * (Math.abs(screenConverter.getYr()) + screenConverter.getHr()) - screenConverter.getHr());
            ScreenPoint point = screenConverter.r2s(star);
            graphics2D.setColor(Color.WHITE);
            graphics2D.fillRect(point.getI(), point.getJ(), 1, 1);
        }
    }


}
