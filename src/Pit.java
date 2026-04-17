public class Pit extends StoneContainer {
    public Pit() {
        super();
    }
    public Pit(int stones) {
        super(stones);
    }
    public int takeStones() {
        int ret = getStones();
        setStones(0);
        return ret;
    }
}
