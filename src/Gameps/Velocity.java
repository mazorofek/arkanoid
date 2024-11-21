package Gameps;

import Geometry.Point;

/**
 * Gameps.Velocity specifies the change in position on the `x` and the `y` axes.
 * Author Ofek Mazor
 * ID 328285705
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * Constructs a new velocity with the specified change in position on the x and y axes.
     *
     * @param dx the change in x coordinate
     * @param dy the change in y coordinate
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Takes a point with position (x,y) and returns a new point with position (x+dx, y+dy).
     *
     * @param p the point to apply the velocity to
     * @return a new point after applying the velocity
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + dx, p.getY() + dy);
    }

    /**
     * Creates a new velocity from an angle and speed.
     *
     * @param angle the angle in degrees
     * @param speed the speed of the velocity
     * @return a new velocity based on the angle and speed
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = Math.sin(Math.toRadians(angle)) * speed;
        double dy = -Math.cos(Math.toRadians(angle)) * speed;
        return new Velocity(dx, dy);
    }

    /**
     * Gets the change in x coordinate.
     *
     * @return the change in x coordinate
     */
    public double getDx() {
        return dx;
    }

    /**
     * Gets the change in y coordinate.
     *
     * @return the change in y coordinate
     */
    public double getDy() {
        return dy;
    }

    /**
     * Gets the speed of the velocity.
     *
     * @return the speed of the velocity
     */
    public double getSpeed() {
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Reverses the direction of the x component of the velocity.
     *
     * @return a new velocity with the x component reversed
     */
    public Velocity reverseX() {
        return new Velocity(-dx, dy);
    }

    /**
     * Reverses the direction of the y component of the velocity.
     *
     * @return a new velocity with the y component reversed
     */
    public Velocity reverseY() {
        return new Velocity(dx, -dy);
    }
}
