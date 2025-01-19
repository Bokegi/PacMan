package com.pacman.Game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

public class Leaderboard {
    private final String leaderboardPath = "pacman/res/leaderboard.txt";
    private List<PlayerScore> scores;

    public Leaderboard() {
        scores = new ArrayList<>();
        loadScores();
    }

    public void addScore(String playerName, int score) {
        scores.add(new PlayerScore(playerName, score));
        Collections.sort(scores);
        saveScores();
    }

    private void loadScores() {
        scores.clear();  // Clear the current scores
        if (Files.exists(Path.of(leaderboardPath))) {
            try (BufferedReader br = new BufferedReader(new FileReader(leaderboardPath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        try {
                            int score = Integer.parseInt(parts[1]);
                            scores.add(new PlayerScore(parts[0], score));
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid score format: " + parts[1]);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Cannot load leaderboard: " + e.getMessage());
            }
        } else {
            System.out.println("Leaderboard file does not exist.");
        }
    }

    private void saveScores() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(leaderboardPath))) {
            int count = 0;
            for (PlayerScore score : scores) {
                if (count >= 10) {
                    break;  // Ensure only top 10 entries are saved
                }
                System.out.println("Saving: " + score.getPlayerName() + " - " + score.getScore());
                bw.write(score.getPlayerName() + ":" + score.getScore());
                bw.newLine();
                count++;
            }
        } catch (IOException e) {
            System.out.println("Cannot save leaderboard: " + e.getMessage());
        }
    }

    public List<PlayerScore> getTopScores(int limit) {
        return scores.subList(0, Math.min(limit, scores.size()));
    }

    public void displayLeaderboard() {
        StringBuilder leaderboardText = new StringBuilder();

        List<PlayerScore> topScores = getTopScores(10);
        if (topScores.isEmpty()) {
            leaderboardText.append("Nessun punteggio disponibile.");
        } else {
            leaderboardText.append("Leaderboard:\n");
            for (PlayerScore playerScore : topScores) {
                leaderboardText.append(playerScore.getPlayerName())
                        .append(": ")
                        .append(playerScore.getScore())
                        .append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, leaderboardText.toString(), "Leaderboard", JOptionPane.INFORMATION_MESSAGE);
    }
}

class PlayerScore implements Comparable<PlayerScore> {
    private String playerName;
    private int score;

    public PlayerScore(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(PlayerScore other) {
        return Integer.compare(other.score, this.score);
    }
}
