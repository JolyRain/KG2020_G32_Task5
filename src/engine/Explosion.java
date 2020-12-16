package engine;

import math.Vector2;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Explosion {
    private double radius;
    private Vector2 position;
    private List<Vector2> points;

    public Explosion(HeavenlyBody body) {
        this.radius = body.getRadius();
        this.position = body.getPosition();
        generatePoints();
    }

    public Explosion(double radius, Vector2 position) {
        this.radius = radius;
        this.position = position;
        generatePoints();
    }

    private void generatePoints() {
        double step = 2 * Math.PI / 12;
        points = new LinkedList<>();
        for (double alpha = 0; alpha < 2 * Math.PI; alpha += step) {
            double currentX = getX(radius, alpha);
            double currentY = getY(radius, alpha);
            Vector2 current = new Vector2(currentX, currentY);

            double nextX = getX(radius, alpha + step);
            double nextY = getY(radius, alpha + step);
            Vector2 next = new Vector2(nextX, nextY);

            double middleX = getX(radius, alpha + step / 2);
            double middleY = getY(radius, alpha + step / 2);
            Vector2 middle = new Vector2(middleX, middleY);

            Vector2 direction = middle.minus(position).normalize();
            Vector2 far = position.plus(direction.mul(radius + Math.random() * radius));

            points.addAll(Arrays.asList(current, far, next));
        }
    }

    private double getX(double radius, double angle) {
        return radius * Math.cos(angle) + position.getX();
    }


    private double getY(double radius, double angle) {
        return radius * Math.sin(angle) + position.getY();
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

    public List<Vector2> getPoints() {
        return points;
    }

    public void setPoints(List<Vector2> points) {
        this.points = points;
    }
}
