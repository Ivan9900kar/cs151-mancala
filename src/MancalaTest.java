import javax.swing.*;

public class MancalaTest {
    public static void main(String[] args) {
        GameModel model = new GameModel(6);
        model.setStartingStones(4);
        JFrame frame = new JFrame();
        frame.setSize(1500, 800);
        GameView view = model.getCurrentView();
        frame.add(view);

        //frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }
}
