package com.pacman.Game;

import com.pacman.entity.Ghost;
import com.pacman.entity.PacMan;
import com.pacman.tile.TileManager;

public class CollisionThread {
    
    GamePanel gp;
    TileManager tileManager;

    int fps = 60;

    public CollisionThread(GamePanel gp){
        this.gp = gp;
        collision();
    }

    public void collision() {

        double drawInterval = 1000000000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (true) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                
            }
        }
    }


    // Check collision for Pac-Man
    public boolean checkCollision(PacMan pacMan) {
        int pacManLeftWorldX = pacMan.entityX + pacMan.hitBox.x;
        int pacManRightWorldX = pacMan.entityX + pacMan.hitBox.x + pacMan.hitBox.width;
        int pacManTopWorldY = pacMan.entityY + pacMan.hitBox.y;
        int pacManBottomWorldY = pacMan.entityY + pacMan.hitBox.y + pacMan.hitBox.height;

        int pacManLeftCol = pacManLeftWorldX / gp.tileSize;
        int pacManRightCol = pacManRightWorldX / gp.tileSize;
        int pacManTopRow = pacManTopWorldY / gp.tileSize;
        int pacManBottomRow = pacManBottomWorldY / gp.tileSize;

        // Check if Pac-Man is within the map boundaries
        if (pacManLeftCol < 0 || pacManRightCol >= gp.screenCol || pacManTopRow < 0 || pacManBottomRow >= gp.screenRow) {
            return true; // Collision if out of bounds
        }

        int tileNum1, tileNum2;

        switch (pacMan.direction) {
            case "up":
                pacManTopRow = (pacManTopWorldY - pacMan.speed) / gp.tileSize;
                if (pacManTopRow < 0) 
                    return true; // Check upper bounds

                /* tileNum1 = tileManager.mapTileNum[pacManLeftCol][pacManTopRow];
                tileNum2 = tileManager.mapTileNum[pacManRightCol][pacManTopRow]; */

                if (tileManager.mapTileNum[pacManLeftCol][pacManTopRow] == 0 || tileManager.mapTileNum[pacManRightCol][pacManTopRow] == 0) {
                    return true; // Collision with wall
                }
                break;

            case "down":
                pacManBottomRow = (pacManBottomWorldY + pacMan.speed) / gp.tileSize;
                if (pacManBottomRow >= gp.screenRow) 
                    return true; // Check lower bounds

                /*     tileNum1 = tileManager.mapTileNum[pacManLeftCol][pacManBottomRow];
                    tileNum2 = tileManager.mapTileNum[pacManRightCol][pacManBottomRow]; */

                if (tileManager.mapTileNum[pacManLeftCol][pacManBottomRow] == 0 || tileManager.mapTileNum[pacManRightCol][pacManBottomRow] == 0) {
                    return true; // Collision with wall
                }
                break;

            case "left":
                pacManLeftCol = (pacManLeftWorldX - pacMan.speed) / gp.tileSize;
                if (pacManLeftCol < 0) 
                    return true; // Check left bounds

                    /* tileNum1 = tileManager.mapTileNum[pacManLeftCol][pacManTopRow];
                    tileNum2 = tileManager.mapTileNum[pacManLeftCol][pacManBottomRow]; */

                if (tileManager.mapTileNum[pacManLeftCol][pacManTopRow] == 0 || tileManager.mapTileNum[pacManLeftCol][pacManBottomRow] == 0) {
                    return true; // Collision with wall
                }
                break;

            case "right":
                pacManRightCol = (pacManRightWorldX + pacMan.speed) / gp.tileSize;
                if (pacManRightCol >= gp.screenCol) 
                    return true; // Check right bounds

                    /* tileNum1 = tileManager.mapTileNum[pacManRightCol][pacManTopRow];
                    tileNum2 = tileManager.mapTileNum[pacManRightCol][pacManBottomRow]; */

                if (tileManager.mapTileNum[pacManRightCol][pacManTopRow] == 0 || tileManager.mapTileNum[pacManRightCol][pacManBottomRow] == 0) {
                    return true; // Collision with wall
                }
                break;
        }
        return false;
    }

    // Check collision for Ghosts
    public boolean checkCollision(Ghost ghost) {
        int ghostLeftWorldX = ghost.entityX + ghost.hitBox.x;
        int ghostRightWorldX = ghost.entityX + ghost.hitBox.x + ghost.hitBox.width;
        int ghostTopWorldY = ghost.entityY + ghost.hitBox.y;
        int ghostBottomWorldY = ghost .entityY + ghost.hitBox.y + ghost.hitBox.height;

        int ghostLeftCol = ghostLeftWorldX / gp.tileSize;
        int ghostRightCol = ghostRightWorldX / gp.tileSize;
        int ghostTopRow = ghostTopWorldY / gp.tileSize;
        int ghostBottomRow = ghostBottomWorldY / gp.tileSize;

        // Check if Ghost is within the map boundaries
        if (ghostLeftCol < 0 || ghostRightCol >= gp.screenCol || ghostTopRow < 0 || ghostBottomRow >= gp.screenRow) {
            return true; // Collision if out of bounds
        }

        int tileNum1, tileNum2;

        switch (ghost.direction) {
            case "up":
                ghostTopRow = (ghostTopWorldY - ghost.speed) / gp.tileSize;
                if (ghostTopRow < 0) return true; // Check upper bounds
                tileNum1 = tileManager.mapTileNum[ghostLeftCol][ghostTopRow];
                tileNum2 = tileManager.mapTileNum[ghostRightCol][ghostTopRow];
                if (tileNum1 == 0 || tileNum2 == 0) {
                    return true; // Collision with wall
                }
                break;
            case "down":
                ghostBottomRow = (ghostBottomWorldY + ghost.speed) / gp.tileSize;
                if (ghostBottomRow >= gp.screenRow) return true; // Check lower bounds
                tileNum1 = tileManager.mapTileNum[ghostLeftCol][ghostBottomRow];
                tileNum2 = tileManager.mapTileNum[ghostRightCol][ghostBottomRow];
                if (tileNum1 == 0 || tileNum2 == 0) {
                    return true; // Collision with wall
                }
                break;
            case "left":
                ghostLeftCol = (ghostLeftWorldX - ghost.speed) / gp.tileSize;
                if (ghostLeftCol < 0) return true; // Check left bounds
                tileNum1 = tileManager.mapTileNum[ghostLeftCol][ghostTopRow];
                tileNum2 = tileManager.mapTileNum[ghostLeftCol][ghostBottomRow];
                if (tileNum1 == 0 || tileNum2 == 0) {
                    return true; // Collision with wall
                }
                break;
            case "right":
                ghostRightCol = (ghostRightWorldX + ghost.speed) / gp.tileSize;
                if (ghostRightCol >= gp.screenCol) return true; // Check right bounds
                tileNum1 = tileManager.mapTileNum[ghostRightCol][ghostTopRow];
                tileNum2 = tileManager.mapTileNum[ghostRightCol][ghostBottomRow];
                if (tileNum1 == 0 || tileNum2 == 0) {
                    return true; // Collision with wall
                }
                break;
        }
        return false;
    }
}
