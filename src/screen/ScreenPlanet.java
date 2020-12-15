package screen;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ScreenPlanet {
    private int radius;
    private ScreenPoint position;
    private Color color = Color.RED;
    private  List<ScreenPoint> path;

    public ScreenPlanet(int radius, ScreenPoint position, Color color, List<ScreenPoint> path) {
        this.radius = radius;
        this.position = position;
        this.color = color;
        this.path = path;
    }

    public ScreenPlanet(int radius, ScreenPoint position, Color color) {
        this.radius = radius;
        this.position = position;
        this.color = color;
        this.path = new LinkedList<>();
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public ScreenPoint getPosition() {
        return position;
    }

    public void setPosition(ScreenPoint position) {
        this.position = position;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List<ScreenPoint> getPath() {
        return path;
    }

    public void setPath(List<ScreenPoint> path) {
        this.path = path;
    }
}
