package Geometry;

/**
 * A class representing a line segment in a 2D space.
 * *  Author Ofek Mazor
 * ID 328285705
 */
public class Line {
    private final double epsilon = 0.000001;
    private Point start;
    private Point end;

    /**
     * Constructs a line given two points.
     *
     * @param start the start point of the line
     * @param end   the end point of the line
     */
    public Line(Point start, Point end) {
        if (start.getX() == end.getX()) {
            if (start.getY() > end.getY()) {
                this.start = end;
                this.end = start;
            } else {
                this.start = start;
                this.end = end;
            }
        } else if (start.getX() > end.getX()) {
            this.start = end;
            this.end = start;
        } else {
            this.start = start;
            this.end = end;
        }
    }

    /**
     * Constructs a line given two sets of coordinates.
     *
     * @param x1 the x-coordinate of the start point
     * @param y1 the y-coordinate of the start point
     * @param x2 the x-coordinate of the end point
     * @param y2 the y-coordinate of the end point
     */
    public Line(double x1, double y1, double x2, double y2) {
        start = new Point(x1, y1);
        end = new Point(x2, y2);
    }

    /**
     * Returns the length of the line.
     *
     * @return the length of the line
     */
    public double length() {
        return start.distance(end);
    }

    /**
     * Returns the middle point of the line.
     *
     * @return the middle point of the line
     */
    public Point middle() {
        double x = (start.getX() + end.getX()) / 2;
        double y = (start.getY() + end.getY()) / 2;
        return new Point(x, y);
    }

    /**
     * Returns the start point of the line.
     *
     * @return the start point of the line
     */
    public Point start() {
        return start;
    }

    /**
     * Returns the end point of the line.
     *
     * @return the end point of the line
     */
    public Point end() {
        return end;
    }

