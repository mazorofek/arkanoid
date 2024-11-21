package Gameps;

import sprites.Ball;
import sprites.Block;

/**
 * Author: Ofek Mazor
 * ID: 328285705
 * BlockRemover is in charge of removing blocks from the game and keeping count
 * of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {
    private Game game;
    private Counter remainingBlocks;

    /**
     * Constructor for BlockRemover.
     *
     * @param game the game instance
     * @param remainingBlocks the counter for the remaining blocks
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    /**
     * This method is called whenever the beingHit object is hit.
     * It removes the beingHit block from the game and removes this listener
     * from the block being removed.
     *
     * @param beingHit the block that is being hit
     * @param hitter the ball that hits the block
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.removeFromGame(this.game);
        beingHit.removeHitListener(this);
        this.remainingBlocks.increase(1);
    }
}
