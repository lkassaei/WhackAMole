/**
 * Represents the hammer the user can use to whack moles
 */

import javax.swing.*;
import java.awt.*;

public class Hammer {
    // Window
    private final WhackAMoleViewer window;

    // Hammer position
    private int x;
    private int y;

    // Hammer image properties
    private Image leftHammerImage;
    private Image rightHammerImage;
    private int imageWidth;
    private int imageHeight;
    private final int mouseAdjust = 100;

    // Initialize everything
    public Hammer(WhackAMoleViewer window) {
        this.window = window;
        setInitialPosition();
        setImages();
    }

    // Sets the initial position to the top left corner
    public void setInitialPosition() {
        this.x = 0;
        this.y = 0;
    }

    // Sets all images and image attributes
    public void setImages() {
        this.leftHammerImage = new ImageIcon("Resources/hammer.png").getImage();
        this.rightHammerImage = new ImageIcon("Resources/hammerRightFacing.png").getImage();
        imageWidth = leftHammerImage.getWidth(window);
        imageHeight = leftHammerImage.getHeight(window);
    }

    // Resets the position of the hammer to given coordinates
    public void setPosition(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    // Logic to detect if the hammer and mole have collided based on their image sizes and locations
    public boolean hasCollided(Mole mole) {
        // Calculate the bounding box coordinates for the hammer, accounting for the mouse centering offset.
        int hammerLeftX = x - mouseAdjust;
        int hammerTopY = y - mouseAdjust;

        int hammerRightX = x - mouseAdjust + imageWidth;
        int hammerBottomY = y - mouseAdjust + imageHeight;

        // Calculate the bounding box coordinates for the mole, accounting for its centering offset.
        int moleRightX = mole.getX() - mole.getAdjust() + mole.getImageWidth();
        int moleBottomY = mole.getY() - mole.getAdjust() + mole.getImageHeight();

        // Check if there is overlap on the x-axis.
        boolean xOverlap = hammerLeftX < moleRightX && hammerRightX > mole.getX();
        // Check if there is overlap on the y-axis.
        boolean yOverlap = hammerTopY < moleBottomY && hammerBottomY > mole.getY();

        // Collision occurs if there is overlap on both the x and y axes.
        return xOverlap && yOverlap;
    }

    // Draw the hammer
    public void draw(Graphics g) {
        // If the hammer is on a certain side of the screen, draw it facing the correct way
        if (x > window.getWidth() / 2) {
            g.drawImage(rightHammerImage, x - mouseAdjust, y - mouseAdjust, imageWidth, imageHeight, window);
        }
        else {
            g.drawImage(leftHammerImage, x - mouseAdjust, y - mouseAdjust, imageWidth, imageHeight, window);;
        }
    }
}
