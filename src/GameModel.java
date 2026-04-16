import java.util.ArrayList;

public class GameModel {
    private final int NUM_PLAYERS = 2;
    private StoneContainer[][] currentState;
    private StoneContainer[][] previousState;
    private ArrayList<GameView> views;
    private int turn;
    private int undos;
    private boolean undo;
    public GameModel() {
        this.currentState = new StoneContainer[NUM_PLAYERS][7];
        this.previousState = new StoneContainer[NUM_PLAYERS][7];
        this.views = new ArrayList<>();
        this.turn = 0;
    }
    public GameModel(int pits) {
        this.currentState = new StoneContainer[NUM_PLAYERS][pits];
        this.previousState = new StoneContainer[NUM_PLAYERS][pits];
        this.views = new ArrayList<>();
        this.turn = 0;
    }
    public boolean attach(GameView view) {
        return views.add(view);
    }
    public void move(StoneContainer c) {
        undo = false;
        this.previousState = this.currentState;
        this.currentState = this.previousState.clone(); //duplicate so that they don't point to the same object

        //move stuff
        for ();
        //ask if you want to undo
        undo();
        //no undo
        turn = (turn + 1) % NUM_PLAYERS;
        undos = 0;
    }
    public void undo() {
        this.currentState = this.previousState;
        undo = true;
        undos++;
    }
}
