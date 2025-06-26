import java.util.ArrayList;
import java.util.Objects;

public class Pokemon {
    // attributes
    private String name;
    private String grade;
    private int hp;
    private String type;
    protected int effectiveness;
    private static ArrayList<Pokemon> allPokemon = new ArrayList<>(); // to store all pokemon in a list for easy retrieval

    // empty constructor
    public Pokemon() {
    	createPokemon();
    }
    
    // constructor that is only accessed by child class
    public Pokemon(String name, String grade, int hp, String type) {
        this.name = name;
        this.grade = grade;
        this.hp = hp;
        this.type = type;
    }

    // create and add all pokemon into the list, only accessed by private constructor
    private void createPokemon() {
        if (allPokemon.isEmpty()) { // Check if the list is already populated
            allPokemon.add(new Electric("Pikachu", "Gold", 3000));
            allPokemon.add(new Electric("Electivire", "Platinum", 4000));
            allPokemon.add(new Electric("Lanturn", "Silver", 2000));
            allPokemon.add(new Electric("Jolteon", "Silver", 2000));
            allPokemon.add(new Electric("Ampharos", "Bronze", 1000));

            allPokemon.add(new Fire("Charizard", "Platinum", 4000));
            allPokemon.add(new Fire("Blaziken", "Platinum", 4000));
            allPokemon.add(new Fire("Arcanine ", "Gold", 3000));
            allPokemon.add(new Fire("Vaporeon", "Gold", 3000));
            allPokemon.add(new Fire("Magikarp", "Bronze", 1000));
            allPokemon.add(new Fire("Flareon", "Gold", 3000));

            allPokemon.add(new Water("Squirtle", "Silver", 2000));
            allPokemon.add(new Water("Gyarados", "Gold", 3000));
            allPokemon.add(new Water("Chinchou", "Bronze", 1000));
            allPokemon.add(new Water("Lapras", "Silver", 2000));
        }
    }
 

    // check attack effectiveness
    // return attack points
    // override by child class
    public int calEffectiveness(Pokemon opponent) {
    	return 0;
    }
    
    public void restorePokemonHp(Pokemon caughtpokemon) {

    	switch(caughtpokemon.getGrade()) {
    		case "Bronze":
    			caughtpokemon.setHp(1000);
    			break;
    			
    		case "Silver":
    			caughtpokemon.setHp(2000);
    			break;
    			
    		case "Gold":
    			caughtpokemon.setHp(3000);
    			break;
    			
    		case "Platinum":
    			caughtpokemon.setHp(4000);
    			break;
    	}
    }
    
    public void displayEffectiveness() {
		if (effectiveness == 15) {
			System.out.println("Attack is SUPER EFFECTIVE, extra 15 attack points");
		}
		else if (effectiveness == 10) {
			System.out.println("Attack is NORMAL, extra 10 attack points");
		}
		else {
			System.out.println("Attack is NOT EFFECTIVE, extra 5 attack points");
		}
    }
    

    // getter methods for private variables
    public String getName() {
        return name;
    }

    public String getGrade() {
        return grade;
    }

    public int getHp() {
        return hp;
    }
    
	public String getType() {
		return type;
	}

    // setter methods for private variables
    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }


    public static ArrayList<Pokemon> getAllPokemon() {
        return allPokemon;
    }

    public void decreaseHp(int amount) {
        setHp(getHp() - amount);
    }

    public void increaseHp(int amount) {
        setHp(getHp() + amount);
    }

    @Override
    public String toString() {
        return String.format("Pokemon [name=%s, grade=%s, hp=%s, type=%s], ", name, grade, hp, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pokemon pokemon = (Pokemon) obj;
        return name.equals(pokemon.name) &&
                grade.equals(pokemon.grade) &&
                getType().equals(pokemon.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, grade, getType());
    }
    
}