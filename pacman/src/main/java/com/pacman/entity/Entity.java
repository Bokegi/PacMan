package com.pacman.entity;

import java.awt.Rectangle;

public class Entity {

    public int entityX, entityY;
    public int speed;
    
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    //public Rectangle hitBox;
    public Rectangle hitBox = new Rectangle(0, 0, 30, 30);
    public Boolean collisionOn = false;
}
