package sprites;

import Geometry.Point;
import Geometry.Rectangle;
import Gameps.Collidable;
import Gameps.Game;
import Gameps.Velocity;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;

/**
 * Author: Ofek Mazor
 * ID: 328285705
 * The sprites.Paddle class represents a player-controlled paddle in a game.
 * It implements both the sprites.Sprite and Gameps.Collidable interfaces.
 */
public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Rectangle rectangle;
    private Color color;
    private int speed;
    private double epsilon = 0.1;

    /**
     * Constructs a new paddle with specified parameters.
     *
     * @param rectangle the rectangle representing the paddle's dimensions and position
     * @param color     the color of the paddle
     * @param keyboard  the keyboard sensor used to control the paddle
     * @param speed     the speed at which the paddle moves
     */
    public Paddle(Rectangle rectangle, Color color, KeyboardSensor keyboard, int speed) {
        this.rectangle = rectangle;
        this.color = color;
        this.keyboard = keyboard;
        this.speed = speed;
    }

    /**
     * Moves the paddle left by a predefined speed.
     */
    public void moveLeft() {
        double newX = this.rectangle.getUpperLeft().getX() - this.speed;
        if (newX + this.rectangle.getWidth() <= 20) { // Assuming left wall is at x = 0
            newX = 780 - this.rectangle.getWidth(); // Assuming right wall is at x = 800
        }
        this.rectangle = new Rectangle(new Point(newX, this.rectangle.getUpperLeft().getY()),
                this.rectangle.getWidth(), this.rectangle.getHeight());
    }

    /**
     * Moves the paddle right by a predefined speed.
     */
    public void moveRight() {
        double newX = this.rectangle.getUpperLeft().getX() + this.speed;
        if (newX >= 780) { // Assuming right wall is at x = 800
            newX = 20; // Assuming left wall is at x = 0
        }
        this.rectangle = new Rectangle(new Point(newX, this.rectangle.getUpperLeft().getY()),
                this.rectangle.getWidth(), this.rectangle.getHeight());
    }

    /**
     * Checks keyboard input and moves the paddle accordingly.
     */
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        } else if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    /**
     * Draws the paddle on the given draw surface.
     *
     * @param d the draw surface to draw on
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillRectangle((int) this.rectangle.getUpperLeft().getX(), (int) this.rectangle.getUpperLeft().getY(),
                (int) this.rectangle.getWidth(), (int) this.rectangle.getHeight());
    }

    /**
     * Returns the collision rectangle representing the paddle's shape.
     *
     * @return the collision rectangle of the paddle
     */
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * Handles a collision with the paddle.
     *
     * @param collisionPoint  the point of collision
     * @param currentVelocity the current velocity of the colliding object
     * @return the new velocity expected after the hit
     */
    @Override
    public Velocity hit(Ball b, Point collisionPoint, Velocity currentVelocity) {
        double regionWidth = this.rectangle.getWidth() / 5;
        double hitX = collisionPoint.getX() - this.rectangle.getUpperLeft().getX();
        // Calculate the hit region
        int region = (int) (hitX / regionWidth) + 1;
        switch (region) {
            case 1:
                return Velocity.fromAngleAndSpeed(300, currentVelocity.getSpeed());
            case 2:
                return Velocity.fromAngleAndSpeed(330, currentVelocity.getSpeed());
            case 3:
                return new Velocity(currentVelocity.getDx(), -currentVelocity.getDy());
            case 4:
                return Velocity.fromAngleAndSpeed(30, currentVelocity.getSpeed());
            case 5:
                return Velocity.fromAngleAndSpeed(60, currentVelocity.getSpeed());
            default:
                return new Velocity(-currentVelocity.getDx(), currentVelocity.getDy());
        }
    }

    /**
     * Adds the paddle to the game environment.
     *
     * @param game the game to add the paddle to
     */
    public void addToGame(Game game) {
        game.addCollidable(this);
        game.addSprite(this);
    }
}
