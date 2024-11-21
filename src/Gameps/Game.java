package Gameps;

import Geometry.Point;
import Geometry.Rectangle;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;
import sprites.*;

import java.awt.*;


/**
 * Author: Ofek Mazor
 * ID: 328285705
 * The Gameps.Game class is responsible for setting up and running the Arkanoid game.
 * It initializes the game environment, including the paddle, balls, walls, and blocks,
 * and handles the game loop.
 */
public class Game {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private KeyboardSensor keyboard;
    private Counter counterBlocks;
    private Counter remainingBlocks;
    private Counter score;
    private Counter remainingBalls; // Gameps.Counter for remaining balls
    private static final int PADDLE_WIDTH = 150;
    private static final int PADDLE_HEIGHT = 20;
    private static final int PADDLE_SPEED = 11;
    private ScoreTrackingListener scoreTrackingListener;

    /**
     * Constructor for the Gameps.Game class.
     * Initializes the sprite collection and game environment.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.counterBlocks = new Counter(0);
        this.remainingBlocks = new Counter(0);
        this.remainingBalls = new Counter(0);
        this.score = new Counter(0);
        this.scoreTrackingListener = new ScoreTrackingListener(this.score);
    }

    /**
     * Returns the sprite collection of the game.
     *
     * @return the sprite collection.
     */
    public SpriteCollection getSprites() {
        return sprites;
    }

    /**
     * Adds a collidable object to the game environment.
     *
     * @param c the collidable object to add.
     */
    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }

    /**
     * Adds a sprite to the sprite collection.
     *
     * @param s the sprite to add.
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    /**
     * Returns the game environment.
     *
     * @return the game environment.
     */
    public GameEnvironment getEnvironment() {
        return this.environment;
    }

    /**
     * Initializes the game by setting up the GUI, keyboard, paddle, balls, walls, and blocks.
     */
    public void initialize() {
        BallRemover ballRemover = new BallRemover(this, this.remainingBalls); // Create a Gameps.BallRemover
        this.gui = new GUI("Arkanoid", 800, 600);
        this.keyboard = gui.getKeyboardSensor();
        Ball ball1 = new Ball(new Point(400, 500), 6, Color.blue);
        ball1.setVelocity(Velocity.fromAngleAndSpeed(65, 7));
        ball1.setEnvironment(this.environment);
        ball1.addToGame(this);
        remainingBalls.increase(1);
        Ball ball2 = new Ball(new Point(400, 500), 6, Color.red);
        ball2.setVelocity(Velocity.fromAngleAndSpeed(70, 7));
        ball2.setEnvironment(this.environment);
        ball2.addToGame(this);
        remainingBalls.increase(1);
        Ball ball3 = new Ball(new Point(400, 500), 6, Color.yellow);
        ball3.setVelocity(Velocity.fromAngleAndSpeed(190, 7));
        ball3.setEnvironment(this.environment);
        ball3.addToGame(this);
        remainingBalls.increase(1);
        Block deathRegion = new Block(new Rectangle(new Point(0, 600), 800, 14), Color.BLACK);
        deathRegion.addHitListener(ballRemover);
        deathRegion.addToGame(this);

        // Create paddle
        Paddle paddle = new Paddle(new Rectangle(new Point((double) (800 - PADDLE_WIDTH) / 2, 600 - PADDLE_HEIGHT - 20),
                PADDLE_WIDTH, PADDLE_HEIGHT),
                new Color(255, 69, 0), keyboard, PADDLE_SPEED);
        paddle.addToGame(this);

        // Create walls with a border effect
        Wall leftWall = new Wall(new Rectangle(new Point(0, 0), 20, 600), new Color(169, 169, 169));
        leftWall.addToGame(this);

        Wall rightWall = new Wall(new Rectangle(new Point(780, 0), 20, 600), new Color(169, 169, 169));
        rightWall.addToGame(this);

        Wall topWall = new Wall(new Rectangle(new Point(0, 0), 800, 20), new Color(169, 169, 169));
        topWall.addToGame(this);

        BlockRemover blockRemover = new BlockRemover(this, counterBlocks); // Create a Gameps.BlockRemover

        // Create blocks with colors comfortable for a light blue background
        Color[] colors = {new Color(255, 255, 0), new Color(255, 140, 0), new Color(0, 128, 0),
                new Color(75, 0, 130), new Color(255, 20, 147), new Color(0, 191, 255)};

        int[] blocksInRow = {12, 11, 10, 9, 8, 7};
        int yPosition = 100;

        for (int i = 0; i < colors.length; i++) {
            for (int j = 0; j < blocksInRow[i]; j++) {
                Block block = new Block(new Rectangle(new Point(50 + j * 50, yPosition), 50, 20), colors[i]);
                block.addHitListener(blockRemover); // Register the Gameps.BlockRemover as a listener to the block
                block.addHitListener(scoreTrackingListener);
                remainingBlocks.increase(1);
                block.addToGame(this);
            }
            yPosition += 20;
        }
    }

    /**
     * Starts the game loop, updating and rendering the game at a fixed frame rate.
     */
    public void run() {
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        Sleeper sleeper = new Sleeper();

        while (!checkWin() && remainingBalls.getValue() != 0) {
            long startTime = System.currentTimeMillis();
            DrawSurface d = gui.getDrawSurface();
            d.setColor(new Color(173, 216, 230)); // Light blue background
            d.fillRectangle(0, 0, 800, 600);
            d.setColor(new Color(0, 0, 0)); // Black color for text
            d.drawText(600, 80, "Score: " + score.getValue(), 16);
            this.sprites.drawAllOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();

            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }

        // Display victory message after the game loop
        DrawSurface c = gui.getDrawSurface();
        c.setColor(new Color(0, 0, 0)); // Black color for text
        if (checkWin()) {
            c.drawText(200, 500, "You Won! Your score is " + (score.getValue() + 100), 32);
        } else if (remainingBalls.getValue() == 0) {
            c.drawText(200, 500, "Game Over. Your score is " + score.getValue(), 32);
        }
        gui.show(c);
        sleeper.sleepFor(3000); // Show the message for 3 seconds before closing
        gui.close();
    }

    /**
     * Removes a collidable object from the game environment.
     *
     * @param c the collidable object to remove.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Removes a sprite from the sprite collection.
     *
     * @param s the sprite to remove.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Returns the count of blocks in the game environment.
     *
     * @return the number of blocks.
     */
    public int getsBlocksCounter() {
        return this.environment.blockCount();
    }

    /**
     * Check if the players wom.
     *
     * @return true if the player won, false otherwise
     */
    public Boolean checkWin() {
        // The game ends if there are no blocks left or if there are no more balls
        return this.counterBlocks.getValue() == remainingBlocks.getValue();
    }
}
