package com.pacman.Game;

import com.pacman.entity.Entity;

public class CollisionDetecter {

    GamePanel gp;

    public CollisionDetecter(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int entityLeftWX = entity.entityX + entity.hitBox.x;
        int entityRightWX = entity.entityX + entity.hitBox.x + entity.hitBox.width;
        int entityTopWY = entity.entityY + entity.hitBox.y;
        int entityBottomWY = entity.entityY + entity.hitBox.y + entity.hitBox.height;

        int entityLeftCol = entityLeftWX/gp.tileSize;
        int entityRightCol = entityRightWX/gp.tileSize;
        int entityTopRow = entityTopWY/gp.tileSize;
        int entityBottomRow = entityBottomWY/gp.tileSize;

        int tileNum1, tileNum2;

        switch(entity.direction){
            case "up":
                entityTopRow = (entityTopWY - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
                if(gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
            break;

            case "down":
                entityBottomRow = (entityBottomWY + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
            break;

            case "left":
                entityLeftCol = (entityLeftWX - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                if(gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
            break;

            case "right":
                entityRightCol = (entityRightWX + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileManager.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileManager.tile[tileNum1].collision == true || gp.tileManager.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
            break;
        }
    }
}
