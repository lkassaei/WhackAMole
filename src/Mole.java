import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Represents a Mole in the Whack-A-Mole game that a user can whack
public class Mole {
    // Mole graphic
    private WhackAMoleViewer window;
    private Image moleImage;
    private int imageWidth;
    private int imageHeight;

    // Mole position
    private final int adjust = 20;
    private int x;
    private int y;

    // Holes and current position
    private ArrayList<Hole> holes;
    private Hole currentHole;

    // Mole states
    private boolean isVisible;
    private boolean waitingToMove;
    private boolean movingRandomly;

    // Timers and ticks
    private static final int TICKS_PER_SECOND = 10;
    private int moveDelayTicks;
    private int moveDelaySeconds; // Delay after being whacked (random)
    private int secondsBeforeMove; // How long to stay visible (random)
    private int ticksBeforeMove;

    // Constructor
    public Mole(WhackAMoleViewer window, ArrayList<Hole> holes) {
        this.window = window;
        this.holes = holes;
        this.moleImage = new ImageIcon("Resources/moleTransparent.png").getImage();
        this.imageWidth = moleImage.getWidth(window);
        this.imageHeight = moleImage.getHeight(window);
        setInitialValues();
        move();
    }

    // Initially set values
    public void setInitialValues() {
        this.currentHole = null;
        this.isVisible = false;
        this.waitingToMove = false;
        this.moveDelayTicks = 0;
        this.moveDelaySeconds = 0;
        this.secondsBeforeMove = 0;
        this.ticksBeforeMove = 0;
        this.movingRandomly = false;
    }

    // Getter methods
    public int getX() {
        return x; // Returns x coordinate of the Mole
    }

    public int getY() {
        return y;  // Returns y coordinate of the Mole
    }

    public int getImageWidth() {
        return imageWidth;  // Returns image width of the Mole
    }

    public int getImageHeight() {
        return imageHeight; // Returns image height of the Mole
    }

    public int getAdjust() {
        return adjust; // Returns adjustment needed to center the Mole image in the hole
    }

    public boolean isVisible() {
        return isVisible; // Returns visibility status of the Mole
    }

    // After the mole has been whacked
    public void move() {
        if (!holes.isEmpty()) {
            // Become visible
            this.isVisible = true;
            // Move to next hole
            currentHole = holes.get((int) (Math.random() * holes.size()));
            this.x = currentHole.getX();
            this.y = currentHole.getY();
            // Update window
            window.repaint();
            findSecondsBeforeMove(); // Set how long to stay visible
            ticksBeforeMove = 0; // Reset
        }
    }

    // Right after being whacked by the user
    public void whack() {
        if (isVisible) {
            // Become invisible and wait to move
            isVisible = false;
            waitingToMove = true;
            moveDelayTicks = 0;
            findMoveDelaySeconds(); // Set the random delay before moving after whack
            // Set position to null
            if (currentHole != null) {
                currentHole = null;
            }
            // Update
            window.repaint();
        }
    }

    // Sets random delay before the mole can move again after being whacked
    public void findMoveDelaySeconds() {
        moveDelaySeconds = (int) ((Math.random() * 3) + 1); // Random delay 1-3 seconds
    }

    // Sets random duration for how long the mole will remain visible. Once this runs out, the mole will move independently
    public void findSecondsBeforeMove() {
        secondsBeforeMove = (int) ((Math.random() * 3)); // Random visibility 1-3 seconds
    }

    // Updates the mole based on its state and timers
    public void update() {
        if (waitingToMove) {
            handleWhackedDelay();
        } else if (isVisible) {
            handleVisibilityTimer();
        } else { // Not waiting to move and not visible
            move(); // Move immediately
        }
    }

    // Handles the delay period after the mole has been whacked
    public void handleWhackedDelay(){
        // Increase timer for mole's move delay
        moveDelayTicks++;
        // If the timer is up
        if (moveDelayTicks >= moveDelaySeconds * TICKS_PER_SECOND) {
            // Move and update moving state
            waitingToMove = false;
            move();
        }
    }

    // Handles the timer for how long the mole stays visible
    public void handleVisibilityTimer() {
        // Increase timer for visibility
        ticksBeforeMove++;
        // If timer is up
        if (ticksBeforeMove >= secondsBeforeMove * TICKS_PER_SECOND) {
            // disappear, move, and start timer for next time
            disappear();
            movingRandomly = true;
        }
    }

    // Make the mole "pop down into a hole" / become invisible
    public void disappear() {
        this.isVisible = false;
        if (currentHole != null) {
            currentHole = null;
        }
        window.repaint();
    }

    public void draw(Graphics g) {
        if (isVisible) {
            g.drawImage(moleImage, x - adjust, y - adjust, imageWidth, imageHeight, window);
        }
    }
}