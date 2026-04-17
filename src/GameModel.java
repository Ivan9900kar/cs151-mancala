import java.util.ArrayList;

public class GameModel {
    private final int NUM_PLAYERS = 2;
    private StoneContainer[][] containers;
    private int pits;
    private ArrayList<GameView> views;
    private int turn;
    private int undos;
    private boolean moved;
    private boolean undo;
    public GameModel(int pits) {
        this.pits = pits;
        containers = new StoneContainer[NUM_PLAYERS][pits + 1];
        initContainers();
        this.views = new ArrayList<>();
        this.turn = 0;
        this.undos = 0;
        this.moved = false;
        this.undo = false;
    }
    private void initContainers() {
        for (int i = 0; i < containers.length; i++) {
            for (int j = 0; j < pits; j++) {
                containers[i][j] = new Pit();
            }
            containers[i][containers[i].length - 1] = new Mancala();
        }
    }
    public boolean attach(GameView view) {
        return views.add(view);
    }
    public void move(int col) {
        //check if already moved
        if (moved) return;

        //check validity of move (is there a stone to take)
        int row = turn;
        int numStones = -1;
        if (containers[row][col] instanceof Pit p) {
            numStones = p.takeStones();
        }
        if (numStones == -1) return;

        //move the stones
        while (numStones > 0) {
            //do not put into opponent's mancala
            if (!(row != turn && col == containers[row].length - 1)) {
                //add to pit or own mancala
                containers[row][col].addStone();
                numStones--;
            }

            //traversal
            col = (col + 1) % (pits + 1);
            if (col == 0) row = (row + 1) % NUM_PLAYERS;
        }

        moved = true;
    }
    public void confirm() {
        turn = (turn + 1) % NUM_PLAYERS;
        undo = false;
        moved = false;
        undos = 0;
    }
    public void undo() {
        undo = true;
        moved = false;


    }
}
