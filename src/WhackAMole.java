// WhackAMole by Lily Kassaei

import javax.swing.*;
import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class WhackAMole implements ActionListener, MouseMotionListener, MouseListener{
    // Game State Constants
    public static final int INSTRUCTION_STATE = 0;
    public static final int MAIN_STATE = 1;
    public static final int GAME_OVER_STATE = 2;

    private Mole mole;
    private final int moleCount = 3;
    private Hammer hammer;
    private ArrayList<Hole> holes;
    private WhackAMoleViewer window;

    private int points;
    private int timeRemainingTicks;
    private int state;
    private static final int GAME_DURATION_SECONDS =40;
    private static final int TICKS_PER_SECOND = 10; // Game updates 10 times per second
    public static final int INSTRUCTION_START_SECOND = 35;
    private static final int SLEEP_TIME = 100;

    public WhackAMole() {
        // Create window
        window = new WhackAMoleViewer(this);

        // Set state to show instructions
        this.state = INSTRUCTION_STATE;

        // Create and populate holes
        this.holes = new ArrayList<Hole>();
        fillHoles();

        // Create the hammer and mole
        this.hammer = new Hammer(window);
        mole = new Mole(window, holes);
        mole.startRandomMovement();

        // Allows MouseMotionListener to receive events from this class
        this.window.addMouseMotionListener(this);
        this.window.addMouseListener(this);

        // 20 seconds
        timeRemainingTicks = GAME_DURATION_SECONDS * TICKS_PER_SECOND;
    }

    public void start() {
        Timer clock = new Timer(SLEEP_TIME, this);
        clock.start();
    }

    public Mole getMole() {
        return mole;
    }

    public Hammer getHammer() {
        return hammer;
    }

    public ArrayList<Hole> getHoles() {
        return holes;
    }

    public int getState() {
        return state;
    }

    public int getTimeRemainingSeconds() {
        return timeRemainingTicks / TICKS_PER_SECOND;
    }

    public int getPoints() {
        return points;
    }

    public void fillHoles() {
        // Row 1 (normal)
        int spacingY = 75;
        int spacingX = 300;
        int startX = 75;
        int startY = 500;

        holes.add(new Hole(window, startX, startY));
        holes.add(new Hole(window,startX + spacingX, startY));
        holes.add(new Hole(window,startX + 2 * spacingX, startY));

        holes.add(new Hole(window,startX + spacingX / 2, startY + spacingY));
        holes.add(new Hole(window,startX + spacingX / 2 + spacingX, startY + spacingY));

        holes.add(new Hole(window,startX, startY + 2 * spacingY));
        holes.add(new Hole(window,startX + spacingX, startY + 2 * spacingY));
        holes.add(new Hole(window,startX + 2 * spacingX, startY + 2 * spacingY));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timeRemainingTicks--;
        if (getTimeRemainingSeconds() > INSTRUCTION_START_SECOND) {
            this.state = INSTRUCTION_STATE;
        }
        else if (getTimeRemainingSeconds() > 0) {
            this.state = MAIN_STATE;
            mole.update();
        }
        else if (getTimeRemainingSeconds() == 0){
            state = GAME_OVER_STATE;
        }
        window.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        window.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (this.state == MAIN_STATE) {
            hammer.setPosition(e.getX(), e.getY());
            this.window.repaint();
        }
    }

    public static void main(String[] args) {
        WhackAMole game = new WhackAMole();
        game.start();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (state == MAIN_STATE && hammer.hasCollided(mole)) {
            points++;
            mole.whack();
            window.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
