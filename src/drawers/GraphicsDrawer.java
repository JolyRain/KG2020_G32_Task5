package drawers;

import screen.ScreenConverter;

import java.awt.*;

public abstract class GraphicsDrawer {
    private Graphics2D graphics2D;
    private ScreenConverter screenConverter;

    public GraphicsDrawer(Graphics2D graphics2D, ScreenConverter screenConverter) {
        this.graphics2D = graphics2D;
        this.screenConverter = screenConverter;
    }

    public GraphicsDrawer() {
    }

    public Graphics2D getGraphics2D() {
        return graphics2D;
    }

    public void setGraphics2D(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;
    }

    public ScreenConverter getScreenConverter() {
        return screenConverter;
    }

    public void setScreenConverter(ScreenConverter screenConverter) {
        this.screenConverter = screenConverter;
    }
}
