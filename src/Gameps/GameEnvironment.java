package Gameps;

import Geometry.Line;
import Geometry.Point;
import Geometry.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Ofek Mazor
 * ID: 328285705
 * The Gameps.GameEnvironment class represents the collection of collidable objects
 * within a game environment.
 */
public class GameEnvironment {
    private List<Collidable> gameObjects;

    /**
     * Constructs a new Gameps.GameEnvironment with an empty list of collidable objects.
     */
    public GameEnvironment() {
        this.gameObjects = new ArrayList<>();
    }

    /**
     * Constructs a new Gameps.GameEnvironment with the provided list of collidable objects.
     *
     * @param gameObjects the initial list of collidable objects to add
     */
    public GameEnvironment(List<Collidable> gameObjects) {
        this.gameObjects = new ArrayList<>(gameObjects);
    }

    /**
     * Adds a collidable object to the game environment.
     *
     * @param c the collidable object to add
     */
    public void addCollidable(Collidable c) {
        this.gameObjects.add(c);
    }

    /**
     * Removes a collidable object from the game environment.
     *
     * @param c the collidable object to remove
     */
    public void removeCollidable(Collidable c) {
        this.gameObjects.remove(c);
    }

    /**
     * Counts the number of blocks in the game environment.
     *
     * @return the number of blocks in the game environment
     */
    public int blockCount() {

        int count = 0;
        for (Collidable c : this.gameObjects) {
            if (c.isBlock()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds the closest collision information for an object moving along a trajectory.
     * If no collision occurs, returns null.
     *
     * @param trajectory the line representing the trajectory of the object
     * @return the closest collision information, or null if no collision occurs
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        Point closestCollisionPoint = null;
        Collidable closestCollidable = null;
        double minDistance = Double.POSITIVE_INFINITY;

        for (Collidable collidable : gameObjects) {
            Rectangle rect = collidable.getCollisionRectangle();
            List<Point> collisionPoints = rect.getCollisionPoint(trajectory);

            for (Point point : collisionPoints) {
                double distance = trajectory.start().distance(point);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestCollisionPoint = point;
                    closestCollidable = collidable;
                }
            }
        }

        if (closestCollisionPoint != null) {
            return new CollisionInfo(closestCollisionPoint, closestCollidable);
        }
        return null; // No collision occurred
    }
}
