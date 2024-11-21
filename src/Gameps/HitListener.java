package Gameps;

import sprites.Ball;
import sprites.Block;

/**
 * Author: Ofek Mazor
 * ID: 328285705
 * The HitListener interface should be implemented by any class
 * that wants to be notified when a block is hit by a ball.
 */
public interface HitListener {
    /**
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the Ball that's doing the hitting.
     *
     * @param beingHit the block that is being hit
     * @param hitter the ball that hits the block
     */
    void hitEvent(Block beingHit, Ball hitter);
}
