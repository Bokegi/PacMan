package com.pacman.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.pacman.Game.GamePanel;
import com.pacman.Game.KeyHandler;

public class PacMan extends Entity{

    private final String pacManUpPath = "pacman/res/PacMan/pacmanUp.png";
    private final String pacManDownPath = "pacman/res/PacMan/pacmanDown.png";
    private final String pacManLeftPath = "pacman/res/PacMan/pacmanLeft.png";
    private final String pacManRightPath = "pacman/res/PacMan/pacmanRight.png";
    private final String pacManClosePath = "pacman/res/PacMan/pacmanClose.png";

    private BufferedImage pacManDown, pacManUp, pacManLeft, pacManRight, pacManClose;

    private int hP;

    private GamePanel gp;
    private KeyHandler keyH;

    private boolean isMoving;
    private boolean isDead;
    private int targetX, targetY;

    private long lastMoveTime;
    
        public PacMan(GamePanel gp, KeyHandler keyH, int startX, int startY) {
            this.gp = gp;
            this.keyH = keyH;
    
            this.entityX = startX;
            this.entityY = startY;
            this.targetX = startX;
            this.targetY = startY;
    
            hitBox = new Rectangle(0, 0, 32, 32);
    
            setDefaultValue();
            getPlayerImg();
            lastMoveTime = System.currentTimeMillis();
        }
    
        private void setDefaultValue() {
            hP = 3;
            speed = gp.tileSize;  // Imposta la velocità pari alla dimensione di una casella
            direction = "right";
            isMoving = false;
        }
    
        public void getPlayerImg() {
            try {
                pacManUp = ImageIO.read(new File(pacManUpPath));
                pacManDown = ImageIO.read(new File(pacManDownPath));
                pacManLeft = ImageIO.read(new File(pacManLeftPath));
                pacManRight = ImageIO.read(new File(pacManRightPath));
                pacManClose = ImageIO.read(new File(pacManClosePath));
            } catch (IOException ex) {
                System.out.println("Cannot load PacMan image: " + ex.getMessage());
            }
        }
    
        public void draw(Graphics2D g2) {
            BufferedImage img = null;
    
            switch (direction) {
                case "up":
                    img = (spriteNum == 1) ? pacManUp : pacManClose;
                    break;
                case "down":
                    img = (spriteNum == 1) ? pacManDown : pacManClose;
                    break;
                case "left":
                    img = (spriteNum == 1) ? pacManLeft : pacManClose;
                    break;
                case "right":
                    img = (spriteNum == 1) ? pacManRight : pacManClose;
                    break;
            }
            g2.drawImage(img, entityX, entityY, gp.tileSize, gp.tileSize, null);
        }
    
        public void update() {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastMoveTime >= 700) {  // Controlla se è passato un secondo
                move();
                lastMoveTime = currentTime;  // Aggiorna l'ora dell'ultimo movimento
            }
    
            // Gestisci l'input dell'utente per cambiare direzione
            if (keyH.upPressed) {
                direction = "up";
            }
            if (keyH.downPressed) {
                direction = "down";
            }
            if (keyH.leftPressed) {
                direction = "left";
            }
            if (keyH.rightPressed) {
                direction = "right";
            }
    
            checkFoodCollision();
        }
    
        private void move() {
            // Calcola le nuove coordinate target in base alla direzione
            switch (direction) {
                case "up":
                    targetY = entityY - speed;
                    break;
                case "down":
                    targetY = entityY + speed;
                    break;
                case "left":
                    targetX = entityX - speed;
                    break;
                case "right":
                    targetX = entityX + speed;
                    break;
            }
            // Verifica collisione con le caselle
            collisionOn = false;
            gp.cDetect.checkTile(this);
    
            // Se non c'è collisione, aggiorna la posizione di Pac-Man
            if (!collisionOn) {
                entityX = targetX;
                entityY = targetY;
            } else {
                // Se c'è collisione, rimani nella posizione attuale
                targetX = entityX;
                targetY = entityY;
            }
        }
    
        private void checkFoodCollision() {
            int col = (entityX + hitBox.x) / gp.tileSize;
            int row = (entityY + hitBox.y) / gp.tileSize;
            if (gp.tileManager.mapTileNum[col][row] == 1) {  // 1 = cibo
                gp.tileManager.mapTileNum[col][row] = 8;  // Rimuove il cibo
                gp.score += 10;  // Incrementa il punteggio
                System.out.println("Score: " + gp.score);  // Stampa il punteggio per debug
            }else if (gp.tileManager.mapTileNum[col][row] == 2) {  // 2 = powerFood
                gp.tileManager.mapTileNum[col][row] = 8;  // Rimuove il cibo
                gp.score += 50;  // Incrementa il punteggio
                System.out.println("Score: " + gp.score);  // Stampa il punteggio per debug
            }
    
            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        }
    }
    