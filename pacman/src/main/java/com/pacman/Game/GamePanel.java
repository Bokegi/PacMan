package com.pacman.Game;

import java.awt.Color;
import java.awt.Dimension;
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

    int fps = 60;
    public int score = 0;

    public KeyHandler keyH = new KeyHandler();
    public TileManager tileManager = new TileManager(this);
    Thread gameThread;
    public CollisionDetecter cDetect = new CollisionDetecter(this);

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        this.addKeyListener(keyH);
        this.setFocusable(true);
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
        tileManager.pacMan.update();
        for(Ghost ghost : tileManager.ghosts){
            ghost.update();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);
        tileManager.pacMan.draw(g2);

        g2.setColor(Color.WHITE);
        g2.drawString("Score: " + score, 10, 20);
        g2.dispose();
    }

}
