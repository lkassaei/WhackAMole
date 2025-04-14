import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class WhackAMole implements ActionListener, MouseListener, KeyListener {
    private Mole mole;
    private Hammer hammer;
    private WhackAMoleViewer window;
    private static final int SLEEP_TIME = 110;

    public WhackAMole() {
        window = new WhackAMoleViewer(this);
        // Create Mole
        window.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
}
