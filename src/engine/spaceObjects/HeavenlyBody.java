package engine.spaceObjects;

import math.Vector2;

import java.awt.*;
import java.util.Objects;

public class HeavenlyBody {
    private double mass, radius;
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;
    private Color color = Color.RED;

    public HeavenlyBody(double mass, double radius, Vector2 position) {
        this.mass = mass;
        this.radius = radius;
        this.position = position;
        this.velocity = new Vector2(0, 0);
        this.acceleration = new Vector2(0, 0);
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
