/**
 * Frontend
 */

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class WhackAMoleViewer extends JFrame {
    // Backend data
    private final WhackAMole game;

    // Window properties
    private final int WINDOW_WIDTH = 1000;
    private final int WINDOW_HEIGHT = 800;

    // UI styling constants
    private final int PANEL_MARGIN = 20;
    private final int PANEL_X = 40;
    private final int PANEL_Y = 50;
    private final Font FONT = new Font("SansSerif", Font.BOLD, 36);
    private final Color PANEL_COLOR = new Color(255, 165, 0, 180); // Orange with transparency
    private final Color TEXT_COLOR = Color.WHITE;

    // Game state images
    private Image loadingGame;
    private Image background;
    private Image gameOver;

    // Set everything to initial values
    public WhackAMoleViewer(WhackAMole game) {
        this.game = game;
        loadImages();
        setupWindow();
    }

    // Load images used in all the game states
    public void loadImages() {
        this.loadingGame = new ImageIcon("Resources/back.png").getImage();
        this.background = new ImageIcon("Resources/back.png").getImage();
        this.gameOver = new ImageIcon("Resources/over.png").getImage();
    }

    // Set up all window properties
    public void setupWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Exit when window is closed
        setTitle("Whack-A-Mole");                // Set window title
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);    // Set fixed window size
        setVisible(true);                        // Make window visible
        createBufferStrategy(2);      // Enable double buffering for smoother graphics
    }

    // Creates double buffering for smoother graphics
    public void paint(Graphics g) {
        BufferStrategy bf = this.getBufferStrategy();
        if (bf == null)
            return;
        Graphics g2 = null;
        try {
            g2 = bf.getDrawGraphics();
            renderGame(g2);
        }
        finally {
            g2.dispose();
        }
        bf.show();
        Toolkit.getDefaultToolkit().sync();
    }

    // Choose the correct screen to display based on the game state
    public void renderGame(Graphics g) {
        // Find what state the game is in
        int state = game.getState();
        // Based on the state, draw the right things
        paintCorrectState(state, g);
    }

    public void paintCorrectState(int state, Graphics g) {
        switch (state) {
            // If the game is in instruction state, draw the instructions
            case WhackAMole.INSTRUCTION_STATE:
                drawState(g, loadingGame, true, false, 0);
                break;
            // If the game is in the main state, draw the main game
            case WhackAMole.MAIN_STATE:
                drawState(g, background, true, true, 0);
                drawGameObjects(g);
                break;
            // If the game is in the game over state, draw the end screen
            case WhackAMole.GAME_OVER_STATE:
                drawState(g, gameOver, false, true, 150);
                break;
            // Default case is to break
            default:
                break;
        }
    }

    public void drawState(Graphics g, Image image, boolean showTimer, boolean gameStarted, int shift) {
        g.drawImage(image, 0, shift * -1, WINDOW_WIDTH, WINDOW_HEIGHT  + (2 * shift), this);
        drawInfoPanel(g, showTimer, gameStarted);
    }

    private void drawInstructionText(Graphics g) {
        // Create local variables for the instruction text panel
        int instructionPanelMargin = 150;
        int instructionArcSize = 20;
        int instructionHeightMargin = 200;

        g.setColor(PANEL_COLOR); // Transparent orange
        // Draw the panel
        g.fillRoundRect(instructionPanelMargin, PANEL_Y * 3, WINDOW_WIDTH - (2 * instructionPanelMargin),
                WINDOW_HEIGHT - instructionHeightMargin, instructionArcSize, instructionArcSize);

        g.setColor(TEXT_COLOR); // White
        g.setFont(FONT);
        g.drawString("How to Play:", PANEL_X * 5 + PANEL_MARGIN, PANEL_Y * 7);

        // Divider line
        g.fillRect(instructionPanelMargin, PANEL_Y * 5, WINDOW_WIDTH - (2 * instructionPanelMargin), 10);

        // Create text lines
        String[] lines = {
                "Whack (click) moles!",
                "Whack = points.",
                "Moles move fast!",
                "You have 25 secs"
        };

        // Create variables for text spacing
        int lineSpacing = 80;
        int startY = instructionHeightMargin * 2 + PANEL_Y;
        int startX = PANEL_X * 5 + PANEL_MARGIN;
        // Draw lines with correct spacing
        for (int i = 0; i < lines.length; i++) {
            g.drawString(lines[i], startX, startY + (i * lineSpacing));
        }
    }

    // Draw the top left info panel with the timer and points
    private void drawInfoPanel(Graphics g, boolean showTimer, boolean gameStarted) {
        g.setFont(FONT); // Set font
        int yOffset = PANEL_Y + (2 * PANEL_MARGIN); // Position below the top

        if (showTimer && gameStarted) {
            // Draw timer and points during active gameplay
            drawPanelBackground(g);
            g.setColor(TEXT_COLOR);
            g.drawString("Timer: " + game.getTimeRemainingSeconds(), PANEL_X + PANEL_MARGIN, yOffset);
            g.drawString("Points: " + game.getPoints(), PANEL_X + PANEL_MARGIN, yOffset + (2 * PANEL_MARGIN));
        } else if (showTimer) {
            // Show countdown timer before game starts
            drawInstructionText(g);
            g.setColor(TEXT_COLOR);
            int countdown = game.getTimeRemainingSeconds() - WhackAMole.INSTRUCTION_END_SECOND;
            g.drawString("Start In: " + countdown, PANEL_X * 5 + PANEL_MARGIN, yOffset + PANEL_Y * 3);
        } else {
            // Game over state: show final points only
            drawPanelBackground(g);
            g.setColor(TEXT_COLOR);
            g.drawString("Points: " + game.getPoints(), PANEL_X + PANEL_MARGIN, yOffset + PANEL_MARGIN);
        }
    }

    // Draw the actual panel behind the timer and points
    private void drawPanelBackground(Graphics g) {
        g.setColor(PANEL_COLOR);
        g.fillRoundRect(PANEL_X, PANEL_Y, PANEL_X * 6, PANEL_Y * 2, 20, 20);
    }

    private void drawGameObjects(Graphics g) {
        // Draw each hole
        for (Hole h : game.getHoles()) {
            h.draw(g);
        }
        // Draw the mole and the hammer
        game.getMole().draw(g);
        game.getHammer().draw(g);
    }
}
