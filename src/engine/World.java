/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;


import engine.spaceObjects.HeavenlyBody;
import engine.spaceObjects.Orbit;
import math.Vector2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Класс, описывающий весь мир, в целом.
 *
 * @author Alexey
 */
public class World {
    private Set<HeavenlyBody> heavenlyBodies;
    private Map<HeavenlyBody, Orbit> orbits;
    private Field field;

    public Map<HeavenlyBody, Orbit> getOrbits() {
        return orbits;
    }

    public void setOrbits(Map<HeavenlyBody, Orbit> orbits) {
        this.orbits = orbits;
    }

    private ForceSource externalForce;

    public World(Set<HeavenlyBody> heavenlyBodies, Field field) {
        this.heavenlyBodies = heavenlyBodies;
        this.field = field;
        this.externalForce = new ForceSource(field.getRectangle().getCenter());
        initMap();
    }

    private void initMap() {
        orbits = new HashMap<>();
        for (HeavenlyBody body : heavenlyBodies) {
            orbits.put(body, new Orbit(new LinkedList<>()));
        }
    }

    /**
     * Метод обновления состояния мира за указанное время
     *
     * @param dt Промежуток времени, за который требуется обновить мир.
     */
    public void update(double dt) {
        for (HeavenlyBody current : heavenlyBodies) {
            if (current.getMass() > 2e26 * 1000) continue;
            Vector2 F = current.getVelocity().normalize();

            Vector2 newPosition = current.getPosition()
                    .plus(current.getVelocity().mul(dt))
                    .plus(current.getAcceleration().mul(dt * dt * 0.5));
            Vector2 newVelocity = current.getVelocity()
                    .plus(current.getAcceleration().mul(dt));

            double vx = newVelocity.getX(), vy = newVelocity.getY();
//            boolean reset = false;
//            if (newPosition.getX() - current.getRadius() < field.getRectangle().getLeft() || newPosition.getX() + current.getRadius() > field.getRectangle().getRight()) {
//                vx = -vx;
//                reset = true;
//            }
//            if (newPosition.getY() - current.getRadius() < field.getRectangle().getBottom() || newPosition.getY() + current.getRadius() > field.getRectangle().getTop()) {
//                vy = -vy;
//                reset = true;
//            }
//            newVelocity = new Vector2(vx, vy);
//            if (newVelocity.length() < 1e-10)
//                newVelocity = new Vector2(0, 0);
//            if (reset)
//                newPosition = current.getPosition();

            Vector2 mainForce = new Vector2(0, 0);
            for (HeavenlyBody body : heavenlyBodies) {
                if (current.equals(body)) continue;
                externalForce.setLocation(body.getPosition());
                externalForce.setValue(externalForce.gravity(current, body));
                Vector2 force = externalForce.getForceAt(newPosition).mul(86400D * 86400D);
                mainForce = mainForce.plus(force);
            }
            current.setAcceleration(mainForce.mul(1 / current.getMass()));
            current.setVelocity(newVelocity);
            current.setPosition(newPosition);
            Orbit orbit = orbits.get(current);
            orbit.addPoint(current);
//            System.out.println(orbits.get(current));
//            System.out.println("------------------------------------");
//            System.out.println(current);
//            System.out.println(externalForce);
        }
    }


    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Set<HeavenlyBody> getHeavenlyBodies() {
        return heavenlyBodies;
    }

    public void setHeavenlyBodies(Set<HeavenlyBody> heavenlyBodies) {
        this.heavenlyBodies = heavenlyBodies;
    }

    public ForceSource getExternalForce() {
        return externalForce;
    }
}
