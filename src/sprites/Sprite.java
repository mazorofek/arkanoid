package sprites;

import biuoop.DrawSurface;
public interface Sprite {
    /**
     * author: Ofek Mazor
     * ID: 328285705
     * Adds the sprite to the game.
     * Draws the sprite on the surface.
     *
     * @param d the surface to draw the sprite on
     */
    void drawOn(DrawSurface d);

    /**
     * Notifies the sprite that time has passed.
     */
    void timePassed();
}
