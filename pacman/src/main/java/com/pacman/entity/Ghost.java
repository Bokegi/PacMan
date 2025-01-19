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
    private final String color;

    GamePanel gp;
    private final Random random;

    private long lastUpdate;
    private boolean isRemoved = false;

    private final int index;

    public Ghost(GamePanel gp, int startX, int startY, String color, int index) {
        this.gp = gp;
        this.entityX = startX;
        this.entityY = startY;
        this.color = color;
        this.index = index;
        this.random = new Random();

        setDefaultValue();
        getGhostImg();
        setCurrentImage();
        lastUpdate = System.currentTimeMillis();
    }

    private void setDefaultValue() {
        direction = "down";
        speed = gp.tileSize;
        hitBox = new Rectangle(0, 0, 20, 20);
    }

    private void getGhostImg() {
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
            case "blue" -> currentImage = blueGhost;
            case "orange" -> currentImage = orangeGhost;
            case "pink" -> currentImage = pinkGhost;
            case "red" -> currentImage = redGhost;
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
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastUpdate >= 300) {
            collisionOn = false;
            gp.cDetect.checkTile(this);
    
            if(collisionOn == false){
                switch (direction) {
                    case "up" -> entityY -= speed;
                    case "down" -> entityY += speed;
                    case "left" -> entityX -= speed;
                    case "right" -> entityX += speed;
                }
            } else {
                changeDirection();
            }
            lastUpdate = currentTime; 
        }
    }
    
    public void changeDirection() {
        switch (color) {
            case "blue" -> changeDirectionBlue();
            case "orange" -> changeDirectionOrange();
            case "pink" -> changeDirectionPink();
            case "red" -> changeDirectionRed();
        }
    }

    private void changeDirectionBlue() {
        // Direzione casuale
        String[] directions = {"up", "down", "left", "right"};
        direction = directions[random.nextInt(directions.length)];
    }

    private void changeDirectionOrange() {
        // Cambia in ordine ciclico
        switch (direction) {
            case "up" -> direction = "right";
            case "right" -> direction = "down";
            case "down" -> direction = "left";
            case "left" -> direction = "up";
        }
    }

    private void changeDirectionPink() {
        // Direzione inversa
        switch (direction) {
            case "up" -> direction = "down";
            case "down" -> direction = "up";
            case "left" -> direction = "right";
            case "right" -> direction = "left";
        }
    }

    private void changeDirectionRed() {
        // Direzione casuale, ma diversa dalla corrente
        String[] directions = {"up", "down", "left", "right"};
        String newDirection;
        do {
            newDirection = directions[random.nextInt(directions.length)];
        } while (newDirection.equals(direction));
        direction = newDirection;
    }

    public void resetPosition(){
        switch (index) {
            case 0 -> {
                entityX = gp.tileManager.blueGhostX;
                entityY = gp.tileManager.blueGhostY;
            }
            case 1 -> {
                entityX = gp.tileManager.pinkGhostX;
                entityY = gp.tileManager.pinkGhostY;
            }
            case 2 -> {
                entityX = gp.tileManager.orangeGhostX;
                entityY = gp.tileManager.orangeGhostY;
            }
            case 3 -> {
                entityX = gp.tileManager.redGhostX;
                entityY = gp.tileManager.redGhostY;
            }
        }
        setDefaultValue();
    }

    public void removeGhost() {
        isRemoved = true;
        hitBox= new Rectangle(0, 0, 0, 0);
    }

    public boolean isRemoved() {
        return isRemoved;
    }
}