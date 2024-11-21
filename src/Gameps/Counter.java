package Gameps;

/**
 * author Ofek Mazor
 * ID 328285705
 * The Counter class represents a simple counter that can increase,
 * decrease, and retrieve its current value.
 */
public class Counter {
    private int number;

    /**
     * Constructs a Counter with an initial value.
     *
     * @param number the initial value of the counter
     */
    public Counter(int number) {
        this.number = number;
    }

    /**
     * Increases the counter by a specified amount.
     *
     * @param number the amount to increase the counter by
     */
    void increase(int number) {
        this.number += number;
    }

    /**
     * Decreases the counter by a specified amount.
     *
     * @param number the amount to decrease the counter by
     */
    void decrease(int number) {
        this.number -= number;
    }

    /**
     * Retrieves the current value of the counter.
     *
     * @return the current value of the counter
     */
    int getValue() {
        return this.number;
    }
}
