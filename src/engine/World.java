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
    private GravityForce force;
    private boolean gravity = true;
    /**
     * Метод обновления состояния мира за указанное время
     *
     * @param dt Промежуток времени, за который требуется обновить мир.
     */
    private double lastTime;

    public World(Set<HeavenlyBody> heavenlyBodies, Space space) {
        this.solarSystem = new SolarSystem(heavenlyBodies);
        this.space = space;
        this.explosions = new HashMap<>();
        initMap();
        force = new GravityForce();
    }

    public void setGravity(boolean gravity) {
        this.gravity = gravity;
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
            if (body.getName().equals("sun")) continue;
            orbits.put(body, new Orbit(new LinkedList<>()));
        }
    }

    public void clearOrbits() {
        for (Map.Entry<HeavenlyBody, Orbit> entry : orbits.entrySet()) {
            entry.getValue().getPath().clear();
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

    public double getLastTime() {
        return lastTime;
    }

    public void setLastTime(double lastTime) {
        this.lastTime = lastTime;
    }


    public void allUpdate(int delimiter, double dt) {
        deleteBlownUpPlanets();
        for (int i = 0; i < delimiter; i++) {
            update(dt / delimiter);
        }
    }


    public void update(double dt) {
        deleteBlownUpPlanets();
        for (HeavenlyBody current : solarSystem.getBodies()) {
            Vector2 newPosition = current.getPosition()
                    .plus(current.getVelocity().mul(dt))
                    .plus(current.getAcceleration().mul(dt * dt * 0.5));
            Vector2 newVelocity = current.getVelocity()
                    .plus(current.getAcceleration().mul(dt));

            Vector2 resultForce = new Vector2(0, 0);
            if (gravity)
                resultForce = force.countSystemForce(current, solarSystem, dt);

            current.setAcceleration(resultForce.mul(1 / current.getMass()));
            current.setVelocity(newVelocity);
            current.setPosition(newPosition);

            if (orbits.containsKey(current)) {
                Orbit orbit = orbits.get(current);
                orbit.addPoint(current);
            }
        }
    }

    private void bang(HeavenlyBody body1, HeavenlyBody body2) {
        if (body1.collision(body2)) {
            boolean firstIsMassive = body1.getMass() > body2.getMass();
            HeavenlyBody poofBody = firstIsMassive ? body2 : body1;
            explosions.put(poofBody, new Explosion(poofBody));
        }
    }

    private void deleteBlownUpPlanets() {
        for (HeavenlyBody current : solarSystem.getBodies()) {
            for (HeavenlyBody body : solarSystem.getBodies()) {
                bang(current, body);
            }
        }
        solarSystem.getBodies().removeIf(body -> explosions.containsKey(body));
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
