/**
 * Title: CS 151 Mancala Project
 * GROUP 7
 * Authors: Anthony Ryabov, Darren Vu, Isaiah Mak
 */

import javax.swing.*;
import java.awt.*;

/**
 * Main class to run the Mancala game. Contains the main method and a play method to set up the game.
 */
public class MancalaTest {
    public static void main(String[] args) {
        play();
    }
    /**
     * The method to play the game.
     * Creates the model, view, and controller, and displays the game window.
     */
    public static void play() {
        // view
        GameView view = new GameView();

        // model
        int numPits = view.pitsMenu();
        GameModel model = new GameModel(numPits);
        model.attach(view);

        // game window
        JFrame frame = new JFrame("Mancala Game (CS 151 - Group 7)");
        int width = 200 + (150 * (numPits + 2));
        frame.setSize(width, 800);
        frame.add(view, BorderLayout.CENTER);

        // style setup
        view.styleMenu();

        // number of stones setup
        int numStones = view.stonesMenu();
        model.setStartingStones(numStones);

        // controller
        new GameController(model, view);

        // finish game window setup
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }
}
