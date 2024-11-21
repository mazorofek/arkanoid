package Geometry;

/**
 * The Geometry.Point class represents a point in a 2D space.
 * Author Ofek Mazor
 * ID 328285705
 */
public class Point {
    // Fields to store the x and y coordinates of the point.
    private double x;
    private double y;

    /**
     * Constructor to initialize a point with given x and y coordinates.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    /**
     * Calculates the distance between this point and another point.
     *
     * @param other the other point
     * @return the distance between this point and the other point
     */
    public double distance(Point other) {
        return Math.sqrt(Math.pow(x - other.getX(), 2) + Math.pow(y - other.getY(), 2));
    }

    /**
     * Checks if this point is equal to another point.
     *
     * @param other the other point to compare with
     * @return true if the points are equal, false otherwise
     */
    public boolean equals(Point other) {
        double epsilon = 0.0000001;

        if (other == null) {
            return false;
        }
        return (Math.abs(other.x - this.x)) < epsilon && (Math.abs(other.y - this.y)) < epsilon;
    }

    /**
     * Gets the x-coordinate of this point.
     *
     * @return the x-coordinate of this point
     */
    public double getX() {
        return this.x;
    }

    /**
     * Gets the y-coordinate of this point.
     *
     * @return the y-coordinate of this point
     */
    public double getY() {
        return this.y;
    }

    /**
     * Sets the x-coordinate of this point.
     *
     * @param x the new x-coordinate
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of this point.
     *
     * @param y the new y-coordinate
     */
    public void setY(double y) {
        this.y = y;
    }
}
