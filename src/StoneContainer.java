public class StoneContainer {
    private int stones;
    public StoneContainer() {
        stones = 0;
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
        this.stones++;
    }
}
