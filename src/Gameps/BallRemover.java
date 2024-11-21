package Gameps;

import sprites.Ball;
import sprites.Block;

/**
 * Author: Ofek Mazor
 * ID: 328285705
 * BallRemover is a listener that removes balls from the game.
 * It updates the count of remaining balls whenever a ball hits a block.
 */
public class BallRemover implements HitListener {
    private Game game;
    private Counter remainingBalls;

    /**
     * Constructor for BallRemover.
     *
     * @param game the game instance
     * @param remainingBalls the counter for the remaining balls
     */
    public BallRemover(Game game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }

    /**
     * This method is called whenever the beingHit object is hit.
     * It removes the hitter ball from the game and decreases the count
     * of remaining balls by 1.
     *
     * @param beingHit the block that is being hit
     * @param hitter the ball that hits the block
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(game);
        remainingBalls.decrease(1);
    }
}
