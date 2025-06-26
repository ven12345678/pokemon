import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Stage {

    // Attributes
	private Pokemon pokemon;
    private ArrayList<Pokemon> stage1Pokemon; // Stores electric type pokemons
    private ArrayList<Pokemon> stage2Pokemon; // Stores fire type pokemons
    private ArrayList<Pokemon> stage3Pokemon; // Stores water type pokemons

    // Constructor
    public Stage() {
    	pokemon = new Pokemon();
        stage1Pokemon = new ArrayList<>();
        stage2Pokemon = new ArrayList<>();
        stage3Pokemon = new ArrayList<>();

        for (Pokemon p : Pokemon.getAllPokemon()) {
            if (p instanceof Electric) {
                stage1Pokemon.add(p);
            } else if (p instanceof Fire) {
                stage2Pokemon.add(p);
            } else if (p instanceof Water) {
                stage3Pokemon.add(p);
            }
        }
    }

    // Methods
    // Choose stages
    public ArrayList<Pokemon> stageOptions(int opt) {
        switch (opt) {
            case 1:
                return stage1Pokemon;
            case 2:
                return stage2Pokemon;
            case 3:
                return stage3Pokemon;
            default:
                throw new IllegalArgumentException("Invalid stage option: " + opt);
        }
    }

    // Prints the stages and all pokemons for each stage
    public void displayStageOptions() {
    	System.out.println();
    	System.out.println("==========================================================================");
    	System.out.println();
        System.out.println("Stage available:");
        System.out.print("Stage 1: ");
        displayAllPokemonNames(stage1Pokemon);
        System.out.print("Stage 2: ");
        displayAllPokemonNames(stage2Pokemon);
        System.out.print("Stage 3: ");
        displayAllPokemonNames(stage3Pokemon);
        System.out.println();
        System.out.println("==========================================================================");
    }

    // Takes a list of Pokémon and prints the names of all Pokémon in the list.
    private void displayAllPokemonNames(ArrayList<Pokemon> pokemons) {
        System.out.print("[");
        for (int i = 0; i < pokemons.size(); i++) {
            System.out.print(pokemons.get(i).getName());
            if (i < pokemons.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    // Catches and returns a Pokémon from the chosen stage, saving it to the player's Pokémon list if not a duplicate.
    public Pokemon chooseStageAndCatchPokemon(Player player) {
        Scanner scanner = new Scanner(System.in);
        boolean validStageChosen = false;
        int stageChoice = 0;

        while (!validStageChosen) {
            displayStageOptions();
            System.out.println();
            System.out.println();
            System.out.print("Choose a stage (1-3): ");
            try {
                stageChoice = scanner.nextInt();
                if (stageChoice >= 1 && stageChoice <= 3) {
                    validStageChosen = true;
                    System.out.println("You have chosen stage " + stageChoice);
                    System.out.println();
                } else {
                    System.out.println("Invalid stage choice. Please choose a valid stage.");
                    System.out.println();
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
                System.out.println();
                scanner.next(); // Clear the invalid input
            }
        }

        // Display 3 random Pokémon from the chosen stage
        ArrayList<Pokemon> stagePokemons = stageOptions(stageChoice);
        ArrayList<Pokemon> randomPokemons = new ArrayList<>(stagePokemons);
        Collections.shuffle(randomPokemons);
        ArrayList<Pokemon> displayedPokemons = new ArrayList<>();
        for (int i = 0; i < 3 && i < randomPokemons.size(); i++) {
            displayedPokemons.add(randomPokemons.get(i));
        }
        
        System.out.println("Available Pokemons to Catch:");
        for (int i = 0; i < displayedPokemons.size(); i++) {
            Pokemon pokemon = displayedPokemons.get(i);
            System.out.println("[" + (i + 1) + "] " + pokemon.getName());
        }
        
        // Player catches a Pokémon from the displayed Pokémons
        Pokemon caughtPokemon = null;
        Collections.shuffle(displayedPokemons); // Shuffle the list before checking

        for (Pokemon pokemon : displayedPokemons) {
            if (!player.getPokemons().contains(pokemon)) {
                caughtPokemon = pokemon; // Select the first Pokemon from the shuffled list
                break;
            }
        }

        System.out.println();
        System.out.println("Catching Pokemon based on your luck! Press 'ENTER' to continue...");
        Scanner input = new Scanner(System.in);
        input.nextLine(); //Wait for user to press Enter
        
        if (caughtPokemon != null) {
            player.addPokemon(caughtPokemon);
            System.out.println("-----------------------------------------------------------------------------");
            System.out.println("YAYY, You have successfully caught " + caughtPokemon.getName() + " from Stage " + stageChoice);
            System.out.println("-----------------------------------------------------------------------------");
        } else {
        	System.out.println("---------------------------------------------------------------------------------------------------");
            System.out.println("OH NOO, unsuccessful catch. All available Pokémon from this stage are already in your collection.");
            System.out.println("---------------------------------------------------------------------------------------------------");
        }

        return caughtPokemon;  
        
    }
    
	@Override
	public String toString() {
		return "Stage [stage1Pokemon=" + stage1Pokemon + ", stage2Pokemon=" + stage2Pokemon + ", stage3Pokemon=" + stage3Pokemon + "]";
	}
}