import javax.swing.*;
import java.awt.*;

public class Hammer {
    private int x;
    private int y;
    private Image leftHammerImage;
    private Image rightHammerImage;
    private final int imageWidth = 200;
    private final int imageHeight = 200;
    private final int margin = 150;
    private WhackAMoleViewer window;

    public Hammer(WhackAMoleViewer window) {
        this.window = window;
        this.x = 0;
        this.y = 0;
        this.leftHammerImage = new ImageIcon("Resources/hammer.png").getImage();
        this.rightHammerImage = new ImageIcon("Resources/hammerRightFacing.png").getImage();
    }

    public void setX(int newX) {
        x = newX;
    }

    public void getY(int newY) {
        y = newY;
    }

    public boolean hasCollided(Mole mole) {
        // Account for the centering offset for the mouse
        int hammerLeftX = x - 100;
        int hammerTopY = y - 100;

        int hammerRightX = x + imageWidth - margin;
        int hammerBottomRY = y + imageHeight - margin;

        int moleRightX = mole.getX() + mole.getImageWidth() - mole.getMargin();
        int moleBottomRY = mole.getY() + mole.getImageHeight() - mole.getMargin();

        boolean xOverlap = hammerLeftX < moleRightX && hammerRightX > mole.getX();
        boolean yOverlap = hammerTopY < moleBottomRY && hammerBottomRY > mole.getY();

        if (xOverlap && yOverlap) {
            return true;
        }
        return false;
    }

    public void draw(Graphics g) {
        if (x > window.getWidth() / 2) {
            g.drawImage(rightHammerImage, x - 100, y - 100, imageWidth, imageHeight, null);
        }
        else {
            g.drawImage(leftHammerImage, x - 100, y - 100, imageWidth, imageHeight, null);;
        }

    }
}
