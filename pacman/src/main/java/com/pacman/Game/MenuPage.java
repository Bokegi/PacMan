package com.pacman.Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuPage extends JFrame implements ActionListener {
    
    private JButton startGameButton, showLeaderboardButton;
    private JLabel titleLabel;
    private GamePanel gp;
    
    public MenuPage() {
        gp = new GamePanel(); 
        setMenu(); // Chiama il metodo setMenu per impostare tutta la parte grafica
    }
    
    private void setMenu() {
        // Impostazioni di base della finestra
        this.setTitle("Pac-Man Menu");
        this.setSize(new Dimension(gp.screenWidth, gp.screenHeight));
        this.getContentPane().setBackground(Color.BLUE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        // Imposta il layout
        this.setLayout(new BorderLayout());

        // Titolo del gioco
        titleLabel = new JLabel("Pac-Man", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE); // Imposta il colore del testo del titolo su bianco
        this.add(titleLabel, BorderLayout.NORTH); // Aggiungi la scritta in alto al centro
        
        // Pannello per i pulsanti
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false); // Rendi il pannello trasparente per vedere lo sfondo blu
        GridBagConstraints gbc = new GridBagConstraints();

        // Pulsante per avviare una nuova partita
        startGameButton = new JButton("Nuova Partita");
        startGameButton.setFont(new Font("Arial", Font.BOLD, 18));
        startGameButton.setPreferredSize(new Dimension(200, 50)); // Ridefinisce le dimensioni del pulsante
        startGameButton.setBackground(Color.YELLOW);
        startGameButton.setOpaque(true);
        startGameButton.setBorderPainted(false);
        startGameButton.addActionListener(this);

        // Impostazioni per il pulsante "Avvia Nuova Partita"
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0); // Spaziatura
        buttonPanel.add(startGameButton, gbc);
        
        // Pulsante per visualizzare la classifica
        showLeaderboardButton = new JButton("Leaderboard");
        showLeaderboardButton.setFont(new Font("Arial", Font.BOLD, 18));
        showLeaderboardButton.setPreferredSize(new Dimension(200, 50)); // Ridefinisce le dimensioni del pulsante
        showLeaderboardButton.setBackground(Color.YELLOW);
        showLeaderboardButton.setOpaque(true);
        showLeaderboardButton.setBorderPainted(false);
        showLeaderboardButton.addActionListener(this);

        // Impostazioni per il pulsante "Visualizza Leaderboard"
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 10, 0); // Spaziatura
        buttonPanel.add(showLeaderboardButton, gbc);
        
        this.add(buttonPanel, BorderLayout.CENTER); // Aggiungi i pulsanti al centro della finestra,
        this.setVisible(true); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startGameButton) {
            this.dispose(); // Chiude il menu
            JFrame gameFrame = new JFrame();
            gameFrame.add(gp);
            gameFrame.pack();
            gameFrame.setTitle("Pac-Man");
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setVisible(true);
            gp.startGameThread();
        } else if (e.getSource() == showLeaderboardButton) {
            gp.leaderboard.displayLeaderboard(); // Mostra la classifica
        }
    }
}
