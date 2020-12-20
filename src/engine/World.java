/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;


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
    private SolarSystem solarSystem;
    private Map<HeavenlyBody, Orbit> orbits;
    private Map<HeavenlyBody, Explosion> explosions;
    private Space space;

    public World(Set<HeavenlyBody> heavenlyBodies, Space space) {
        this.solarSystem = new SolarSystem(heavenlyBodies);
        this.space = space;
        this.explosions = new HashMap<>();
        initMap();
    }

    public Map<HeavenlyBody, Explosion> getExplosions() {
        return explosions;
    }

    public void setExplosions(Map<HeavenlyBody, Explosion> explosions) {
        this.explosions = explosions;
    }

    public Map<HeavenlyBody, Orbit> getOrbits() {
        return orbits;
    }

    public void setOrbits(Map<HeavenlyBody, Orbit> orbits) {
        this.orbits = orbits;
    }

    public void clear() {
        solarSystem.clear();
        explosions.clear();
        orbits.clear();
    }

    public void initMap() {
        orbits = new HashMap<>();
        for (HeavenlyBody body : solarSystem.getBodies()) {
//            if (body.getName().equals("sun")) continue;
            orbits.put(body, new Orbit(new LinkedList<>()));
        }
    }

    private void removeExplosion() {
        Map.Entry<HeavenlyBody, Explosion> wasRemoved = null;
        for (Map.Entry<HeavenlyBody, Explosion> entry : explosions.entrySet()) {
            if (!solarSystem.getBodies().contains(entry.getKey())) {
                wasRemoved = entry;
            }
        }
        if (wasRemoved != null)
            explosions.remove(wasRemoved.getKey(), wasRemoved.getValue());
    }

    /**
     * Метод обновления состояния мира за указанное время
     *
     * @param dt Промежуток времени, за который требуется обновить мир.
     */
    private double lastTime;

    public double getLastTime() {
        return lastTime;
    }

    public void setLastTime(double lastTime) {
        this.lastTime = lastTime;
    }


    public void allUpdate(int delimiter, double dt) {
        for (int i = 0; i < delimiter; i++) {
            update(dt / delimiter);
        }
    }



    public void update(double dt) {
//        removeExplosion();
        for (HeavenlyBody current : solarSystem.getBodies()) {
//            current.setAxes(current.getAxes().rotate(current.getPosition(), current.getAxes(), Math.PI / 100));

            Vector2 newPosition = current.getPosition()
                    .plus(current.getVelocity().mul(dt))
                    .plus(current.getAcceleration().mul(dt * dt * 0.5));
            Vector2 newVelocity = current.getVelocity()
                    .plus(current.getAcceleration().mul(dt));

            boolean mustReturn = false;
            Vector2 resultForce = new Vector2(0, 0);
            for (HeavenlyBody body : solarSystem.getBodies()) {
                if (current.equals(body)) continue;
                if (bang(current, body)) {
                    mustReturn = true;
                    break;
                }
                GravityForce gravityForce = new GravityForce(body.getPosition());
                gravityForce.setValue(gravityForce.gravity(current, body));
                Vector2 force = gravityForce.getForceAt(current.getPosition()).mul(86400D * 86400D);
                resultForce = resultForce.plus(force);
            }
            if (mustReturn) return;

            current.setAcceleration(resultForce.mul(1 / current.getMass()));
            current.setVelocity(newVelocity);
            current.setPosition(newPosition);
            if (orbits.containsKey(current)) {
                Orbit orbit = orbits.get(current);
                orbit.addPoint(current);
            }
//            System.out.println("acc " + current.getAcceleration());
        }
    }

    private boolean bang(HeavenlyBody body1, HeavenlyBody body2) {
        if (body1.collision(body2)) {
            boolean firstIsMassive = body1.getMass() > body2.getMass();
            HeavenlyBody poofBody = firstIsMassive ? body2 : body1;
            explosions.put(poofBody, new Explosion(poofBody));
            solarSystem.getBodies().remove(poofBody);
            return true;
        }
        return false;
    }


    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    public SolarSystem getSolarSystem() {
        return solarSystem;
    }

    public void setSolarSystem(SolarSystem solarSystem) {
        this.solarSystem = solarSystem;
    }
}
