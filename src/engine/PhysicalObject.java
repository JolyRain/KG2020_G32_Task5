package engine;

import java.util.Objects;

public abstract class PhysicalObject {
    private double mass;

    public PhysicalObject(double mass) {
        this.mass = mass;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhysicalObject that = (PhysicalObject) o;
        return Double.compare(that.mass, mass) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mass);
    }
}
