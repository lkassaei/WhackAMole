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

    private final int adjust = 20;

    private int x;
    private int y;

    private ArrayList<Hole> holes;
    private Hole currentHole;

    private int moveDelaySeconds;
    private int secondsBeforeMove;
    private static final int TICKS_PER_SECOND = 10;

    private boolean isVisible; // To control whether the mole is drawn
    private boolean waitingToMove; // Flag to start the delay
    private int moveDelayTicks;
    private int ticksBeforeMove;

    public Mole(WhackAMoleViewer window, ArrayList<Hole> holes) {
        this.window = window;
        this.windowWidth = window.getWidth();
        this.windowHeight = window.getHeight();
        this.holes = holes;
        this.moleImage = new ImageIcon("Resources/moleTransparent.png").getImage();
        imageWidth = moleImage.getWidth(window);
        imageHeight = moleImage.getHeight(window);
        this.currentHole = null;
        this.isVisible = false;
        this.waitingToMove = false;
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

    public int getAdjust() {
        return adjust;
    }

    public Hole getCurrentHole() {
        return currentHole;
    }

    public void setCurrentHole(Hole currentHole) {
        this.currentHole = currentHole;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void move() {
        if (!holes.isEmpty()) {
            this.isVisible = true;
            currentHole = holes.get((int) (Math.random() * holes.size()));
            this.x = currentHole.getX();
            this.y = currentHole.getY();
            currentHole.setIsOccupied(true);
            window.repaint();
        }
    }

    public void whack() {
        if (isVisible) {
            isVisible = false;
            waitingToMove = true; // Start the delay to move
        }
    }

    public void findMoveDelaySeconds() {
        moveDelaySeconds = (int)(Math.random() * 8);
    }

    public void findSecondsBeforeMove() {
        secondsBeforeMove = (int)(Math.random() * 3);
    }

    public void update() {
        findMoveDelaySeconds();
        findSecondsBeforeMove();
        if (waitingToMove) {
            moveDelayTicks++;
            ticksBeforeMove++;
            if (moveDelayTicks >= moveDelaySeconds * TICKS_PER_SECOND) {
                waitingToMove = false;
                moveDelayTicks = 0;
                ticksBeforeMove = 0;
                disappear(); // Ensure it's not visible before moving
                move();
            }
        }
    }

    public void disappear() {
        this.isVisible = false;
    }

    public void draw(Graphics g) {
        if (isVisible) {
            g.drawImage(moleImage, x - adjust, y - adjust, imageWidth, imageHeight, window);
        }
    }
}
