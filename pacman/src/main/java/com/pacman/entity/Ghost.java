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

    private final String blueGhostPath = "pac-man/res/Ghost/blueGhost.png";
    private final String orangeGhostPath = "pac-man/res/Ghost/orangeGhost.png";
    private final String pinkGhostPath = "pac-man/res/Ghost/pinkGhost.png";
    private final String redGhostPath = "pac-man/res/Ghost/redGhost.png";

    private BufferedImage blueGhost, orangeGhost, pinkGhost, redGhost;
    private BufferedImage currentImage;
    private String color;

    GamePanel gp;
    private Random random;

    public Ghost(GamePanel gp, int startX, int startY, String color) {
        this.gp = gp;
        this.entityX = startX;
        this.entityY = startY;
        this.color = color;
        this.random = new Random();

        hitBox = new Rectangle(30, 30, 32, 32);

        setDefaultValue();
        getGhostImg();
        setCurrentImage();
    }

    private void setDefaultValue() {
        speed = 4;
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
        int direction = random.nextInt(4);

        switch (direction) {
            case 0: // Move up
                entityY -= speed;
                break;
            case 1: // Move down
                entityY += speed;
                break;
            case 2: // Move left
                entityX -= speed;
                break;
            case 3: // Move right
                entityX += speed;
                break;
        }
    }
}
