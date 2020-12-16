/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;
import math.Vector2;

import static engine.Constants.G;

/**
 * Класс, описывающий источник внешнего воздействия.
 *
 * @author Alexey
 */
public class GravityForce {
    private Vector2 location;
    private double value;

    public GravityForce(Vector2 location) {
        this.location = location;
        value = 0;
    }

    public double gravity(HeavenlyBody body1, HeavenlyBody body2) {
        double r = body1.getPosition().minus(body2.getPosition()).length();
        double m1 = body1.getMass(), m2 = body2.getMass();
        return G * m1 * m2 / (r * r);
    }


    public Vector2 getLocation() {
        return location;
    }

    public void setLocation(Vector2 location) {
        this.location = location;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Vector2 getForceAt(HeavenlyBody body) {
        if (Math.abs(value) < 1e-12)
            return new Vector2(0, 0);
        return location.minus(body.getPosition()).normalize().mul(value);
    }

    public Vector2 getForceAt(Vector2 p) {
        if (Math.abs(value) < 1e-12)
            return new Vector2(0, 0);
        return location.minus(p).normalize().mul(value);
    }

    @Override
    public String toString() {
        return "ForceSource{" +
                "location=" + location +
                ", value=" + value +
                '}';
    }
}
