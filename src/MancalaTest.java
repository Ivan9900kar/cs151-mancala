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
        // model
        GameModel model = new GameModel(6);

        // game window
        JFrame frame = new JFrame("Mancala Game (CS 151 - Group 7)");
        frame.setSize(1500, 800);

        // view
        GameView view = model.getCurrentView();
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
