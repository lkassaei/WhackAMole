/**
 * Represents a hole in the Whack-A-Mole game where the mole can appear
 */

import javax.swing.*;
import java.awt.*;

public class Hole{
    // Frontend
    private WhackAMoleViewer window;

    // Hole location
    private int x;
    private int y;

    // Hole image attributes
    private Image holeImage;
    private final int imageWidth = 250;
    private final int imageHeight = 150;

    // Constructor
    public Hole(WhackAMoleViewer window, int x, int y) {
        this.window = window;
        this.x = x;
        this.y = y;
        this.holeImage = new ImageIcon("Resources/moleInHole.png").getImage();
    }

    // Getter methods
    public int getX() {
        return x; // Returns x coordinate of the hole
    }

    public int getY() {
        return y; // Returns y coordinate of the hole
    }

    // Draws the hole on the frontend
    public void draw(Graphics g) {
        g.drawImage(holeImage, x, y, imageWidth, imageHeight, window);
    }
}
