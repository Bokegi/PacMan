package com.pacman.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.pacman.Game.GamePanel;

public class Ghost extends Entity{

    private final String blueGhostPath = "pacman/res/Ghost/blueGhost.png";
    private final String orangeGhostPath = "pacman/res/Ghost/orangeGhost.png";
    private final String pinkGhostPath = "pacman/res/Ghost/pinkGhost.png";
    private final String redGhostPath = "pacman/res/Ghost/redGhost.png";

    private BufferedImage blueGhost, orangeGhost, pinkGhost, redGhost;
    private BufferedImage currentImage;
    private String color;

    GamePanel gp;
    private Random random;

    private long lastUpdate;

    public Ghost(GamePanel gp, int startX, int startY, String color) {
        this.gp = gp;
        this.entityX = startX;
        this.entityY = startY;
        this.color = color;
        this.random = new Random();
        
        this.hitBox = super.hitBox;

        setDefaultValue();
        getGhostImg();
        setCurrentImage();
        lastUpdate = System.currentTimeMillis();
    }

    private void setDefaultValue() {
        direction = "down";
        speed = gp.tileSize;
    }

    public void getGhostImg() {
        try {
            blueGhost = ImageIO.read(new File(blueGhostPath));
            orangeGhost = ImageIO.read(new File(orangeGhostPath));
            pinkGhost = ImageIO.read(new File(pinkGhostPath));
            redGhost = ImageIO.read(new File(redGhostPath));
        } catch (IOException ex) {
            System.out.println("Cannot load ghost image: " + ex.getMessage());
        }
    }

    private void setCurrentImage() {
        switch (color) {
            case "blue":
                currentImage = blueGhost;
                break;
            case "orange":
                currentImage = orangeGhost;
                break;
            case "pink":
                currentImage = pinkGhost;
                break;
            case "red":
                currentImage = redGhost;
                break;
        }
    }

    public void draw(Graphics2D g2) {
        if (currentImage != null) {
            g2.drawImage(currentImage, entityX, entityY, gp.tileSize, gp.tileSize, null);
        } else {
            System.out.println("Ghost image is null for color: " + color);
        }
    }

    public void update() {
        // Aggiorna la posizione della hitbox in base alla posizione dell'entità
        this.hitBox.x = this.entityX;
        this.hitBox.y = this.entityY;

        // Nella classe Ghost (nel metodo update):
        Rectangle nextHitBox = new Rectangle(entityX, entityY, hitBox.width, hitBox.height);
        if (!gp.tileManager.checkCollision(nextHitBox)) {
            switch (direction) {
                case "up":
                    entityY -= speed;
                    break;
                case "down":
                    entityY += speed;
                    break;
                case "left":
                    entityX -= speed;
                    break;
                case "right":
                    entityX += speed;
                    break;
            }
        } else {
            changeDirection();
        }
    }

    public void changeDirection() {
        String[] directions = {"up", "down", "left", "right"};
        String newDirection;
        newDirection = directions[(int) (Math.random() * directions.length)];

        direction = newDirection;
    }
}