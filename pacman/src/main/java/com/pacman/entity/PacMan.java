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

    private final GamePanel gp;
    private final KeyHandler keyH;

    private boolean isMoving;
    private boolean isDead;
    private int targetX, targetY;

    private long lastMoveTime;
    
    public PacMan(GamePanel gp, KeyHandler keyH, int startX, int startY) {
        this.gp = gp;
        this.keyH = keyH;

        this.entityX = startX;
        this.entityY = startY;

        /* this.targetX = startX;
        this.targetY = startY; */

        hitBox = new Rectangle(0, 0, 20, 20);

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

    private void getPlayerImg() {
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

    public void update(){
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastMoveTime >= 100) {
            if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true){
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

                collisionOn = false;
                gp.cDetect.checkTile(this);

                if(collisionOn == false){
                    switch (direction) {
                        case "up" -> entityY -= speed;
                        case "down" -> entityY += speed;
                        case "left" -> entityX -= speed;
                        case "right" -> entityX += speed;
                    }
                    checkFoodCollision();
                }

                spriteCounter++;
                if (spriteCounter > 2) {
                    spriteNum = (spriteNum == 1) ? 2 : 1;
                    spriteCounter = 0;
                }
            }
            lastMoveTime = currentTime;
        }
    }
}
