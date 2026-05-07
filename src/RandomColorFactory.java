import java.awt.*;

/**
 * RandomColorFactory class supplies methods to draw/fill shapes with color
 */
public class RandomColorFactory {
    /**
     * Draws the shape filled in with the color Gray
     * @param g2
     * @param shape Shape to be drawn
     */
    public static void drawGray(Graphics2D g2, Shape shape) {
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(1));
        g2.fill(shape);
        g2.setColor(Color.BLACK);
        g2.draw(shape);
        g2.setStroke(new BasicStroke(2));
    }

    /**
     * Draws the shape filled with a random color and black border
     * @param g2
     * @param shape Shape to be drawn
     */
    public static void drawRandomColor(Graphics2D g2, Shape shape) {
         g2.setColor(new Color(randomRGB(), randomRGB(), randomRGB()));
         g2.fill(shape);
         g2.setColor(Color.BLACK);
         g2.draw(shape);
    }

    /**
     * Random RGB value ranging from 100 to 249
     * @return 
     */
    public static int randomRGB() {
        return (int) (Math.random() * 150) + 100;
    }
}