    /**
     * Returns true if this line intersects with both other lines, false otherwise.
     *
     * @param other1 the first other line
     * @param other2 the second other line
     * @return true if this line intersects with both other lines, false otherwise
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return this.isIntersecting(other1) && this.isIntersecting(other2);
    }

    /**
     * Returns the intersection point if the lines intersect, and null otherwise.
     *
     * @param other the other line to check intersection with
     * @return the intersection point if the lines intersect, null otherwise
     */
    public Point intersectionWith(Line other) {
        if (this.isIntersecting(other)) {
            if (this.equals(other)) {
                return null;
            }
            if (this.start.equals(other.start) || this.start.equals(other.end)) {
                return this.start;
            }
            if (this.end.equals(other.start) || this.end.equals(other.end)) {
                return this.end;
            }
            if (this.isOnLine(other.start(), other) && this.isOnLine(other.end(), other)
                    || other.isOnLine(this.start(), this) && other.isOnLine(this.end(), this)) {
                return null;
            }
            double x1 = this.start.getX();
            double y1 = this.start.getY();
            double x2 = other.start.getX();
            double y2 = other.start.getY();
            double m1 = (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
            double m2 = (other.end.getY() - other.start.getY()) / (other.end.getX() - other.start.getX());
            if (m1 == m2) {
                return null;
            }
            double x = (m1 * x1 - m2 * x2 + y2 - y1) / (m1 - m2);
            double y = m1 * (x - x1) + y1;
            if (Math.abs(m1) == Double.POSITIVE_INFINITY) {
                x = x1;
                y = m2 * x + (y2 - m2 * x2);
                if (inRange(x, y, other)) {
                    return new Point(x, y);
                }
            }
            if (Math.abs(m2) == Double.POSITIVE_INFINITY) {
                x = x2;
                y = m1 * x + (y1 - m1 * x1);
                if (inRange(x, y, other)) {
                    return new Point(x, y);
                }
            }
            return new Point(x, y);
        }
        return null;
    }

    /**
     * Returns true if the lines are equal, false otherwise.
     *
     * @param other the other line to compare with
     * @return true if the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        return (this.start.equals(other.start) && this.end.equals(other.end)
                || this.start.equals(other.end) && this.end.equals(other.start));
    }

    /**
     * Checks if this line is vertical (parallel to the y-axis).
     *
     * @return true if the line is vertical, false otherwise
     */
    private boolean isVertical() {
        return this.start.getX() == this.end.getX();
    }

    /**
     * Checks if a given point is within a small range (epsilon) of this line.
     *
     * @param x     the x-coordinate of the point
     * @param y     the y-coordinate of the point
     * @param other the other line to compare ranges with
     * @return true if the point is within range of this line, false otherwise
     */
    public Boolean inRange(double x, double y, Line other) {
        if (x >= Math.min(this.start.getX(), this.end.getX()) - epsilon
                && x <= Math.max(this.start.getX(), this.end.getX()) + epsilon
                && x >= Math.min(other.start.getX(), other.end.getX()) - epsilon
                && x <= Math.max(other.start.getX(), other.end.getX()) + epsilon
                && y >= Math.min(this.start.getY(), this.end.getY()) - epsilon
                && y <= Math.max(this.start.getY(), this.end.getY()) + epsilon
                && y >= Math.min(other.start.getY(), other.end.getY()) - epsilon
                && y <= Math.max(other.start.getY(), other.end.getY()) + epsilon) {
            return true;
        }
        return false;
    }

    /**
     * Checks if a given point lies on this line.
     *
     * @param point the point to check
     * @param other the other line to compare with
     * @return true if the point lies on this line, false otherwise
     */
    public Boolean isOnLine(Point point, Line other) {
        double m = (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
        double x = point.getX();
        if (Math.abs(m) == Double.POSITIVE_INFINITY) {
            if (x == this.start.getX() && point.getY() <= Math.max(this.start.getY(), this.end.getY()) /*+ epsilon*/
                    && point.getY() >= Math.min(this.start.getY(), this.end.getY()) /*- epsilon*/) {
                return true;

            }
        } else {
            double y = m * (x - this.start.getX()) + this.start.getY();
            if (Math.abs(y - point.getY()) <= epsilon && inRange(x, y, other)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the intersection point with another line, if exists.
     *
     * @param other the other line to find intersection with
     * @return the intersection point if exists, null otherwise
     */
    public Boolean isIntersecting(Line other) {
        double m1 = (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
        double m2 = (other.end.getY() - other.start.getY()) / (other.end.getX() - other.start.getX());
        if (isOnLine(other.start(), other) || isOnLine(other.end(), other)
                || other.isOnLine(this.start(), other) || other.isOnLine(this.end(), other)) {
            return true;
        }

        if (Math.abs(m1 - m2) <= epsilon) {
            return false;
        }
        if (Math.abs(m1) == Double.POSITIVE_INFINITY) {
            return ifInfinity(m2, other);
        }
        if (Math.abs(m2) == Double.POSITIVE_INFINITY) {
            return other.ifInfinity(m1, this);
        }
        double x1 = this.start.getX();
        double y1 = this.start.getY();
        double x2 = other.start.getX();
        double y2 = other.start.getY();
        double x = (m1 * x1 - m2 * x2 + y2 - y1) / (m1 - m2);
        double y = m1 * (x - x1) + y1;
        return (inRange(x, y, other));
    }


    /**
     * Helper method to handle cases where the slope of the line is infinite.
     *
     * @param m     the slope of the other line
     * @param other the other line to compare with
     * @return true if the lines intersect, false otherwise
     */
    public Boolean ifInfinity(double m, Line other) {
        double x = this.start.getX();
        double y = m * x + (other.start.getY() - m * other.start().getX());
        return inRange(x, y, other);
    }

    /**
     * Finds the closest intersection point of this line with a given rectangle.
     *
     * @param rect the rectangle to check for intersection
     * @return the closest intersection point to the start of this line, or null if no intersection
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        Line[] lines = rect.getLines();
        Point closest = null;
        for (Line l : lines) {
            Point intersection = this.intersectionWith(l);
            if (closest == null) {
                closest = intersection;
            } else if (this.start.distance(intersection) < this.start.distance(closest)) {
                closest = intersection;
            }
        }
        return closest;
    }

    /**
     * Checks if a given point lies exactly on this line.
     *
     * @param point the point to check
     * @return true if the point lies exactly on this line, false otherwise
     */
    public Boolean isPointOnLine(Point point) {
        double m = (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
        double x = point.getX();
        if (Math.abs(m) == Double.POSITIVE_INFINITY) {
            if (x == this.start.getX() && point.getY()
                    <=
                    Math.max(this.start.getY(), this.end.getY()) + epsilon
                    && point.getY() >= Math.min(this.start.getY(), this.end.getY()) - epsilon) {
                return true;

            }
        } else {
            double y = m * (x - this.start.getX()) + this.start.getY();
            if (Math.abs(y - point.getY()) <= epsilon) {
                return true;
            }
        }
        return false;
    }
}
