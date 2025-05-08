/**
 * WhackAMole by Lily Kassaei
  */

import javax.swing.*;
import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class WhackAMole implements ActionListener, MouseMotionListener, MouseListener {

    // Game State Constants
    private int state;
    public static final int INSTRUCTION_STATE = 0;
    public static final int MAIN_STATE = 1;
    public static final int GAME_OVER_STATE = 2;

    // Game Parameters
    private static final int GAME_DURATION_SECONDS = 35; // How long the game lasts (including instructions)
    private static final int TICKS_PER_SECOND = 10; // Game updates 10 times per second
    public static final int INSTRUCTION_END_SECOND = 25; // When the instructions end
    private static final int SLEEP_TIME = 100; // Events happen every 100 ms or 1 sec

    // Game Objects

    private WhackAMoleViewer window;
    private Mole mole;
    private Hammer hammer;
    private ArrayList<Hole> holes;
    private int points; // User points
    private int timeRemainingTicks; // Keep track of how many ticks are left


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

        // Allows MouseMotionListener to receive events from this class
        this.window.addMouseMotionListener(this);
        this.window.addMouseListener(this);

        // 30 seconds
        timeRemainingTicks = GAME_DURATION_SECONDS * TICKS_PER_SECOND;
    }

    public void start() {
        // Start the timer that calls actionListener
        Timer clock = new Timer(SLEEP_TIME, this);
        clock.start();
    }

    // Getter methods

    public Mole getMole() {
        return mole; // Gives access to the Mole
    }

    public Hammer getHammer() {
        return hammer; // Gives access to the Hammer
    }

    public ArrayList<Hole> getHoles() {
        return holes; // Gives access to the Holes ArrayList
    }

    public int getState() {
        return state; // Returns the state of the game
    }

    public int getTimeRemainingSeconds() {
        return timeRemainingTicks / TICKS_PER_SECOND; // Returns the seconds remaining the the game
    }

    public int getPoints() {
        return points; // Returns the user's points/moles whacked
    }

    // Populate the holes arrayList
    public void fillHoles() {
        // Set correct spacings for positioning
        int spacingY = 75;
        int spacingX = 300;
        int startX = 75;
        int startY = 500;

        // Row 1 (normal)
        holes.add(new Hole(window, startX, startY));
        holes.add(new Hole(window,startX + spacingX, startY));
        holes.add(new Hole(window,startX + 2 * spacingX, startY));

        // Row 2 (shifted)
        holes.add(new Hole(window,startX + spacingX / 2, startY + spacingY));
        holes.add(new Hole(window,startX + spacingX / 2 + spacingX, startY + spacingY));

        // Row 3 (normal)
        holes.add(new Hole(window,startX, startY + 2 * spacingY));
        holes.add(new Hole(window,startX + spacingX, startY + 2 * spacingY));
        holes.add(new Hole(window,startX + 2 * spacingX, startY + 2 * spacingY));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Decrease the remaining game time
        timeRemainingTicks--;
        // If more than 25 seconds remain
        if (getTimeRemainingSeconds() > INSTRUCTION_END_SECOND) {
            // Draw the instructions
            this.state = INSTRUCTION_STATE;
        }
        // If the instruction time is over, draw the main game
        else if (getTimeRemainingSeconds() > 0) {
            this.state = MAIN_STATE;
            mole.update(); // Update the mole
        }
        // If the game is over then show the game over screen
        else if (getTimeRemainingSeconds() == 0){
            state = GAME_OVER_STATE;
        }
        // Update window
        window.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Only register movement when the main game is in play
        if (this.state == MAIN_STATE) {
            // Move the hammer to the mouse's location
            hammer.setPosition(e.getX(), e.getY());
            // Update window
            this.window.repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Only register clicks when the main game is in play and when the user clicks a mole
        if (state == MAIN_STATE && hammer.hasCollided(mole) && mole.isVisible()) {
            // Give the user points
            points++;
            // Trigger the mole's reaction
            mole.whack();
            // Update window
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

    public static void main(String[] args) {
        WhackAMole game = new WhackAMole();
        game.start();
    }
}
