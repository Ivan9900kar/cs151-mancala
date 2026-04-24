import javax.swing.*;
import javax.swing.event.ChangeListener;

public class GameController extends JPanel {
    private GameModel model;
    public GameController(GameModel model) {
        this.model = model;
        addButtons();
    }
    private void addButtons() {
        JButton confirm = new JButton("Confirm Move");
        confirm.addActionListener(e -> model.confirm());
        add(confirm);

        JButton undo = new JButton("Undo Move");
        confirm.addActionListener(e -> model.undo());
        add(undo);
    }
}
