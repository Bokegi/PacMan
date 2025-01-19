package com.pacman.Game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
    public boolean levelComplete = false;
    public boolean gameOver = false;
    private boolean nameEntered = false;

    int fps = 60;
    public int score = 0;

    public KeyHandler keyH = new KeyHandler();
    public TileManager tileManager;
    Thread gameThread;
    public CollisionDetecter cDetect;
    Leaderboard leaderboard;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        
        this.addKeyListener(keyH);
        this.setFocusable(true);
        tileManager = new TileManager(this);
        cDetect = new CollisionDetecter(this);
        leaderboard = new Leaderboard();
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
                checkLeaderboard();
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
            score = 0;
        }
        if (keyH.spacePressed && gameStarted && !paused) {
            paused = true;
            keyH.spacePressed = false;
        } else if (keyH.spacePressed && gameStarted && paused) {
            paused = false;
            keyH.spacePressed = false;
        }
        if (levelComplete && (keyH.yPressed || keyH.nPressed)) {
            if (keyH.yPressed) {
                resetGame();
            } else if (keyH.nPressed) {
                returnToMainMenu();
            }
            keyH.yPressed = false;
            keyH.nPressed = false;
        }
        if (gameOver && (keyH.yPressed || keyH.nPressed)) {
            if (keyH.yPressed) {
                resetGame();
            } else if (keyH.nPressed) {
                returnToMainMenu();
            }
            keyH.yPressed = false;
            keyH.nPressed = false;
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

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        String scoreText = "SCORE: " + score;
        String hpText = "HP: " + tileManager.pacMan.hP;
        g2.drawString(scoreText, 0, 25);
        g2.drawString(hpText, screenWidth - 100, 25);

        if (gameStarted && paused && !levelComplete && !gameOver) {
            g2.setFont(new Font("Arial", Font.BOLD, 40));
            String pausedText = "GAME PAUSED";
            int stringWidth = g2.getFontMetrics().stringWidth(pausedText);
            int x = (screenWidth - stringWidth) / 2;
            int y = screenHeight / 2;
            g2.drawString(pausedText, x, y);
        }

        if (levelComplete) {
            g2.setFont(new Font("Arial", Font.BOLD, 40));
            String levelCompleteText = "LEVEL COMPLETE";
            int stringWidth = g2.getFontMetrics().stringWidth(levelCompleteText);
            int x = (screenWidth - stringWidth) / 2;
            int y = screenHeight / 2;
            g2.drawString(levelCompleteText, x, y);
            
            String playAgain = "Nuova Partita? Y, N";
            stringWidth = g2.getFontMetrics().stringWidth(playAgain);
            x = (screenWidth - stringWidth) / 2;
            y = screenHeight / 2 + 50;
            g2.drawString(playAgain, x, y);
        }

        if (gameOver) { 
            g2.setFont(new Font("Arial", Font.BOLD, 40)); 
            String gameOverText = "GAME OVER"; 
            int stringWidth = g2.getFontMetrics().stringWidth(gameOverText); 
            int x = (screenWidth - stringWidth) / 2; 
            int y = screenHeight / 2 - 40; 
            g2.drawString(gameOverText, x, y); 

            String playAgainText = "Nuova Partita? Y, N"; 
            stringWidth = g2.getFontMetrics().stringWidth(playAgainText); 
            x = (screenWidth - stringWidth) / 2; 
            y = screenHeight / 2 + 20; 
            g2.drawString(playAgainText, x, y);
        }

        g2.dispose();
    }


    public void resetGame() {
        levelComplete = false;
        score = 0;
        tileManager.resetLevel();
    }
    
    private void checkLeaderboard(){
        if(levelComplete || gameOver){
            if(nameEntered == false){
                String playerName = JOptionPane.showInputDialog("Immetti Nome: ");
                leaderboard.addScore(playerName, score);
                nameEntered = true;
            }
        }
    }

    private void returnToMainMenu() {
        // Ferma il thread del gioco
        gameThread = null;
    
        // Chiude e distrugge il frame del gioco
        JFrame parentFrame = (JFrame) this.getTopLevelAncestor();
        if (parentFrame != null) {
            parentFrame.dispose();
        }
    
        // Crea e mostra il nuovo menu principale
        MenuPage menuPage = new MenuPage();
        menuPage.setVisible(true);
    }
    

}
