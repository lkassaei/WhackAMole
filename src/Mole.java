import java.awt.*;
import java.util.ArrayList;

public class Mole {
    private WhackAMoleViewer window;
    private Image moleImage;
    private int imageWidth;
    private int imageHeight;
    private int x;
    private int y;
    private ArrayList<Integer> xLocations;

    private ArrayList<Integer> yLocations;
    private int windowWidth;
    private int windowHeight;

    public Mole(WhackAMoleViewer window) {
        this.window = window;
        this.windowWidth = window.getWidth();
        this.windowHeight = window.getHeight();
        // Set image
        // Set x to random x location out of possibilities
        // Set y to random y location out of possibilities


    }
}
