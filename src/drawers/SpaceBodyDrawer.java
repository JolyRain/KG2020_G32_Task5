package drawers;

import engine.spaceObjects.HeavenlyBody;
import screen.ScreenConverter;
import screen.ScreenPoint;

import java.awt.*;

public class SpaceBodyDrawer extends GraphicsDrawer implements ISpaceBodyDrawer {



    public SpaceBodyDrawer(Graphics2D graphics2D, ScreenConverter screenConverter) {
        super(graphics2D, screenConverter);
    }

    public SpaceBodyDrawer() {
    }

    @Override
    public void drawSpaceObject(HeavenlyBody spaceObject) {
        ScreenConverter screenConverter = getScreenConverter();
        Graphics2D graphics2D = getGraphics2D();
        ScreenPoint position = screenConverter.r2s(spaceObject.getPosition());
        int radiusHoriz = screenConverter.r2sDistanceH(spaceObject.getRadius());
        int radiusVert = screenConverter.r2sDistanceV(spaceObject.getRadius());
        graphics2D.setColor(spaceObject.getColor());
        graphics2D.fillOval(
                position.getI() - radiusHoriz,
                position.getJ() - radiusVert,
                radiusHoriz + radiusHoriz,
                radiusVert + radiusVert);
    }




}
