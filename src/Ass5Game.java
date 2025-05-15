import Gameps.Game;

/** Author: Ofek Mazor
 * ID: 328285705
 * The Ass3Game class is responsible for starting the Arkanoid game.
 * It creates an instance of the Gameps.Game class, initializes it, and starts the game.
 */
public class Ass5Game {
    /**
     * The main method to start the game.
     *
     * @param args command line arguments (not used).
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();

    }
}
