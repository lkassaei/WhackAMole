import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.image.ImageObserver;

public class Mole {
    private WhackAMoleViewer window;
    private int windowWidth;
    private int windowHeight;
    private Image moleImage;
    private final int imageWidth = 200;
    private final int imageHeight = 150;
    private int x;
    private int y;
    private Hole currentHole;
    private ArrayList<Hole> holes;

    public Mole(WhackAMoleViewer window, ArrayList<Hole> holes) {
        this.window = window;
        this.windowWidth = window.getWidth();
        this.windowHeight = window.getHeight();
        this.holes = holes;
        this.moleImage = new ImageIcon("Resources/moleTransparent.png").getImage();
        this.move();
    }

    public void move() {
        this.currentHole = holes.get((int)(Math.random() * holes.size()));
        this.x = currentHole.getX() + 20;
        this.y = currentHole.getY() + 5;
    }

    public void draw(Graphics g) {
        g.drawImage(moleImage, x, y, imageWidth, imageHeight, window);
    }
}
