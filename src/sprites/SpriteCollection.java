package sprites;

import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;
/** Author: Ofek Mazor
 * ID: 328285705
 * Represents a collection of sprites.
 * holds a collection of sprites and provides methods to add, remove, and draw them.
 */
public class SpriteCollection {
    private List<Sprite> sprites;

    /**
     * Constructs a sprites.SpriteCollection object.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<>();
    }

    /**
     * Adds a sprite to the collection.
     *
     * @param s the sprite to add
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /**
     * Removes a sprite from the collection.
     *
     * @param s the sprite to remove
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }
    /**
     * Calls timePassed() on all sprites in the collection.
     */
    public void notifyAllTimePassed() {
        List<Sprite> spritesCopy = new ArrayList<>(this.sprites);
        for (Sprite s : spritesCopy) {
            s.timePassed();
        }
    }
    public Sprite getSprite(int index) {
        return this.sprites.get(index);
    }

    /**
     * Draws all sprites in the collection on the specified surface.
     *
     * @param d the surface to draw the sprites on
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : this.sprites) {
            s.drawOn(d);
        }
    }
}
