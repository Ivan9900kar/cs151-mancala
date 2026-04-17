public class Mancala extends StoneContainer {
    public Mancala() {
        super();
    }
    public Mancala(int stones) {
        super(stones);
    }
    public void addStones(int stones) {
        setStones(getStones() + stones);
    }
}
