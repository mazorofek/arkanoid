package Geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a rectangle defined by its upper-left and lower-right corners.
 * The rectangle is defined by its minimum x and y coordinates and its maximum x and y coordinates.
 * This class provides various methods to interact with the rectangle, such as checking for intersections
 * with lines, drawing the rectangle, and getting its dimensions and corners.
 * Author: Ofek Mazor
 * ID: 328285705
 */
public class Rectangle {
    private double minx;
    private double miny;
    private double maxX;
    private double maxy;
    private Line[] lines;

    /**
     * Constructs a rectangle with specified upper-left corner, width, and height.
     *
     * @param upperLeft the upper-left corner of the rectangle
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.minx = upperLeft.getX();
        this.miny = upperLeft.getY();
        this.maxX = upperLeft.getX() + width;
        this.maxy = upperLeft.getY() + height;
        this.lines = new Line[4];
        initializeLines();
    }

    /**
     * Initializes the lines that form the edges of the rectangle.
     */
    private void initializeLines() {
        lines[0] = new Line(getUpperLeft(), getUpperRight());
        lines[1] = new Line(getUpperRight(), getLowerRight());
        lines[2] = new Line(getLowerRight(), getLowerLeft());
        lines[3] = new Line(getLowerLeft(), getUpperLeft());
    }

    /**
     * Returns the width of the rectangle.
     *
     * @return the width of the rectangle
     */
    public double getWidth() {
        return Math.abs(minx - maxX);
    }

    /**
     * Returns the height of the rectangle.
     *
     * @return the height of the rectangle
     */
    public double getHeight() {
        return Math.abs(miny - maxy);
    }

    /**
     * Returns the upper-left corner of the rectangle.
     *
     * @return the upper-left corner of the rectangle
     */
    public Point getUpperLeft() {
        return new Point(minx, miny);
    }

    /**
     * Returns the upper-right corner of the rectangle.
     *
     * @return the upper-right corner of the rectangle
     */
    public Point getUpperRight() {
        return new Point(maxX, miny);
    }

    /**
     * Returns the lower-left corner of the rectangle.
     *
     * @return the lower-left corner of the rectangle
     */
    public Point getLowerLeft() {
        return new Point(minx, maxy);
    }

    /**
     * Returns the lower-right corner of the rectangle.
     *
     * @return the lower-right corner of the rectangle
     */
    public Point getLowerRight() {
        return new Point(maxX, maxy);
    }

    /**
     * Returns a list of collision points between the rectangle and a given line.
     *
     * @param trajectory the line to check for collisions
     * @return a list of collision points
     */
    public List<Point> getCollisionPoint(Line trajectory) {
        List<Point> points = new ArrayList<>();
        for (Line l : this.lines) {
            if (l.isIntersecting(trajectory)) {
                points.add(l.intersectionWith(trajectory));
            }
        }
        return points;
    }

    /**
     * Checks if the rectangle intersects with a line.
     *
     * @param line the line to check intersection with
     * @return true if the rectangle intersects with the line, false otherwise
     */
    public boolean isIntersecting(Line line) {
        for (Line l : this.lines) {
            if (l.isIntersecting(line)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns an array of the lines representing the edges of the rectangle.
     *
     * @return an array of the lines representing the edges of the rectangle
     */
    public Line[] getLines() {
        return this.lines.clone();
    }

    /**
     * Finds the intersection point between the rectangle and a line, if it exists.
     *
     * @param line the line to find intersection with
     * @return the intersection point if it exists, null otherwise
     */
    public Point intersectionWith(Line line) {
        for (Line l : this.lines) {
            if (l.isIntersecting(line)) {
                return l.intersectionWith(line);
            }
        }
        return null;
    }
    /**
     * Returns the minimum x-coordinate of the rectangle.
     *
     * @return the minimum x-coordinate
     */
    public double getMinx() {
        return minx;
    }

    /**
     * Returns the minimum y-coordinate of the rectangle.
     *
     * @return the minimum y-coordinate
     */
    public double getMiny() {
        return miny;
    }

    /**
     * Returns the x-coordinate of the right edge of the rectangle.
     *
     * @return the x-coordinate of the right edge
     */
    public double getRightX() {
        return maxX;
    }

    /**
     * Returns the y-coordinate of the bottom edge of the rectangle.
     *
     * @return the y-coordinate of the bottom edge
     */
    public double getBottomY() {
        return maxy;
    }

    /**
     * Checks if a point is inside the rectangle.
     *
     * @param p the point to check
     * @return true if the point is inside the rectangle, false otherwise
     */
    public boolean isInside(Point p) {
        return p.getX() >= minx && p.getX() <= maxX && p.getY() >= miny && p.getY() <= maxy;
    }
    /**
     * Checks if a point is inside the rectangle.
     *
     * @param p the point to check
     * @return true if the point is inside the rectangle, false otherwise
     */
    public boolean isInsideRectangle(Point p) {
        return p.getX() > minx && p.getX() < maxX && p.getY() > miny && p.getY() < maxy;
    }
}
