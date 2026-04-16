public class Pit extends StoneContainer {
    public Pit() {

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
