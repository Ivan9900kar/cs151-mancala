public class Pit extends StoneContainer {
    /**
     * Default constructor of a pit.
     */
    public Pit() {
        super();
    }
    /**
     * Constructs a pit that will start with a specified amount of stones inside.
     * @param stones the stones that the pit will start out with
     */
    public Pit(int stones) {
        super(stones);
    }
    /**
     * Takes all of the stones out of the pit and returns the taken amount.
     * @return the number of stones that were inside of the pit
     */
    public int takeStones() {
        int ret = getStones();
        setStones(0);
        return ret;
    }
}
