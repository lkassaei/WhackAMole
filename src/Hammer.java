import javax.swing.*;
import java.awt.*;

public class Hammer {
    private int x;
    private int y;
    private Image hammerImage;
    private final int imageWidth = 300;
    private final int imageHeight = 300;
    private WhackAMoleViewer window;

    public Hammer(WhackAMoleViewer window) {
        this.window = window;
        this.x = 0;
        this.y = 0;
        this.hammerImage = new ImageIcon("Resources/hammer.png").getImage();
    }

    public void setX(int newX) {
        x = newX;
    }

    public void getY(int newY) {
        y = newY;
    }

    public boolean hasCollided(Mole mole) {
        int hammerRightX = x + imageWidth;
        int hammerBottomRY = y + imageHeight;

        int moleRightX = mole.getX() + mole.getImageWidth();
        int moleBottomRY = mole.getY() + mole.getImageHeight();

        boolean xOverlap = x < moleRightX && hammerRightX > mole.getX();
        boolean yOverlap = y < moleBottomRY && hammerBottomRY > mole.getY();

        if (xOverlap && yOverlap) {

            return true;
        }
        return false;
    }

    public void draw(Graphics g) {
        g.drawImage(hammerImage, x - 150, y - 150, imageWidth, imageHeight, null);;
    }
}
