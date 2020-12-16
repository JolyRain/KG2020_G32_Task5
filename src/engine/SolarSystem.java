package engine;

import java.util.Set;

public class SolarSystem {
    private Set<HeavenlyBody> bodies;

    public SolarSystem(Set<HeavenlyBody> bodies) {
        this.bodies = bodies;
    }

    public void addPlanet(HeavenlyBody planet) {
        bodies.add(planet);
    }

    public void removePlanet(HeavenlyBody planet) {
        bodies.remove(planet);
    }

    public HeavenlyBody getPlanet(HeavenlyBody planet) {
        for (HeavenlyBody body : bodies) {
            if (body.equals(planet))
                return body;
        }
        return null;
    }

    public Set<HeavenlyBody> getBodies() {
        return bodies;
    }

    public void setBodies(Set<HeavenlyBody> bodies) {
        this.bodies = bodies;
    }

    public void clear() {
        bodies.clear();
    }
}
