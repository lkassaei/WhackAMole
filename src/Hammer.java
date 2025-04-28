import javax.swing.*;
import java.awt.*;

public class Hammer {
    private int x;
    private int y;
    private Image leftHammerImage;
    private Image rightHammerImage;
    private int imageWidth;
    private final int imageHeight;
    private WhackAMoleViewer window;

    private final int mouseAdjust = 100;

    public Hammer(WhackAMoleViewer window) {
        this.window = window;
        this.x = 0;
        this.y = 0;
        this.leftHammerImage = new ImageIcon("Resources/hammer.png").getImage();
        this.rightHammerImage = new ImageIcon("Resources/hammerRightFacing.png").getImage();
        imageWidth = leftHammerImage.getWidth(window);
        imageHeight = leftHammerImage.getHeight(window);
    }

    public void setX(int newX) {
        x = newX;
    }

    public void setY(int newY) {
        y = newY;
    }

    public boolean hasCollided(Mole mole) {
        // Account for the centering offset for the mouse
        int hammerLeftX = x - mouseAdjust;
        int hammerTopY = y - mouseAdjust;

        int hammerRightX = x - mouseAdjust + imageWidth;
        int hammerBottomY = y - mouseAdjust + imageHeight;

        int moleRightX = mole.getX() - mole.getAdjust() + mole.getImageWidth();
        int moleBottomY = mole.getY() - mole.getAdjust() + mole.getImageHeight();

        boolean xOverlap = hammerLeftX < moleRightX && hammerRightX > mole.getX();
        boolean yOverlap = hammerTopY < moleBottomY && hammerBottomY > mole.getY();

        if (xOverlap && yOverlap) {
            return true;
        }
        return false;
    }

    public void draw(Graphics g) {
        if (x > window.getWidth() / 2) {
            g.drawImage(rightHammerImage, x - mouseAdjust, y - mouseAdjust, imageWidth, imageHeight, null);
        }
        else {
            g.drawImage(leftHammerImage, x - mouseAdjust, y - mouseAdjust, imageWidth, imageHeight, null);;
        }
    }
}
