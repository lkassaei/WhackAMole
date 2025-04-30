import javax.swing.*;
import java.awt.*;

public class Hammer {
    private int x;
    private int y;
    private Image leftHammerImage;
    private Image rightHammerImage;
    private int imageWidth;
    private int imageHeight;
    private final WhackAMoleViewer window;

    private final int mouseAdjust = 100;

    public Hammer(WhackAMoleViewer window) {
        this.window = window;
        setInitialPosition();
        setImages();
    }

    public void setInitialPosition() {
        this.x = 0;
        this.y = 0;
    }

    public void setImages() {
        this.leftHammerImage = new ImageIcon("Resources/hammer.png").getImage();
        this.rightHammerImage = new ImageIcon("Resources/hammerRightFacing.png").getImage();
        imageWidth = leftHammerImage.getWidth(window);
        imageHeight = leftHammerImage.getHeight(window);
    }

    public void setPosition(int newX, int newY) {
        this.x = newX;
        this.y = newY;
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

        return xOverlap && yOverlap;
    }

    public void draw(Graphics g) {
        if (x > window.getWidth() / 2) {
            g.drawImage(rightHammerImage, x - mouseAdjust, y - mouseAdjust, imageWidth, imageHeight, window);
        }
        else {
            g.drawImage(leftHammerImage, x - mouseAdjust, y - mouseAdjust, imageWidth, imageHeight, window);;
        }
    }
}
