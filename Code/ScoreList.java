import java.io.*;
import java.util.*;

public class ScoreList {
    private static final String SCORE_FILE = "score_lists.txt";
    private ArrayList<ScoreRecord> scoreRecords;

    // Constructor
    public ScoreList() {
        scoreRecords = new ArrayList<>();
        loadScoresFromFile();
    }

    // Add or update player's score
    public void updateScore(Player player) {
        String playerName = player.getUsername();
        int playerScore = player.getScore();
        boolean found = false;
        for (ScoreRecord record : scoreRecords) {
            if (record.getPlayer().equals(playerName)) {
                record.setScore(playerScore);
                found = true;
                break;
            }
        }
        if (!found) {
            scoreRecords.add(new ScoreRecord(playerName, playerScore));
        }
        saveScoresToFile();
    }

    // Method to get sorted score list
    public List<ScoreRecord> getTopScores(int topN) {
        scoreRecords.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));
        return scoreRecords.subList(0, Math.min(topN, scoreRecords.size()));
    }

    // Save scores to file
    private void saveScoresToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SCORE_FILE))) {
            for (ScoreRecord record : scoreRecords) {
                writer.write(record.getPlayer() + "," + record.getScore());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load scores from file
    private void loadScoresFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SCORE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String player = parts[0];
                int score = Integer.parseInt(parts[1]);
                scoreRecords.add(new ScoreRecord(player, score));
            }
        } catch (FileNotFoundException e) {
            System.out.println("No previous score lists found. Starting with an empty list.");
            System.out.println();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@Override
	public String toString() {
		return "ScoreList [scoreRecords=" + scoreRecords + "]";
	}
    
    
}