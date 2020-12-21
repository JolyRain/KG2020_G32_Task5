package drawers;

import defaults.Defaults;
import engine.HeavenlyBody;
import screen.ScreenConverter;
import screen.ScreenPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Observer;

public class SpaceBodyDrawer extends GraphicsDrawer implements ISpaceBodyDrawer {

private ImageObserver observer;

    public SpaceBodyDrawer(Graphics2D graphics2D, ScreenConverter screenConverter, ImageObserver observer) {
        super(graphics2D, screenConverter);
        this.observer = observer;
    }

    @Override
    public void drawSpaceObject(HeavenlyBody spaceObject) {
        ScreenConverter screenConverter = getScreenConverter();
        Graphics2D graphics2D = getGraphics2D();
        ScreenPoint position = screenConverter.r2s(spaceObject.getPosition());
        int radiusHoriz = screenConverter.r2sDistanceH(spaceObject.getRadius());
        int radiusVert = screenConverter.r2sDistanceV(spaceObject.getRadius());
        int radius = radiusHoriz + radiusVert;
        if (spaceObject.getName().equals("123")) {
            System.out.println(radiusHoriz);
            System.out.println(radiusVert);

        }
        ScreenPoint axes = screenConverter.r2s(spaceObject.getAxes());
        graphics2D.setColor(spaceObject.getColor());
        if (getTextureImage(spaceObject) != null) {
            Image texture = createResizedCopy(getTextureImage(spaceObject), radiusHoriz * 2, radiusVert * 2);
            graphics2D.drawImage(texture, position.getI() - radiusHoriz, position.getJ() - radiusVert, observer);
        } else {
            graphics2D.fillOval(position.getI() - radiusHoriz, position.getJ() - radiusVert, radius, radius);
        }
            //        graphics2D.setColor(Color.GREEN);
//        graphics2D.drawLine(position.getI(), position.getJ(), axes.getI(), axes.getJ());
    }

    private Image getTextureImage(HeavenlyBody body) {
        if (!hasTexture(body)) return null;
        return new ImageIcon("src\\textures\\" + body.getName().toLowerCase() + ".gif").getImage();
    }

    private BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight) {
        BufferedImage scaledImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.setComposite(AlphaComposite.Src);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g2d.dispose();
        return scaledImage;
    }

    private boolean hasTexture(HeavenlyBody body) {
        String name = body.getName().toLowerCase();
        return name.equals("sun") ||  name.equals("mercury") || name.equals("venus") || name.equals("earth")
                || name.equals("mars") || name.equals("jupiter") || name.equals("saturn") || name.equals("uranus")
                    || name.equals("neptune") || name.equals("pluto");
    }




}
