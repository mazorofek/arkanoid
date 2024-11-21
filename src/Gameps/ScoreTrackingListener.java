package Gameps;

import sprites.Ball;
import sprites.Block;

/**
 * Author: Ofek Mazor
 * ID: 328285705
 * The ScoreTrackingListener class implements the HitListener interface
 * to track the score in the game.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * Constructs a ScoreTrackingListener with a given score counter.
     *
     * @param scoreCounter the counter to track the score
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * This method is called whenever the beingHit object is hit.
     * It increases the score by 5.
     *
     * @param beingHit the block that is being hit
     * @param hitter the ball that hits the block
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        this.currentScore.increase(5);
    }

    /**
     * Returns the current score counter.
     *
     * @return the current score counter
     */
    public Counter getScoreTrackingListener() {
        return this.currentScore;
    }
}
