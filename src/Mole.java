import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.image.ImageObserver;

public class Mole {
    private WhackAMoleViewer window;
    private int windowWidth;
    private int windowHeight;

    private Image moleImage;
    private int imageWidth;
    private int imageHeight;

    private final int adjust = 20;

    private int x;
    private int y;

    private ArrayList<Hole> holes;

    public Mole(WhackAMoleViewer window, ArrayList<Hole> holes) {
        this.window = window;
        this.windowWidth = window.getWidth();
        this.windowHeight = window.getHeight();
        this.holes = holes;
        this.moleImage = new ImageIcon("Resources/moleTransparent.png").getImage();
        imageWidth = moleImage.getWidth(window);
        imageHeight = moleImage.getHeight(window);
        this.move();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public int getAdjust() {
        return adjust;
    }

    public void move() {
        Hole currentHole = holes.get((int) (Math.random() * holes.size()));
        this.x = currentHole.getX();
        this.y = currentHole.getY();
        window.repaint();
    }

    public void draw(Graphics g) {
        g.drawImage(moleImage, x - adjust, y - adjust, imageWidth, imageHeight, window);
    }
}
