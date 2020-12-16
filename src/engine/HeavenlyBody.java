package engine;

import math.Vector2;
import utils.Pair;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class HeavenlyBody extends PhysicalObject {
    private double mass, radius;
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;
    private Vector2 axes;
    private Color color = Color.RED;

    public Vector2 getAxes() {
        return axes;
    }

    public void setAxes(Vector2 axes) {
        this.axes = axes;
    }



    public HeavenlyBody(double mass, double radius, Vector2 position) {
        super(mass);
        this.mass = mass;
        this.radius = radius;
        this.position = position;
        this.velocity = new Vector2(0, 0);
        this.acceleration = new Vector2(0, 0);
        this.axes = this.position.plus(new Vector2(radius, radius));
    }


    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;

    }

    public List<Vector2> getAnglePoints() {
        List<Vector2> points = new LinkedList<>();
        double x = position.getX(), y = position.getY();
        points.add(new Vector2(x + radius, y + radius));
        points.add(new Vector2(x - radius, y + radius));
        points.add(new Vector2(x + radius, y - radius));
        points.add(new Vector2(x - radius, y - radius));
        return points;
    }

    public boolean collision(HeavenlyBody object) {
//        for (Vector2 point : object.getAnglePoints()) {\
        boolean b = isInsidePoint(object.getPosition());
        return b;
//        }
    }

    private boolean isInsidePoint(Vector2 point) {
        double px = point.getX(), py = point.getY();
        double centerX = position.getX(), centerY = position.getY();
        double deltaX = px - centerX, deltaY = py - centerY;

        double ddX = deltaX * deltaX, ddY = deltaY * deltaY;
        double sum = ddX + ddY;
        double rr = radius * radius;
        return sum <= rr;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        HeavenlyBody body = (HeavenlyBody) o;
        return Double.compare(body.mass, mass) == 0 &&
                Double.compare(body.radius, radius) == 0 &&
//                Objects.equals(position, body.position) &&
//                Objects.equals(velocity, body.velocity) &&
//                Objects.equals(acceleration, body.acceleration) &&
                Objects.equals(color, body.color);
    }

    @Override
    public String toString() {
        return "HeavenlyBody{" +
                "mass=" + mass +
                ", radius=" + radius +
                ", position=" + position +
                ", velocity=" + velocity +
                ", acceleration=" + acceleration +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(mass, radius, color);
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
