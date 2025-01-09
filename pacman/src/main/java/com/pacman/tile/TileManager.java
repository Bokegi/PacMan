package com.pacman.tile;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import com.pacman.Game.GamePanel;
import com.pacman.entity.Ghost;
import com.pacman.entity.PacMan;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][]; 

    private final String wallPath = "pacman/res/Object/wall.png";
    private final String powerFoodPath = "pacman/res/Object/powerFood.png";
    private final String foodPath = "pacman/res/Object/food.png";

    public PacMan pacMan;
    public Ghost[] ghosts;

    public Rectangle wallHitBox;  

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.screenCol][gp.screenRow];
        ghosts = new Ghost[4]; // Inizializza array per i fantasmi
        getTileImg();
        loadMap("pacman/res/Map/Map02.txt");

        wallHitBox = new Rectangle(0, 0, gp.tileSize, gp.tileSize); 
    }

    private void getTileImg() {
        try {
            tile[0] = new Tile(); // Wall
            tile[0].img = ImageIO.read(new File(wallPath));
            //tile[0].collision = true;
            
            tile[1] = new Tile(); // Empty space (no collision)
            tile[1].img = ImageIO.read(new File(foodPath));
            
            tile[2] = new Tile(); // PowerFood (no collision)
            tile[2].img = ImageIO.read(new File(powerFoodPath));
            
            tile[3] = new Tile(); 
            tile[3].img = null;
            
            tile[4] = new Tile(); 
            tile[4].img = null;

            tile[5] = new Tile(); 
            tile[5].img = null;

            tile[6] = new Tile(); 
            tile[6].img = null;

            tile[7] = new Tile(); 
            tile[7].img = null;

            tile[8] = new Tile(); 
            tile[8].img = null;
            tile[8].collision = false;

    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void loadMap(String mapPath1) {
        try {
            InputStream is = new FileInputStream(mapPath1);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;
    
            while (col < gp.screenCol && row < gp.screenRow) {
                String line = br.readLine();
                //System.out.println("Line: " + line); // Per debug
    
                while (col < gp.screenCol) { 
                    if (line == null || line.isEmpty() || line.startsWith("//")) { 
                        continue;
                    }
                    String[] numbArr = line.split(" ");
                    int num = Integer.parseInt(numbArr[col]);
                    mapTileNum[col][row] = num;

                    // Controlla se il numero rappresenta un'entità speciale e inizializzala
                    switch (num) {
                        case 3 -> pacMan = new PacMan(gp, gp.keyH, col * gp.tileSize, row * gp.tileSize);
                        case 4 -> ghosts[0] = new Ghost(gp, col * gp.tileSize, row * gp.tileSize, "blue");
                        case 5 -> ghosts[1] = new Ghost(gp, col * gp.tileSize, row * gp.tileSize, "pink");
                        case 6 -> ghosts[2] = new Ghost(gp, col * gp.tileSize, row * gp.tileSize, "orange");
                        case 7 -> ghosts[3] = new Ghost(gp, col * gp.tileSize, row * gp.tileSize, "red");
                    }
                    col++;
                }
    
                if (col == gp.screenCol) {
                    col = 0;
                    row++;
                } 
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }  
    
    public void draw(Graphics2D g2){
        int col = 0;
        int row = 0;
        int foodSize = gp.tileSize / 4; // Dimensione ridotta per food e powerfood
    
        while(col < gp.screenCol && row < gp.screenRow){
            int tileNum = mapTileNum[col][row];
    
            int screenX = col * gp.tileSize;
            int screenY = row * gp.tileSize;
    
            if (tileNum == 0 || tileNum == 3 || tileNum == 4 || tileNum == 5 || tileNum == 6 || tileNum == 7 || tileNum == 8) {
                g2.drawImage(tile[tileNum].img, screenX, screenY, gp.tileSize, gp.tileSize, null);
            } else if (tileNum == 1 || tileNum == 2) {
                int imgX = screenX + (gp.tileSize - foodSize) / 2;
                int imgY = screenY + (gp.tileSize - foodSize) / 2;
                g2.drawImage(tile[tileNum].img, imgX, imgY, foodSize, foodSize, null);
            }
    
            col++;
            if(col == gp.screenCol){
                col = 0;
                row++;
            }
        }
    
        // Disegna PacMan
        if (pacMan != null) {
            pacMan.draw(g2);
        }
        
        // Disegna i fantasmi se non sono nulli
        for (Ghost ghost : ghosts) {
            if (ghost != null) {
                ghost.draw(g2);
            }
        }
    }
}
