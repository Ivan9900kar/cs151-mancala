import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameController {
    private final GameModel model;

    public GameController(GameModel model, GameView view) {
        this.model = model;
        view.addConfirmActionListener(e -> model.confirm());
        view.addUndoActionListener(e -> model.undo());
        view.addMouseListener(new MyMouseListener());
        view.update();
    }

    private class MyMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            StoneContainer[][] containers = model.getContainers();
            for (StoneContainer[] row : containers) {
                for (StoneContainer col : row) {
                    if (col instanceof Pit pit && pit.contains(event.getPoint())) {
                        model.move(pit.getRow(), pit.getCol());
                    }
                }
            }
        }
    }
}
