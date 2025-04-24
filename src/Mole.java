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
    private int margin;
    private int x;
    private int y;
    private Hole currentHole;
    private ArrayList<Hole> holes;

    private static Hole moleLocation;

    private boolean hasBeenCollided;

    public final int goodMole = 1;
    public final int evilMole = 2;

    private int identifier;

    public static int moleCounter = 0;

    public Mole(WhackAMoleViewer window, ArrayList<Hole> holes) {
        moleCounter++;

        this.window = window;
        this.windowWidth = window.getWidth();
        this.windowHeight = window.getHeight();
        this.holes = holes;
        hasBeenCollided = false;
        if (moleCounter == goodMole) {
            imageWidth = 200;
            imageHeight = 150;
            margin = 50;
            this.identifier = goodMole;
            this.moleImage = new ImageIcon("Resources/moleTransparent.png").getImage();
        }
        if (moleCounter == evilMole) {
            imageWidth = 300;
            imageHeight = 350;
            margin = 150;
            this.identifier = evilMole;
            this.moleImage = new ImageIcon("Resources/evilMole.png").getImage();
        }
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

    public int getMargin() {
        return margin;
    }

    public boolean isHasBeenCollided() {
        return hasBeenCollided;
    }

    public void setHasBeenCollided(boolean hasBeenCollided) {
        this.hasBeenCollided = hasBeenCollided;
    }

    public void move() {
        this.currentHole = holes.get((int) (Math.random() * holes.size()));

        if (identifier == goodMole) {
            moleLocation = this.currentHole;
            this.x = currentHole.getX() + 20;
            this.y = currentHole.getY() + 5;
        }
        else {
            Hole previous = this.currentHole;
            do {
                this.currentHole = holes.get((int) (Math.random() * holes.size()));
            }
            while ((this.currentHole.getX() == previous.getX() && this.currentHole.getY() == previous.getY())
            || (this.currentHole.getX() == moleLocation.getX() && this.currentHole.getY() == moleLocation.getY())
            );

            this.x = currentHole.getX() - 40;
            this.y = currentHole.getY() - 90;
        }

        window.repaint();
    }

    public void draw(Graphics g) {
        g.drawImage(moleImage, x, y, imageWidth, imageHeight, window);
    }
}
