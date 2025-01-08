package com.pacman.tile;

import java.awt.Graphics2D;
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

    public TileManager(GamePanel gamePanel) {
        this.gp = gamePanel;
        tile = new Tile[10];
        mapTileNum = new int[gp.screenCol][gp.screenRow];
        getTileImg();
        loadMap("pacman/res/Map/Map02.txt");
    }

    private void getTileImg() {
        try {
            tile[0] = new Tile(); // Wall
            tile[0].img = ImageIO.read(new File(wallPath));
            tile[0].collision = true;
            System.out.println("Wall loaded: " + (tile[0].img != null));
            
            tile[1] = new Tile(); // Empty space (no collision)
            tile[1].img = ImageIO.read(new File(foodPath));
            System.out.println("Food loaded: " + (tile[1].img != null));
            
            tile[2] = new Tile(); // PowerFood (no collision)
            tile[2].img = ImageIO.read(new File(powerFoodPath));
            System.out.println("PowerFood loaded: " + (tile[2].img != null));
            
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
                System.out.println("Line: " + line); // Per debug
    
                while (col < gp.screenCol) { 
                    if (line == null || line.isEmpty() || line.startsWith("//")) { 
                        continue;
                    }
                    String[] numbArr = line.split(" ");
                    int num = Integer.parseInt(numbArr[col]);
                    mapTileNum[col][row] = num;
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

        while(col < gp.screenCol && row < gp.screenRow){
            int tileNum = mapTileNum[col][row];

            int screenX = col * gp.tileSize;
            int screenY = row * gp.tileSize;

            g2.drawImage(tile[tileNum].img, screenX, screenY, gp.tileSize, gp.tileSize, null);

            switch (tileNum) {
                case 3 -> {
                    PacMan pacMan = new PacMan(gp, gp.keyH, col * gp.tileSize, row * gp.tileSize); 
                    pacMan.draw(g2);
                }
                case 4 ->{
                    Ghost ghost = new Ghost(gp, col * gp.tileSize, row * gp.tileSize, "blue");
                    ghost.draw(g2);
                }
                case 5 ->{
                    Ghost ghost = new Ghost(gp, col * gp.tileSize, row * gp.tileSize, "pink");
                    ghost.draw(g2);
                }
                case 6 ->{
                    Ghost ghost = new Ghost(gp, col * gp.tileSize, row * gp.tileSize, "orange");
                    ghost.draw(g2);
                }
                case 7 ->{
                    Ghost ghost = new Ghost(gp, col * gp.tileSize, row * gp.tileSize, "red");
                    ghost.draw(g2);
                }
                case 8 ->{
                    continue;
                }
                default -> {
                }
            }

            col++;
            if(col == gp.screenCol){
                col = 0;
                row++;
            }
        }
    }

    /* public void loadMap(String mapPath1) {
        try {
            
            InputStream is = new FileInputStream(mapPath1);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;

            while (col < gp.screenCol && row < gp.screenRow) {
                String line = br.readLine();

                while (col < gp.screenCol) { 
                    if (line.isEmpty() || line.startsWith("//")) { 
                        continue;
                    }
                    String numbArr[] = line.split(" ");

                    int num = Integer.parseInt(numbArr[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }

                if(col == gp.screenCol){
                    col = 0;
                    row++;
                } 
            }
            br.close();
        } catch (Exception e) {
        }
    }   */  

}
