package com.pacman.Game;

import com.pacman.entity.Ghost;
import com.pacman.tile.TileManager;

public class CollisionDetecter {
    GamePanel gp;
    TileManager tileManager;

    public CollisionDetecter(GamePanel gp) {
        this.gp = gp;
        this.tileManager = gp.tileManager; // Associa tileManager dal GamePanel
    }

    public boolean checkCollision(Ghost ghost) {
        if (ghost.direction == null) {
            return false; // Evita la collisione se la direzione è null
        }

        int ghostLeftWorldX = ghost.entityX + ghost.hitBox.x;
        int ghostRightWorldX = ghost.entityX + ghost.hitBox.x + ghost.hitBox.width;
        int ghostTopWorldY = ghost.entityY + ghost.hitBox.y;
        int ghostBottomWorldY = ghost.entityY + ghost.hitBox.y + ghost.hitBox.height;

        int ghostLeftCol = ghostLeftWorldX / gp.tileSize;
        int ghostRightCol = ghostRightWorldX / gp.tileSize;
        int ghostTopRow = ghostTopWorldY / gp.tileSize;
        int ghostBottomRow = ghostBottomWorldY / gp.tileSize;

        // Verifica che il fantasma sia all'interno dei limiti della mappa
        if (ghostLeftCol < 0 || ghostRightCol >= gp.screenCol || ghostTopRow < 0 || ghostBottomRow >= gp.screenRow) {
            return true; // Se il fantasma è fuori dai limiti, considera una collisione
        }

        int tileNum1, tileNum2;

        switch (ghost.direction) {
            case "up":
                ghostTopRow = (ghostTopWorldY - ghost.speed) / gp.tileSize;
                if (ghostTopRow < 0) return true; // Controlla i limiti superiori
                tileNum1 = tileManager.mapTileNum[ghostLeftCol][ghostTopRow];
                tileNum2 = tileManager.mapTileNum[ghostRightCol][ghostTopRow];
                if (tileNum1 == 0 || tileNum2 == 0) {
                    return true;
                }
                break;
            case "down":
                ghostBottomRow = (ghostBottomWorldY + ghost.speed) / gp.tileSize;
                if (ghostBottomRow >= gp.screenRow) return true; // Controlla i limiti inferiori
                tileNum1 = tileManager.mapTileNum[ghostLeftCol][ghostBottomRow];
                tileNum2 = tileManager.mapTileNum[ghostRightCol][ghostBottomRow];
                if (tileNum1 == 0 || tileNum2 == 0) {
                    return true;
                }
                break;
            case "left":
                ghostLeftCol = (ghostLeftWorldX - ghost.speed) / gp.tileSize;
                if (ghostLeftCol < 0) return true; // Controlla i limiti a sinistra
                tileNum1 = tileManager.mapTileNum[ghostLeftCol][ghostTopRow];
                tileNum2 = tileManager.mapTileNum[ghostLeftCol][ghostBottomRow];
                if (tileNum1 == 0 || tileNum2 == 0) {
                    return true;
                }
                break;
            case "right":
                ghostRightCol = (ghostRightWorldX + ghost.speed) / gp.tileSize;
                if (ghostRightCol >= gp.screenCol) return true; // Controlla i limiti a destra
                tileNum1 = tileManager.mapTileNum[ghostRightCol][ghostTopRow];
                tileNum2 = tileManager.mapTileNum[ghostRightCol][ghostBottomRow];
                if (tileNum1 == 0 || tileNum2 == 0) {
                    return true;
                }
                break;
        }
        return false;
    }

    // Metodo per controllare collisioni in una direzione specifica
    public boolean checkCollisionWithDirection(Ghost ghost, String direction) {
        String originalDirection = ghost.direction;
        ghost.direction = direction;
        boolean collision = checkCollision(ghost);
        ghost.direction = originalDirection;
        return collision;
    }
}
