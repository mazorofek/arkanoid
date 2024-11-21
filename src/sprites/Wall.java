package sprites;

import Geometry.Line;
import Geometry.Point;
import Geometry.Rectangle;
import Gameps.Velocity;

import java.awt.Color;

public class Wall extends Block {
    private static final double EPSILON = 0.00001;
    private Rectangle rectangle;
    /**
     * author Ofek Mazor
     * ID 328285705
     * Constructs a sprites.Wall with the given rectangle and color.
     *
     * @param rectangle the rectangle representing the wall's shape and position
     * @param color     the color of the wall
     */
    public Wall(Rectangle rectangle, Color color) {
        super(rectangle, color);
        this.rectangle = rectangle;
    }
    @Override
    public Velocity hit(Ball b, Point collisionPoint, Velocity currentVelocity) {
        Velocity newVelocity = currentVelocity;
        Line[] edges = rectangle.getLines();
        for (Line edge : edges) {
            if (edge.isPointOnLine(collisionPoint)) {
                // Check if the edge is vertical
                if (Math.abs(edge.start().getX() - edge.end().getX()) < EPSILON) {
                    newVelocity = new Velocity(-newVelocity.getDx(), newVelocity.getDy());
                }
                // Check if the edge is horizontal
                if (Math.abs(edge.start().getY() - edge.end().getY()) < EPSILON) {
                    newVelocity = new Velocity(newVelocity.getDx(), -newVelocity.getDy());
                }
            }
        }
        return newVelocity; // No change if the collision point is not on the edge
    }
    @Override
    public boolean isBlock() {
        return false;
    }
}
