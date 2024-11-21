package sprites;

import Geometry.Line;
import Geometry.Point;
import Geometry.Rectangle;
import biuoop.DrawSurface;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import Gameps.Game;
import Gameps.Velocity;
import Gameps.Collidable;
import Gameps.HitListener;
import Gameps.HitNotifier;


/**
 * Author: Ofek Mazor
 * ID: 328285705
 * The sprites.Block class represents a block that can collide with other objects and can be drawn on the screen.
 * It implements the Gameps.Collidable and sprites.Sprite interfaces, allowing it to participate in collision detection
 * and be rendered as part of the game's graphics.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private static final double EPSILON = 0.00001;
    private Rectangle rectangle;
    private Color color;
    private Game game;
    private List<HitListener> hitListeners = new ArrayList<HitListener>();

    /**
     * Constructs a sprites.Block with the given rectangle and color.
     *
     * @param rectangle the rectangle representing the block's shape and position
     * @param color     the color of the block
     */
    public Block(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    /**
     * Constructs a sprites.Block with the specified upper-left corner, width, height, and color.
     *
     * @param upperLeft the upper-left corner of the block
     * @param width     the width of the block
     * @param height    the height of the block
     * @param color     the color of the block
     */
    public Block(Point upperLeft, double width, double height, Color color) {
        this.rectangle = new Rectangle(upperLeft, width, height);
        this.color = color;
    }

    /**
     * Returns the rectangle representing the block's shape and position.
     *
     * @return the rectangle representing the block
     */
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * Sets the game that this block is part of.
     *
     * @param game the game to set
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Handles the collision of the block with another object.
     *
     * @param collisionPoint  the point where the collision occurred
     * @param currentVelocity the current velocity of the object colliding with the block
     * @return the new velocity after the collision
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
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
                // todo if you want that only block without the same color will remove;
//                if (!this.ballColorMatch(hitter)) {
                    this.notifyHit(hitter);
                    hitter.setColor(this.color);
//                }
//                score++;
//                this.removeFromGame(this.game);
            }
        }
        return newVelocity; // No change if the collision point is not on the edge
    }

    /**
     * Draws the block on the given draw surface.
     *
     * @param surface the draw surface to draw on
     */
    public void drawOn(DrawSurface surface) {
        int x = (int) rectangle.getUpperLeft().getX();
        int y = (int) rectangle.getUpperLeft().getY();
        int width = (int) rectangle.getWidth();
        int height = (int) rectangle.getHeight();
        surface.setColor(color);
        surface.fillRectangle(x, y, width, height);
        surface.setColor(Color.BLACK);
        surface.drawRectangle(x, y, width, height);
    }

    /**
     * Called to notify the block that time has passed. Currently, it does nothing.
     */
    public void timePassed() {
        // Do nothing
    }

    /**
     * Adds the block to the specified game.
     *
     * @param game the game to add the block to
     */
    public void addToGame(Game game) {
        this.setGame(game);
        game.addCollidable(this);
        game.addSprite(this);
    }

    /**
     * Removes the block from the specified game.
     *
     * @param g the game to remove the block from
     */
    public void removeFromGame(Game g) {
        g.removeCollidable(this);
        g.removeSprite(this);
    }

    @Override
    public boolean isBlock() {
        return true;
    }

    /**
     * Returns the color of this object.
     *
     * @return the color of this object
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Checks if the color of this object matches the color of a given ball.
     *
     * @param b the ball to compare color with
     * @return true if the colors match, false otherwise
     */
    public boolean ballColorMatch(Ball b) {
        return this.color.equals(b.getColor());
    }

    @Override
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }

    /**
     * Notifies all registered hit listeners about a hit event.
     *
     * @param hitter the ball that hits this object
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    @Override
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }
}