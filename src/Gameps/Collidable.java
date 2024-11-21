package Gameps;

import sprites.Ball;
import Geometry.Point;
import biuoop.DrawSurface;
import Geometry.Rectangle;

/**
 * Author: Ofek Mazor
 * ID: 328285705
 * The Gameps.Collidable interface represents an object that can collide with other objects.
 * It defines methods to retrieve the collision shape, handle collisions, and draw the object.
 */
public interface Collidable{

    /**
     * Returns the collision shape of the object.
     *
     * @return the collision rectangle representing the object's shape
     */
    Rectangle getCollisionRectangle();
    default boolean isBlock() {
        return false;
    }
    /**
     * Handles a collision with the object.
     *
     * @param collisionPoint the point of collision
     * @param currentVelocity the current velocity of the colliding object
     * @return the new velocity expected after the hit
     */
    Velocity hit(Ball b, Point collisionPoint, Velocity currentVelocity);

    /**
     * Draws the object on the given draw surface.
     *
     * @param d the draw surface to draw on
     */
     void  drawOn(DrawSurface d);
}
