package com.pacman.Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.pacman.entity.Ghost;
import com.pacman.tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

    private final int normalTile = 16;
    private final int scale = 2;
    public int screenCol = 19;
    public int screenRow = 21;

    public int tileSize = normalTile * scale;
    public int screenWidth = screenCol * tileSize;
    public int screenHeight = screenRow * tileSize;

    public boolean gameStarted = false;
    public boolean paused = false;

    int fps = 60;
    public int score = 0;

    public KeyHandler keyH = new KeyHandler();
    public TileManager tileManager;
    Thread gameThread;
    public CollisionDetecter cDetect;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        
        this.addKeyListener(keyH);
        this.setFocusable(true);
        tileManager = new TileManager(this);
        cDetect = new CollisionDetecter(this);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    private void update() {
        if (keyH.spacePressed && !gameStarted) {
            gameStarted = true;
            paused = false; // Assicura che il gioco non sia in pausa all'inizio
            keyH.spacePressed = false; // Resetta il tasto SPAZIO
        }
        if (keyH.spacePressed && gameStarted && !paused) {
            paused = true;
            keyH.spacePressed = false;
        } else if (keyH.spacePressed && gameStarted && paused) {
            paused = false;
            keyH.spacePressed = false;
        }
        if (gameStarted && !paused) {
            tileManager.pacMan.update();
            for (Ghost ghost : tileManager.ghosts) {
                ghost.update();
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);
        tileManager.pacMan.draw(g2);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 24)); // Imposta il font in grassetto
        String scoreText = "Score: " + score;
        String hpText = "HP: " + tileManager.pacMan.hP;
        g2.drawString(scoreText, 0, 25); // Posiziona lo score in alto a sinistra
        g2.drawString(hpText, 530, 25); // Posiziona gli HP a destra dello score

        // Controlla se il gioco è in pausa e se è stato avviato per disegnare la scritta
        if (gameStarted && paused) {
            String pausedText = "GAME PAUSED";
            g2.setFont(new Font("Arial", Font.BOLD, 40)); // Imposta il font
            int stringWidth = g2.getFontMetrics().stringWidth(pausedText);
            int stringHeight = g2.getFontMetrics().getHeight();
            int x = (screenWidth - stringWidth) / 2;
            int y = (screenHeight - stringHeight) / 2 + stringHeight;
            g2.drawString(pausedText, x, y);
        }
        g2.dispose();
    }
}
