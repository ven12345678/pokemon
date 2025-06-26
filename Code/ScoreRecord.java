public class ScoreRecord {
    private String player;
    private int score;

    public ScoreRecord() {
    	
    }
    
    public ScoreRecord(String player, int score) {
        this.player = player;
        this.score = score;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return String.format("Player: %s, Score: %d", player, score);
    }
}