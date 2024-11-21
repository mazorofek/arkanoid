package Gameps;

import Geometry.Point;

public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     * author Ofek Mazor
     * ID 328285705
     * Constructs a Gameps.CollisionInfo object with the specified collision point and collidable object.
     *
     * @param collisionPoint the point at which the collision occurs
     * @param collisionObject the Gameps.Collidable object involved in the collision
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * Returns the point at which the collision occurs.
     *
     * @return the collision point
     */
    public Point collisionPoint() {
        return collisionPoint;
    }

    /**
     * Returns the collidable object involved in the collision.
     *
     * @return the collidable object
     */
    public Collidable collisionObject() {
        return collisionObject;
    }
}

