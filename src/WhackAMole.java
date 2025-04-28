import javax.swing.*;
import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class WhackAMole implements ActionListener, MouseMotionListener {
    private Mole mole;
    private Hammer hammer;
    private ArrayList<Hole> holes;
    private WhackAMoleViewer window;

    private int points;
    private int counter;

    private int state;
    public static final int INSTRUCTION_STATE = 0;
    public static final int MAIN_STATE = 1;
    public static final int GAME_OVER_STATE = 2;

    private static final int SLEEP_TIME = 110;

    public WhackAMole() {
        window = new WhackAMoleViewer(this);

        this.state = INSTRUCTION_STATE;

        this.holes = new ArrayList<Hole>();
        fillHoles();
        this.hammer = new Hammer(window);
        this.mole = new Mole(window, holes);

        this.window.addMouseMotionListener(this);

        counter = 200;
    }

    public void start(WhackAMole game) {
        Timer clock = new Timer(SLEEP_TIME, game);
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
        counter--;
        if (counter > 150) {
            this.state = INSTRUCTION_STATE;
        }
        else if (counter > 0) {
            this.state = MAIN_STATE;
        }
        else if (counter == 0){
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
        if (this.state != GAME_OVER_STATE) {
            hammer.setX(e.getX());
            hammer.setY(e.getY());
            if (hammer.hasCollided(mole)) {
                mole.move();
                points++;
            }
            this.window.repaint();
        }
    }

    public static void main(String[] args) {
        WhackAMole game = new WhackAMole();
        game.start(game);
    }
}
