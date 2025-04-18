import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class WhackAMole implements ActionListener, MouseListener, MouseMotionListener, KeyListener {
    private Mole mole;
    private Hammer hammer;
    private ArrayList<Hole> holes;
    private final int numHoleRows = 2;
    private final int numHoleCols = 4;
    private final int margin = 200;
    private WhackAMoleViewer window;
    private int points;

    private int state;
    public static final int INSTRUCTION_STATE = 0;
    public static final int MAIN_STATE = 1;
    public static final int GAME_OVER_STATE = 2;

    private Timer clock;
    private final int DELAY_IN_MILISECONDS = 1000;
    private static final int SLEEP_TIME = 110;

    private int counter;



    public WhackAMole() {
        window = new WhackAMoleViewer(this);
        this.state = INSTRUCTION_STATE;
        holes = new ArrayList<Hole>();
        holes.add(new Hole(window, 50, 500));
        holes.add(new Hole(window, 190, 570));
        holes.add(new Hole(window, 330, 490));
        holes.add(new Hole(window, 470, 580));
        holes.add(new Hole(window, 610, 500));
        holes.add(new Hole(window, 750, 570));

        this.mole = new Mole(window, holes);
        this.hammer = new Hammer(window);
        window.addKeyListener(this); // Required for KeyListener
        this.window.addMouseListener(this);
        this.window.addMouseMotionListener(this);

        counter = 200;

        clock = new Timer(DELAY_IN_MILISECONDS, this);
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

    public int getCounter() {
        return counter;
    }



    public int getPoints() {
        return points;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        counter--;
        if (counter == 0) {
            state = GAME_OVER_STATE;
        }
        window.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        window.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        window.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        window.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        window.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        window.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        window.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        window.repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        window.repaint();
    }

    public static void main(String[] args) {
        WhackAMole game = new WhackAMole();
        Timer clock = new Timer(SLEEP_TIME, game);
        clock.start();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        window.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        hammer.setX(e.getX());
        hammer.getY(e.getY());
        if (hammer.hasCollided(mole) && !mole.isHasBeenCollided()) {
            mole.setHasBeenCollided(true);
            mole.move();
            points++;
        }
        else {
            mole.setHasBeenCollided(false);
        }

        this.window.repaint();
    }
}
