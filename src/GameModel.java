import java.util.ArrayList;

public class GameModel {
    private StoneContainer[][] currentState;
    private StoneContainer[][] previousState;
    private ArrayList<GameView> views;
    private boolean[] turnA;
    private boolean[] turnB;
    private int undos;
    private boolean undo;
    public GameModel() {
        this.currentState = new StoneContainer[2][7];
        this.previousState = new StoneContainer[2][7];
        this.views = new ArrayList<>();
        this.turnA = new boolean[] {false, false};
        this.turnB = new boolean[] {false, false};
    }
    public GameModel(int pits) {
        this.currentState = new StoneContainer[2][pits];
        this.previousState = new StoneContainer[2][pits];
        this.views = new ArrayList<>();
        this.turnA = new boolean[] {false, false};
        this.turnB = new boolean[] {false, false};
    }
    public boolean attach(GameView view) {
        return views.add(view);
    }
    public void move(StoneContainer c) {
        undo = false;


    }
    public void undo() {
        //Minimizes memory usage by swapping
        StoneContainer[][] temp = this.currentState;
        this.currentState = this.previousState;
        this.previousState = temp;

        //First index is current turn state
        //Second index is for previous turn state
        turnA[0] = turnA[1];
        turnB[0] = turnB[1];

        undo = true;
    }
}
