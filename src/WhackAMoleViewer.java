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

    private final int PANEL_MARGIN = 20;


    public WhackAMoleViewer(WhackAMole game) {
        this.game = game;
        setImages();
        setupWindow();
    }

    public void setImages() {
        this.loadingGame = new ImageIcon("Resources/back.png").getImage();
        this.background = new ImageIcon("Resources/back.png").getImage();
        this.gameOver = new ImageIcon("Resources/over.png").getImage();
    }

    public void setupWindow() {
        // Make sure window exits when you close it, has the title "Card Game", has a set size, and can be seen
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Whack-A-Mole");
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
        paintCorrectState(state, g);
    }

    public void paintCorrectState(int state, Graphics g) {
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

    public void drawPanel(Graphics g, Boolean drawTimer, Boolean hasStarted, int x, int y) {
        // Draw information panel
        g.setColor(new Color(255, 165, 0, 180)); // Orange with transparency
        g.fillRect(x, y, x * 5, y * 2); // Information panel background

        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 36));

        if (drawTimer && hasStarted) {
            g.drawString("Timer: " + game.getCounter() / 10, x + PANEL_MARGIN, y + (2 * PANEL_MARGIN));
            g.drawString("Points: " + game.getPoints(), x + PANEL_MARGIN, y + (4 * PANEL_MARGIN));
        }
        else if (drawTimer) {
            g.drawString("Start In: " + ((game.getCounter() / 10) - 15), x + PANEL_MARGIN, y + (3 * PANEL_MARGIN));
        }
        else {
            g.drawString("Points: " + game.getPoints(), x + PANEL_MARGIN, y + (3 * PANEL_MARGIN));
        }
    }

    public void paintInstructions(Graphics g) {
        g.drawImage(this.loadingGame, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
        drawPanel(g, true, false, 40, 50);
    }

    public void paintMain(Graphics g) {
        g.drawImage(this.background, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
        for (Hole h : game.getHoles()) {
            h.draw(g);
        }
        game.getMole().draw(g);
        game.getHammer().draw(g);

        drawPanel(g, true, true, 40, 50);
    }

    public void paintGameOver(Graphics g) {
        g.drawImage(this.gameOver, 0, -150, WINDOW_WIDTH, WINDOW_HEIGHT + 300, this);

        drawPanel(g, false, true, 40, 50);
    }
}
