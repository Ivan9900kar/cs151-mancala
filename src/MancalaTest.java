public class MancalaTest {
    public static void main(String[] args) {
        StoneContainer s = new StoneContainer();
        s = new Pit(3);
        if (s instanceof Pit p) {
            System.out.println(p.takeStones());
            System.out.println(p.getStones());
        }
    }
}
