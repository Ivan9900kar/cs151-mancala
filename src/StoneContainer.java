public class StoneContainer {
    private int stones;
    public StoneContainer() {
        this(0);
    }
    public StoneContainer(int stones) {
        this.stones = stones;
    }
    public int getStones() {
        return this.stones;
    }
    public void setStones(int stones) {
        this.stones = stones;
    }
    public void addStone() {
        stones++;
    }
    public void removeStone() {
        if (stones > 0) stones--;
    }
}
