import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
	private Player player;
	private Stage stage;
	private Battle battle;
	private ScoreList scorelist;
	
	public Game() {
		player = null;
		stage = new Stage();
		battle = new Battle();
		scorelist = new ScoreList();
	}
	
	public void start() {
		Scanner input = new Scanner(System.in);
	        
        System.out.println("These are the existing usernames: ");
        System.out.println(Player.getAllUsername());
        System.out.println();

        while (player == null) {
            try {
                System.out.println("Do you want to (1) create a new username or (2) load an existing username?");
                int choice = input.nextInt();
                input.nextLine(); // Consume newline

                if (choice == 1) {
                    // Create a new username
                    System.out.println("Enter your new username: ");
                    String username = input.nextLine();

                    try {
                        player = new Player(username, 0, false, false);
                        System.out.println("New username created successfully.");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                } else if (choice == 2) {
                    // Load an existing username
                    System.out.println("Enter your existing username: ");
                    String username = input.nextLine();

                    try {
                    	player= new Player(username, 0, false, true);
                        player.loadPokemonsFromFile();
                        System.out.println("Username loaded successfully.");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number (1 or 2).");
                input.next(); // Clear the invalid input
            }
        }

        // Display player's Pokémon
        displayMyPokemon();
        
        // Player chooses a stage and catches a Pokémon
        stage.chooseStageAndCatchPokemon(player);

        // Save player's Pokémon to file before exiting
        player.savePokemonsToFile();
        
        // Display player's Pokémon
        displayMyPokemon();
        
        System.out.println();
        System.out.println("-------------------------------------");
		System.out.println("Press 'Enter' to start the battle");
		System.out.println("-------------------------------------");
        String userInput = input.nextLine();
        

        //Call battle to start battle
        battle.startBattle(player);
        player.savePokemonsToFile();
        
        
        scorelist.updateScore(player);
        showTop5Scores();
        
	}

	private void displayMyPokemon() {
        System.out.println();
        System.out.println(player.getUsername() + "'s Pokémon: ");
        for (int i = 0; i < player.getPokemons().size(); i++) {
            Pokemon p = player.getPokemons().get(i);
            System.out.println("[" + (i + 1) + "] " + p.getName());
        }
	}
	
	private void showTop5Scores() {
		System.out.println();
        System.out.println("Top 5 Scores:");
        for (ScoreRecord record : scorelist.getTopScores(5)) {
            System.out.println(record);
        }
        System.out.println();
    }

	@Override
	public String toString() {
		return "Game [player=" + player + ", stage=" + stage + ", battle=" + battle + "]";
	}
	
}
