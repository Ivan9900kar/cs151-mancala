import javax.swing.*;
import java.awt.*;

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
        JFrame frame = new JFrame();
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

        // finish frame setup
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }
}
