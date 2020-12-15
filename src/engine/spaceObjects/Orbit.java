package engine.spaceObjects;

import math.Vector2;

import java.util.List;

public class Orbit {
    private List<Vector2> path;

    public Orbit(List<Vector2> path) {
        this.path = path;
    }

    public void addPoint(HeavenlyBody body) {
        path.add(body.getPosition());
        if (path.size() > 400) path.remove(0);
    }

    public List<Vector2> getPath() {
        return path;
    }

    public void setPath(List<Vector2> path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Orbit{" +
                "path=" + path +
                '}';
    }
}
