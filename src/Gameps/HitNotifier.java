package Gameps;

/**
 * Author: Ofek Mazor
 * ID: 328285705
 * The HitNotifier interface specifies methods to add and remove HitListeners
 * for hit events.
 */
public interface HitNotifier {
    /**
     * Adds a HitListener to the list of listeners for hit events.
     *
     * @param hl the HitListener to add
     */
    void addHitListener(HitListener hl);

    /**
     * Removes a HitListener from the list of listeners for hit events.
     *
     * @param hl the HitListener to remove
     */
    void removeHitListener(HitListener hl);
}
