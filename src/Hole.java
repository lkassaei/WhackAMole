import javax.swing.*;
import java.awt.*;

public class Hole {
    private WhackAMoleViewer window;
    private int windowWidth;
    private int windowHeight;
    private int x;
    private int y;
    private Image holeImage;
    private final int imageWidth = 250;
    private final int imageHeight = 200;

    public Hole(WhackAMoleViewer window, int x, int y) {
        this.window = window;
        this.windowWidth = window.getWidth();
        this.windowHeight = window.getHeight();
        this.x = x;
        this.y = y;
        this.holeImage = new ImageIcon("Resources/moleInHole.png").getImage();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void draw(Graphics g) {
        g.drawImage(holeImage, x, y, imageWidth, imageHeight, window);
    }

}
