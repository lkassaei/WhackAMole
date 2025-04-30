import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Mole {
    private WhackAMoleViewer window;
    private Image moleImage;
    private int imageWidth;
    private int imageHeight;
    private final int adjust = 20;
    private int x;
    private int y;
    private ArrayList<Hole> holes;
    private Hole currentHole;
    private static final int TICKS_PER_SECOND = 10;
    private boolean isVisible;
    private boolean waitingToMove; // After being whacked
    private int moveDelayTicks;
    private int moveDelaySeconds; // Delay after being whacked (random)
    private int secondsBeforeMove; // How long to stay visible (random)
    private int ticksBeforeMove;
    private boolean movingRandomly; // Flag for independent movement delay
    private int randomMoveDelaySeconds;
    private int randomMoveDelayTicks;

    public Mole(WhackAMoleViewer window, ArrayList<Hole> holes) {
        this.window = window;
        this.holes = holes;
        this.moleImage = new ImageIcon("Resources/moleTransparent.png").getImage();
        this.imageWidth = moleImage.getWidth(window);
        this.imageHeight = moleImage.getHeight(window);
        this.currentHole = null;
        this.isVisible = false;
        this.waitingToMove = false;
        this.moveDelayTicks = 0;
        this.moveDelaySeconds = 0;
        this.secondsBeforeMove = 0;
        this.ticksBeforeMove = 0;
        this.movingRandomly = false;
        this.randomMoveDelaySeconds = 0;
        this.randomMoveDelayTicks = 0;
        startRandomMovement();
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
            findSecondsBeforeMove(); // Set how long to stay visible
            ticksBeforeMove = 0;
        }
    }

    public void whack() {
        if (isVisible) {
            isVisible = false;
            waitingToMove = true;
            moveDelayTicks = 0;
            findMoveDelaySeconds(); // Set the random delay before moving after whack
            if (currentHole != null) {
                currentHole.setIsOccupied(false);
                currentHole = null;
            }
            window.repaint();
        }
    }

    public void findMoveDelaySeconds() {
        moveDelaySeconds = (int) (Math.random() * 3); // Random delay 1-3 seconds
    }

    public void findSecondsBeforeMove() {
        secondsBeforeMove = (int) (Math.random() * 3); // Random visibility 1-3 seconds
    }

    public void findRandomMoveDelaySeconds() {
        randomMoveDelaySeconds = (int) (Math.random() * 3); // Random delay for independent move 2-6 seconds
    }

    public void startRandomMovement() {
        findRandomMoveDelaySeconds();
        movingRandomly = true;
    }

    public void update() {
        if (waitingToMove) {
            moveDelayTicks++;
            if (moveDelayTicks >= moveDelaySeconds * TICKS_PER_SECOND) {
                waitingToMove = false;
                move();
            }
        } else if (isVisible) {
            ticksBeforeMove++;
            if (ticksBeforeMove >= secondsBeforeMove * TICKS_PER_SECOND) {
                disappear();
                movingRandomly = true;
                findRandomMoveDelaySeconds();
                randomMoveDelayTicks = 0;
            }
        } else if (movingRandomly) {
            randomMoveDelayTicks++;
            if (randomMoveDelayTicks >= randomMoveDelaySeconds * TICKS_PER_SECOND) {
                movingRandomly = false;
                move();
            }
        }
    }

    public void disappear() {
        this.isVisible = false;
        if (currentHole != null) {
            currentHole.setIsOccupied(false);
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