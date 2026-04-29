import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class GameController extends JPanel {
    private GameModel model;
    public GameController(GameModel model) {
        this.model = model;
        addButtons();
    }
    private void addButtons() {
        JButton confirm = new JButton("Confirm Move");
        confirm.setPreferredSize(new Dimension(200, 50));
        confirm.addActionListener(e -> model.confirm());
        add(confirm);

        JButton undo = new JButton("Undo Move");
        undo.setPreferredSize(new Dimension(200, 50));
        undo.addActionListener(e -> model.undo());
        add(undo);
    }
}
