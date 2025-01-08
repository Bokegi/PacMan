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
    public KeyHandler keyH;

    public PacMan(GamePanel gp, KeyHandler keyH, int startX, int startY){      
        this.gp = gp;
        this.keyH = keyH;

        this.entityX = startX;
        this.entityY = startY;

        hitBox = new Rectangle(12, 10, gp.tileSize, gp.tileSize);

        setDefaultValue();
        getPlayerImg();
    }
private void setDefaultValue() {
        hP = 3;
        speed = 2;
        direction = "right";
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
    

    public void draw(Graphics2D g2){
        BufferedImage img = null;

        switch(direction){
            case "up":
                if(spriteNum == 1){
                    img = pacManUp;
                }else if(spriteNum == 2){
                    img = pacManClose;
                }
            break;
            
            case "down":
                if(spriteNum == 1){
                    img = pacManDown;
                }else if(spriteNum == 2){
                    img = pacManClose;
                }
            break;
            
            case "left":
                if(spriteNum == 1){
                    img = pacManLeft;
                }else if(spriteNum == 2){
                    img = pacManClose;
                }
            break;
            
            case "right":
                if(spriteNum == 1){
                    img = pacManRight;
                }else if(spriteNum == 2){
                    img = pacManClose;
                }   
            break;
        }
        if (img != null) { 
            g2.drawImage(img, entityX, entityY, gp.tileSize, gp.tileSize, null); 
        } else { 
            System.out.println("PacMan image is null"); 
        }
    }

    public void update() {
        collisionOn = false;

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
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
    
            // Verifica della collisione
            //gp.cDetect.checkTile(this);
    
            // Se non c'è collisione, muovi il giocatore
            if (collisionOn == false) {
                switch (direction) {
                    case "up":
                        if (entityY - speed >= 0) {
                            entityY -= speed;
                        }
                        break;
                    case "down":
                        if (entityY + speed < gp.screenHeight) {
                            entityY += speed;
                        }
                        break;
                    case "left":
                        if (entityX - speed >= 0) {
                            entityX -= speed;
                        }
                        break;
                    case "right":
                        if (entityX + speed < gp.screenWidth) {
                            entityX += speed;
                        }
                        break;
                }
            }

            spriteCounter++;
            if(spriteCounter > 12){
                if(spriteNum == 1){
                    spriteNum = 2;
                }else if(spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }
}
