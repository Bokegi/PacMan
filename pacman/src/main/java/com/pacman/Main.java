package com.pacman;

import javax.swing.JFrame;

import com.pacman.Game.GamePanel;

public class Main {
    public static void main(String[] args) {
        
        JFrame window = new JFrame("PAC MAN");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        GamePanel gamePanel = new GamePanel();
        //CollisionThread collisionThread = new CollisionThread(gamePanel);
        
        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        
        gamePanel.startGameThread();
        //collisionThread.collisionThread();
        gamePanel.requestFocus();
    }
}