package sprites;

import Gameps.CollisionInfo;
import Gameps.Game;
import Gameps.GameEnvironment;
import Gameps.Velocity;
import Geometry.Line;
import biuoop.DrawSurface;

import java.awt.Color;


/**
 * The sprites.Ball class represents a ball with a center point, radius, color, and velocity.
 * It can move within a defined area and bounce off the boundaries.
 * Author Ofek Mazor
 * ID 328285705
 */
public class Ball implements Sprite {
    private Geometry.Point center;
    private int radius;
    private java.awt.Color color;
    private Velocity v;
    private int width;
    private int height;
    private int minX, minY, maxX, maxY;
    private GameEnvironment environment;
    private double epsilon = 0.2;

    /**
     * Constructor to initialize a ball with a given center point, radius, and color.
     *
     * @param center the center point of the ball
     * @param r      the radius of the ball
     * @param color  the color of the ball
     */
    public Ball(Geometry.Point center, int r, java.awt.Color color) {
        this.center = center;
        this.radius = r;
        this.color = color;
        this.v = new Velocity(0, 0);
    }

    /**
     * Gets the x-coordinate of the center of the ball.
     *
     * @return the x-coordinate of the center
     */
    public int getX() {
        return (int) center.getX();
    }

    public void setEnvironment(GameEnvironment environment) {
        this.environment = environment;
    }

    /**
     * Gets the y-coordinate of the center of the ball.
     *
     * @return the y-coordinate of the center
     */
    public int getY() {
        return (int) center.getY();
    }

    /**
     * Sets the x-coordinate of the center of the ball.
     *
     * @param x the new x-coordinate of the center
     */
    public void setX(int x) {
        this.center = new Geometry.Point(x, this.getY());
    }

    /**
     * Sets the y-coordinate of the center of the ball.
     *
     * @param y the new y-coordinate of the center
     */
    public void setY(int y) {
        this.center = new Geometry.Point(this.getX(), y);
    }

    /**
     * Sets the width and height of the area in which the ball can move.
     *
     * @param width  the width of the area
     * @param height the height of the area
     */
    public void setWH(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the radius of the ball.
     *
     * @return the radius of the ball
     */
    public int getSize() {
        return radius;
    }

    /**
     * Gets the color of the ball.
     *
     * @return the color of the ball
     */
    public java.awt.Color getColor() {
        return color;
    }

    /**
     * Draws the ball on the given DrawSurface.
     *
     * @param surface the DrawSurface on which to draw the ball
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(color);
        surface.fillCircle((int) center.getX(), (int) center.getY(), radius);
    }

    /**
     * set a color for a ball.
     *
     * @param color the new color of the ball
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Sets the velocity of the ball.
     *
     * @param v the new velocity of the ball
     */
    public void setVelocity(Velocity v) {
        this.v = v;
    }

    /**
     * Sets the velocity of the ball.
     *
     * @param dx the change in x-coordinate per unit time
     * @param dy the change in y-coordinate per unit time
     */
    public void setVelocity(double dx, double dy) {
        this.v = new Velocity(dx, dy);
    }

    /**
     * Gets the velocity of the ball.
     *
     * @return the current velocity of the ball
     */
    public Velocity getVelocity() {
        return v;
    }

    /**
     * Moves the ball one step, checking for collisions with the boundaries of the area.
     */
    public void moveOneStep() {
        // Calculate the trajectory based on current position and velocity
        Line trajectory = new Line(this.center, this.v.applyToPoint(this.center));
        CollisionInfo info = environment.getClosestCollision(trajectory);

        if (info == null) {
            // No collision, move to the new position
            this.center = this.v.applyToPoint(this.center);
        } else {
            // Handle collision
            Geometry.Point collisionPoint = info.collisionPoint();
            if (info.collisionObject().getCollisionRectangle().isInsideRectangle(this.center)) {
                this.center = new Geometry.Point(center.getX(),
                        info.collisionObject().getCollisionRectangle().getMiny() - 2);
                this.v = info.collisionObject().hit(this, this.center, v);
                return;
            }
            double distance = trajectory.start().distance(collisionPoint);
            if (distance == 0) {
                this.v = info.collisionObject().hit(this, info.collisionPoint(), v);
            } else {
                Velocity newVelocity = info.collisionObject().hit(this, info.collisionPoint(), v);
                if (newVelocity != null) {
                    this.v = newVelocity;
                }

                // Move to the new position based on the updated velocity
                this.center = this.v.applyToPoint(this.center);
            }
        }
    }

    /**
     * Checks if a value is within the range defined by two other values.
     *
     * @param x The value to check.
     * @param y One end of the range.
     * @param z The other end of the range.
     * @return True if x is within the range [y, z], false otherwise.
     */
    public static boolean inRange(double x, double y, double z) {
        return x >= Math.min(y, z) && x <= Math.max(y, z);
    }

    /**
     * Moves the ball one step, checking for collisions with the specified rectangle.
     *
     * @param rectangle The rectangle to check for collisions.
     */
    public void moveOneStep(Geometry.Rectangle rectangle) {
        double nextX = this.center.getX() + this.v.getDx();
        double nextY = this.center.getY() + this.v.getDy();

        if (nextX + radius >= rectangle.getRightX()) {
            nextX = rectangle.getRightX() - radius;
            setVelocity(-v.getDx(), v.getDy());
        } else if (nextX - radius < rectangle.getMinx()) {
            nextX = rectangle.getMinx() + radius;
            setVelocity(-v.getDx(), v.getDy());
        }

        if (nextY + radius >= rectangle.getBottomY()) {
            nextY = rectangle.getBottomY() - radius;
            setVelocity(v.getDx(), -v.getDy());
        } else if (nextY - radius < rectangle.getMiny()) {
            nextY = rectangle.getMiny() + radius;
            setVelocity(v.getDx(), -v.getDy());
        }

        this.center = new Geometry.Point(nextX, nextY);
    }

    /**
     * Moves the ball one step, checking for collisions with the specified rectangle.
     */
    @Override
    public void timePassed() {
        moveOneStep();
    }

    /**
     * Adds the ball to the specified game.
     *
     * @param g the game to add the ball to
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }

    public void removeFromGame(Game g) {
        g.removeSprite(this);
    }
}