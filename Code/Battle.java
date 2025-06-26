import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Battle {
    private Player botPlayer;
    private ArrayList<Pokemon> usernamePokemon;
    private ArrayList<Pokemon> player2Pokemon;
    private Roulette roulette;
    private Pokeball pokeball;
    private int battleCount;

    public Battle() {
        botPlayer = new Player("Player2", 0, true, false);
        usernamePokemon = new ArrayList<>();
        player2Pokemon = new ArrayList<>();
        roulette = new Roulette();
        pokeball = new Pokeball();
    }

    public void startBattle(Player username) {
    	battleCount = readBattleCount(username);
    	battleCount++;
    	
    	Scanner scanner = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("Depart for Battle!");
        System.out.println("========================================");

        usernamePokemon.clear();
        usernamePokemon.addAll(username.getPokemons());

        if (usernamePokemon.size() < 2) {
            System.out.println("You need at least 2 Pokémons from the Pokemon lists to start a battle.");
            System.out.println();
        }
        
        
        if (usernamePokemon.size() == 1) {
        	Pokemon playerSecPoke = generateRandomPokemon();    	
        	while(usernamePokemon.contains(playerSecPoke)) {
        		playerSecPoke = generateRandomPokemon();
        	}
            usernamePokemon.add(playerSecPoke);
            
            System.out.println("A random Pokemon has been rented to you: " + usernamePokemon.get(1).getName());
        
        } else {
        	// choose first Pokémon
            System.out.println("Choose your first Pokémon to send to battle: ");
            System.out.println("* Note: effectiveness attack calculation will be based on first Pokémon, choose wisely!");
            System.out.println();
            for (int i = 0; i < usernamePokemon.size(); i++) {
                System.out.println((i+1) + ". " + usernamePokemon.get(i).getName() + " - HP: " + usernamePokemon.get(i).getHp());
            }
            

            while (true) {
                try {
                	int firstPokemonIndex = scanner.nextInt() - 1;
                    if (firstPokemonIndex < 0 || firstPokemonIndex >= usernamePokemon.size()) {
                        System.out.println("Please enter a valid number.");
                        continue;
                    }
                    Pokemon firstPokemon = usernamePokemon.remove(firstPokemonIndex);
                    usernamePokemon.add(0, firstPokemon);
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid number.");
                    scanner.nextLine(); // Consume the invalid input
                }
            }

            
            // choose second Pokémon
            System.out.println("Choose your second Pokémon to send to battle: ");
            System.out.println();
            for (int i = 1; i < usernamePokemon.size(); i++) {
        		System.out.println((i) + ". " + usernamePokemon.get(i).getName() + " - HP: " + usernamePokemon.get(i).getHp());
            }
            
            while (true) {
                try {
                    int secondPokemonIndex = scanner.nextInt();
                    if (secondPokemonIndex < 1 || secondPokemonIndex>= usernamePokemon.size()) {
                        System.out.println("Please enter a valid number.");
                        continue;
                    }
                    Pokemon secondPokemon = usernamePokemon.remove(secondPokemonIndex);
                    usernamePokemon.add(1, secondPokemon);
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid number.");
                    scanner.nextLine(); // Consume the invalid input
                }
            }   
            
        }
        

        player2Pokemon.clear();       
        
      
        //bot pokemon cannot be the same as username pokemon
        Pokemon botFirstPokemon = generateRandomPokemon();
        while (botFirstPokemon.equals(usernamePokemon.get(0)) || botFirstPokemon.equals(usernamePokemon.get(1))) {
            botFirstPokemon = generateRandomPokemon();
        }
        player2Pokemon.add(botFirstPokemon);
        
        // bot cannot have 2 same pokemons
        Pokemon botSecondPokemon = generateRandomPokemon();
    	while(player2Pokemon.contains(botSecondPokemon)|| botFirstPokemon.equals(usernamePokemon.get(0)) || botFirstPokemon.equals(usernamePokemon.get(1))) {
    		botSecondPokemon = generateRandomPokemon();
    	}
        player2Pokemon.add(botSecondPokemon);

        System.out.println("You sent out " + usernamePokemon.get(0).getName() + " and " + usernamePokemon.get(1).getName());
        System.out.println("Opponent sent out " + player2Pokemon.get(0).getName() + " and " + player2Pokemon.get(1).getName());

        displayBattlePokemonHP(username);
        		
        int userNumber = getUserGuess(scanner);
        int botNumber = new Random().nextInt(100) + 1;
        int targetNumber = new Random().nextInt(100) + 1;

        while (Math.abs(userNumber - targetNumber) == Math.abs(botNumber - targetNumber)) {
            System.out.println("It's a tie or both numbers are equally close to the target. Please try again");
            System.out.println();
            userNumber = getUserGuess(scanner);
            botNumber = new Random().nextInt(100) + 1;
            targetNumber = new Random().nextInt(100) + 1;
        }
        
        System.out.println("The hidden number was: " + targetNumber);
        System.out.println(username.getUsername() + " guessed: " + userNumber);
        System.out.println("Player 2 guessed: " + botNumber);

        boolean userGoesFirst = Math.abs(userNumber - targetNumber) < Math.abs(botNumber - targetNumber);

        
        if (userGoesFirst) {
            System.out.println(username.getUsername() + " gets to attack first!");
            usernameAttack(username);
            player2Attack(username);
        } else {
            System.out.println("Player 2 gets to attack first!");
            player2Attack(username);
            usernameAttack(username);
        }

        executeSpecialAttack(username);
        displayBattlePokemonHP(username);

        Player winner = determineWinner(username);
        if (winner != null) {
            calculateScore(winner, username);
            displayScoresAfterBattle(username);
            if (winner.equals(username)) {
                Pokemon caughtpokemon = attemptToCatchDefeatedPokemon(scanner);
                caughtpokemon.restorePokemonHp(caughtpokemon);
                username.addPokemon(caughtpokemon);          
            }
            
        }
        
        // restore all the Pokemon HP for storing
        for(Pokemon pokemon : usernamePokemon) {
        	pokemon.restorePokemonHp(pokemon);
        }
        for(Pokemon pokemon : player2Pokemon) {
        	pokemon.restorePokemonHp(pokemon);
        }
        
        saveBattleRecord(username, winner, battleCount);
    }
    
    private Pokemon generateRandomPokemon() {
        List<Pokemon> allPokemons = Pokemon.getAllPokemon();
        int index = new Random().nextInt(allPokemons.size());
        return allPokemons.get(index);
    }
    
    private void displayBattlePokemonHP(Player username) {
        System.out.println();
        System.out.println("Current Pokémon HP:");
        System.out.println();
        System.out.println(username.getUsername() + "'s Pokémon:");
        System.out.println(usernamePokemon.get(0).getName() + " - HP: " + usernamePokemon.get(0).getHp());
        System.out.println(usernamePokemon.get(1).getName() + " - HP: " + usernamePokemon.get(1).getHp());
        System.out.println();
        System.out.println("\nPlayer 2's Pokémon:");
        System.out.println(player2Pokemon.get(0).getName() + " - HP: " + player2Pokemon.get(0).getHp());
        System.out.println(player2Pokemon.get(1).getName() + " - HP: " + player2Pokemon.get(1).getHp());
        System.out.println();
    }
    
    
    private int getUserGuess(Scanner scanner) {
        int guess = -1;
        while (guess < 1 || guess > 100) {
            System.out.print("Enter a number between 1 and 100 to guess the hidden number: ");
            try {
            	guess = scanner.nextInt();
                if (guess < 1 || guess > 100) {
                    System.out.println("Invalid input. Please enter a number between 1 and 100.");
                    scanner.next();
                }    
                
            } catch (InputMismatchException e) {
            	System.out.println("Invalid input. Please enter a number between 1 and 100.");
            	scanner.next();
            }  
        }
        return guess;
    }
    
    
    private void usernameAttack(Player username) {
        System.out.println();
        System.out.println(username.getUsername() + "'s turn to attack.");
        if (!usernamePokemon.isEmpty() && !player2Pokemon.isEmpty()) {
            
        	Scanner input = new Scanner(System.in);
            int num = -1;

            while (true) {
                System.out.println("Enter a number between 1 and 3 to spin the roulette:");
                try {
                    num = input.nextInt();
                    if (num >= 1 && num <= 3) {
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter a number between 1 and 3.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
                    input.nextLine(); // Consume the invalid input
                }
            }

        	attack(username, usernamePokemon.get(0), botPlayer, player2Pokemon, usernamePokemon);
            displayBattlePokemonHP(username);
        }
    }

    private void player2Attack(Player username) {
        System.out.println();
        System.out.println("Player 2's turn to attack.");
        if (!player2Pokemon.isEmpty() && !usernamePokemon.isEmpty()) {
            attack(botPlayer, player2Pokemon.get(0), username, usernamePokemon, player2Pokemon);
            displayBattlePokemonHP(username);
        }
    }
    
    
    private void attack(Player attacker, Pokemon attackerPokemon, Player defender, List<Pokemon> defenderPokemons, List<Pokemon> attackerPokemons) {
    	if (attackerPokemon == null || defenderPokemons == null || defenderPokemons.isEmpty()) {
            System.out.println("Invalid attack conditions.");
            return;
        }
    	
    	for(Pokemon defenderPoke: defenderPokemons) {
    		int effectiveness = attackerPokemon.calEffectiveness(defenderPoke);
            defenderPoke.decreaseHp(effectiveness);
    	}
    	
        roulette.spin();
        System.out.println();
        System.out.println("Roulette spinned!");
        System.out.println(attacker.getUsername() + " uses " + roulette.getType() + " " + roulette.getPoints() + "!");

        String type = roulette.getType();
        int points = roulette.getPoints();

        switch (type) {
        case "heal":
            for (Pokemon pokemon : attackerPokemons) {
                pokemon.increaseHp(points);
            }
            break;
        case "attack":
            for (Pokemon pokemon : defenderPokemons) {
                pokemon.decreaseHp(points);
            }
            break;
        }
        
        System.out.println();
        System.out.println("Calculating HP based on effectiveness attack and roulette spinned...");
        System.out.println("-------------------------------------------------");
    	System.out.println("Press Enter to continue: ");
    	System.out.println("-------------------------------------------------");
        
        // Wait for user to press Enter
        try {
            System.in.read();
        } catch (IOException e) {
            System.out.println("An error occurred while waiting for Enter key.");
        }
        
    }
    
    
    private void executeSpecialAttack(Player username) {
        int userTotalHp = usernamePokemon.get(0).getHp() + usernamePokemon.get(1).getHp();
        int playerTotalHp = player2Pokemon.get(0).getHp() + player2Pokemon.get(1).getHp();
        
        if (userTotalHp > playerTotalHp) {
            System.out.println("Congratulations! You have a higher total HP! You won a special attack!");
            System.out.println();
            specialAttack(username, player2Pokemon);
        } else if (playerTotalHp > userTotalHp) {
            System.out.println("Oh no... Opponent has a higher total HP... Get ready for a special attack...");
            System.out.println();
            specialAttack(botPlayer, usernamePokemon);
        } else {
        	System.out.print("No special attack executed for both.");
        	System.out.println();
        }
    }

    
    
    private void specialAttack(Player attacker, List<Pokemon> defenderPokemons) {
        System.out.println(attacker.getUsername() + " uses special attack!");
        for (Pokemon defenderPokemon : defenderPokemons) {
            defenderPokemon.decreaseHp(defenderPokemon.getHp()); // Setting HP to 0 (fainted)
        }
    }

    private Player determineWinner(Player username) {
        boolean usernameAllFainted = usernamePokemon.stream().allMatch(p -> p.getHp() <= 0);
        boolean player2AllFainted = player2Pokemon.stream().allMatch(p -> p.getHp() <= 0);

        if (player2AllFainted) {
        	System.out.println(username.getUsername() + " wins!");
        	return username;
        } else if (usernameAllFainted) {
            System.out.println("Player 2 wins!");
            return botPlayer;
        } else{
        	System.out.println("No one wins. It's a draw!");
        	return null;
        } 
    }


    private void calculateScore(Player winner, Player username) {
        int winnerScore = 0;
        if (winner.equals(username)) {
            winnerScore = usernamePokemon.get(0).getHp() + usernamePokemon.get(1).getHp();
        } else if (winner.equals(botPlayer)) {
            winnerScore = player2Pokemon.get(0).getHp() + player2Pokemon.get(1).getHp();
        } else {
        	System.out.println("It's a draw! cannot calculate score");
        }
        winner.setScore(winner.getScore() + winnerScore);
        System.out.println();
        System.out.println("Calculating score for " + winner.getUsername()+ " ...");
        System.out.println("-------------------------------------------------");
        System.out.println("Press Enter to continue: ");
        System.out.println("-------------------------------------------------");
        
        // Wait for user to press Enter
        try {
            System.in.read();
        } catch (IOException e) {
            System.out.println("An error occurred while waiting for Enter key.");
        }

        System.out.println();
    }

    private void displayScoresAfterBattle(Player username) {
        System.out.println("Scores after battle:");
        System.out.println(username.getUsername() + ": " + username.getScore());
        System.out.println(botPlayer.getUsername() + ": " + botPlayer.getScore());
    }

    
    private Pokemon attemptToCatchDefeatedPokemon(Scanner scanner) {
        Pokemon selectedPokemon = null;
    	if (!player2Pokemon.isEmpty()) {
            List<Pokemon> defeatedPokemons = new ArrayList<>();
            for (Pokemon defeatedPokemon : player2Pokemon) {
                if (defeatedPokemon.getHp() <= 0) { // Only add fainted Pokémon
                    defeatedPokemons.add(defeatedPokemon);
                }
            }

            if (defeatedPokemons.isEmpty()) {
                System.out.println("No defeated Pokémon available to catch.");
                return null;
            }
            
            System.out.println();
            System.out.println("========================================");
            System.out.println("Available defeated Pokémon to catch:");
            for (int i = 0; i < defeatedPokemons.size(); i++) {
                System.out.println((i + 1) + ". " + defeatedPokemons.get(i).getName());
            }
            System.out.println("========================================");

            int selectedPokemonIndex = -1;
            while (selectedPokemonIndex < 0 || selectedPokemonIndex >= defeatedPokemons.size()) {
                System.out.println();
            	System.out.print("Enter the number of the defeated Pokémon you want to catch (1 or 2): ");
                try {
                    selectedPokemonIndex = scanner.nextInt() - 1;
                    if (selectedPokemonIndex < 0 || selectedPokemonIndex >= defeatedPokemons.size()) {
                        System.out.println("Invalid input. Please enter a valid number.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.next(); // Clear the invalid input
                }
            }

            selectedPokemon = defeatedPokemons.get(selectedPokemonIndex);
            System.out.println();
    		System.out.println("-------------------------------------------------");
            System.out.println("Attempt to catch the defeated Pokémon: " + selectedPokemon.getName());
    		System.out.println("-------------------------------------------------");
    		System.out.println();

            // Randomly assign a type of Pokéball
    		pokeball = getRandomPokeball();
            System.out.println("You received a " + pokeball.getClass().getSimpleName());

            // Attempt to catch the Pokémon with the assigned Pokéball
            if (pokeball.catchPokemon(selectedPokemon)) {
                System.out.println("Congratulations! You caught the Pokémon: " + selectedPokemon.getName()+"!");
                //username.catchPokemon(selectedPokemon);
            } else {
                System.out.println("OhNOOO...The Pokémon escaped!");
            }
        }
    	return selectedPokemon;
    }

    private Pokeball getRandomPokeball() {
        Random rand = new Random();
        int pokeballType = rand.nextInt(4); // Assuming 4 types of Pokéballs
        switch (pokeballType) {
            case 0:
                return new BasicPokeball();
            case 1:
                return new Greatball();
            case 2:
                return new Ultraball();
            case 3:
                return new Masterball();
            default:
                return new BasicPokeball();
        }

    }

    private void saveBattleRecord(Player username, Player winner, int battleCount) {
        String fileName = username.getUsername() + "_battle_records.txt";
        try (FileWriter fw = new FileWriter(fileName, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println("Battle #" + battleCount + " result: " + (winner == null ? "Draw" : winner.getUsername() + " wins"));
        } catch (IOException e) {
            System.out.println("An error occurred while saving the battle record.");
        }
    }
    
    

    private int readBattleCount(Player username) {
    	String lastLine = "";

        // Read the file to get the last line
        try (BufferedReader br = new BufferedReader(new FileReader(username.getUsername() + "_battle_records.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                lastLine = line;  // Update lastLine to be the current line
            }
        } catch (IOException e) {
            System.out.println("");
            return 0;  // Return a special value to indicate error
        }

        // Extract the battle number using regex
        Pattern pattern = Pattern.compile("Battle #(\\d+) result");
        Matcher matcher = pattern.matcher(lastLine);
        if (matcher.find()) {
            // Extracted battle number as a string
            String numberStr = matcher.group(1);
            return Integer.parseInt(numberStr); // Convert to integer
        } else {
            System.out.println("No battle record found, starting a new one...");
            return 0;  // Return a special value to indicate no number found
        }
    }
    
	public int getBattleCount() {
		return battleCount;
	}

	@Override
	public String toString() {
		return "Battle [botPlayer=" + botPlayer + ", usernamePokemon=" + usernamePokemon + ", player2Pokemon="
				+ player2Pokemon + ", roulette=" + roulette + ", pokeball=" + pokeball + ", battleCount=" + battleCount
				+ "]";
	}  
    
}