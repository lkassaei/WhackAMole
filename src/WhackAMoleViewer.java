import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class WhackAMoleViewer extends JFrame {
    private WhackAMole game;
    private final int WINDOW_WIDTH = 1000;
    private final int WINDOW_HEIGHT = 800;
    private Image loadingGame;
    private Image background;
    private Image gameOver;


    public WhackAMoleViewer(WhackAMole game) {
        this.game = game;
        // Find and set image
        this.loadingGame = new ImageIcon("Resources/back.png").getImage();
        this.background = new ImageIcon("Resources/back.png").getImage();
        this.gameOver = new ImageIcon("Resources/back.png").getImage();
        setupWindow();
    }

    public void setupWindow() {
        // Make sure window exits when you close it, has the title "Card Game", has a set size, and can be seen
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Card Game");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        // Calls paint method
        this.setVisible(true);
        createBufferStrategy(2);
    }

    public void paint(Graphics g) {
        BufferStrategy bf = this.getBufferStrategy();
        if (bf == null)
            return;
        Graphics g2 = null;
        try {
            g2 = bf.getDrawGraphics();
            myPaint(g2);
        }
        finally {
            g2.dispose();
        }
        bf.show();
        Toolkit.getDefaultToolkit().sync();
    }

    public void myPaint(Graphics g) {
        // Find what state the game is in
        int state = game.getState();
        // Based on the state, draw the right things
        if (state == WhackAMole.INSTRUCTION_STATE) {
            paintInstructions(g);
        }
        else if (state == WhackAMole.MAIN_STATE) {
            paintMain(g);
        }
        else if (state == WhackAMole.GAME_OVER_STATE) {
            paintGameOver(g);
        }
    }

    public void paintInstructions(Graphics g) {
        g.drawImage(this.loadingGame, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
        for (Hole h : game.getHoles()) {
            h.draw(g);
        }
        game.getMole().draw(g);
    }

    public void paintMain(Graphics g) {
        g.drawImage(this.background, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
    }

    public void paintGameOver(Graphics g) {
        g.drawImage(this.gameOver, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
    }
}
