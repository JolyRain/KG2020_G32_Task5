package engine;

import math.Vector2;

public interface IForce {

    double value(PhysicalObject object1, PhysicalObject object2);

    Vector2 vector(PhysicalObject object1, PhysicalObject object2);

    Vector2 getForceAt(Vector2 position);
}
