import javax.swing.*;
import java.awt.*;

public class MancalaTest {
    public static void main(String[] args) {
        play();
    }
    public static void play() {
        GameModel model = new GameModel(6);

        JFrame frame = new JFrame();
        frame.setSize(1500, 800);

        GameView view = model.getCurrentView();
        frame.add(view, BorderLayout.CENTER);

        int numStones = view.stonesMenu();
        model.setStartingStones(numStones);

        new GameController(model, view);

        //frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }
}
