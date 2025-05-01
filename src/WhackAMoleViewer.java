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
    private final int PANEL_X = 40;
    private final int PANEL_Y = 50;
    private final Font FONT = new Font("SansSerif", Font.BOLD, 36);
    private final Color PANEL_COLOR = new Color(255, 165, 0, 180); // Orange with transparency
    private final Color TEXT_COLOR = Color.WHITE;


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
        switch (state) {
            case WhackAMole.INSTRUCTION_STATE:
                paintInstructions(g);
                break;
            case WhackAMole.MAIN_STATE:
                paintMain(g);
                break;
            case WhackAMole.GAME_OVER_STATE:
                paintGameOver(g);
                break;
            default:
                break;
        }
    }

    public void drawPanel(Graphics g, Boolean drawTimer, Boolean hasStarted) {
        // Draw information panel
         // Orange with transparency

        g.setFont(FONT);
        int yOffset = PANEL_Y + (2 * PANEL_MARGIN);

        if (drawTimer && hasStarted) {
            g.setColor(PANEL_COLOR);
            g.fillRoundRect(PANEL_X, PANEL_Y, PANEL_X * 6, PANEL_Y * 2, 20, 20); // Information panel background
            g.setColor(TEXT_COLOR);
            g.drawString("Timer: " + game.getTimeRemainingSeconds(), PANEL_X + PANEL_MARGIN, yOffset);
            g.drawString("Points: " + game.getPoints(), PANEL_X + PANEL_MARGIN, yOffset + (2 * PANEL_MARGIN));
        }
        else if (drawTimer) {
            g.setColor(TEXT_COLOR);
            g.drawString("Start In: " + (game.getTimeRemainingSeconds() - WhackAMole.INSTRUCTION_START_SECOND), (PANEL_X * 5) + PANEL_MARGIN, yOffset + PANEL_Y * 3);
        }
        else {
            g.setColor(PANEL_COLOR);
            g.fillRoundRect(PANEL_X, PANEL_Y, PANEL_X * 6, PANEL_Y * 2, 20, 20); // Information panel background
            g.setColor(TEXT_COLOR);
            g.drawString("Points: " + game.getPoints(), PANEL_X + PANEL_MARGIN, yOffset + PANEL_MARGIN);
        }
    }

    public void drawInstructions(Graphics g) {
        g.setColor(PANEL_COLOR);
        g.fillRoundRect(150, PANEL_Y * 3, WINDOW_WIDTH - 300, WINDOW_HEIGHT - 200, 20, 20);
        g.setColor(TEXT_COLOR);
        g.setFont(FONT);
        g.drawString("How to Play:", (PANEL_X * 5) + PANEL_MARGIN, 350);
        g.fillRect(150, 250, WINDOW_WIDTH - 300, 10);
        g.setFont(FONT);
        String line1 = "Whack (click) moles!";
        String line2 = "Whack = points.";
        String line3 = "You have 15 secs";
        int lineSpacing = 80;
        int startY = 450;
        int startX = PANEL_X * 5 + PANEL_MARGIN;
        g.drawString(line1, startX, startY);
        g.drawString(line2, startX, startY + lineSpacing);
        g.drawString(line3, startX, startY + 2 * lineSpacing);
    }

    public void paintInstructions(Graphics g) {
        g.drawImage(this.loadingGame, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
        drawInstructions(g);
        drawPanel(g, true, false);
    }

    public void drawObjects(Graphics g) {
        for (Hole h : game.getHoles()) {
            h.draw(g);
        }
        game.getMole().draw(g);
        game.getHammer().draw(g);
    }

    public void paintMain(Graphics g) {
        g.drawImage(this.background, 0, 0, WINDOW_WIDTH, WINDOW_HEIGHT, this);
        drawObjects(g);
        drawPanel(g, true, true);
    }

    public void paintGameOver(Graphics g) {
        g.drawImage(this.gameOver, 0, -150, WINDOW_WIDTH, WINDOW_HEIGHT + 300, this);
        drawPanel(g, false, true);
    }
}
