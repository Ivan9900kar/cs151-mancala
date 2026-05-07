import java.awt.*;

public class RandomColorFactory {
    public static void draw(Graphics2D g2, Shape shape) {
         g2.setColor(new Color(randomRGB(), randomRGB(), randomRGB()));
         g2.fill(shape);
         g2.setColor(Color.BLACK);
         g2.draw(shape);
    }
    public static int randomRGB() {
        return (int) (Math.random() * 150) + 100;
    }
}
