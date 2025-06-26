import java.util.Random;

public class Roulette {
    private String type;
    private int points;

    
    public Roulette() {
        
    }

   
    public void spin() {

        String[] types = {"attack", "heal"};
        int[] Points = {30,40,50};
  

        Random rand = new Random();
        this.type = types[rand.nextInt(types.length)];

        if (type.equals("attack")) {
            this.points = Points[rand.nextInt(Points.length)];
        } else if (type.equals("heal")) {
        	this.points = Points[rand.nextInt(Points.length)];
        }
    }

    
    public String getType() {
        return type;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return String.format("Roulette: You can %s for %d hp!", type, points);
    }
    
}
