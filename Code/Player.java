import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Player {
    // Attributes
    private String username;
    private int score;
    private ArrayList<Pokemon> myPokemons;
    private static HashSet<String> existingUsernames = new HashSet<>();
    private static final String USERNAMES_FILE = "usernames.txt";

    // Static block to load usernames when the class is loaded
    static {
        loadUsernamesFromFile();
    }
    
    //empty constructor
    public Player() {
    	
    }

    // Constructor with parameters for creating a new player    
    public Player(String username, int score, boolean isDefaultPlayer, boolean isExistingPlayer) {
        if (!isDefaultPlayer && username.equalsIgnoreCase("Player2")) {
            throw new IllegalArgumentException("Username cannot be 'Player2'. It is the default name for computer.");
        }
        
        
        if (isDefaultPlayer) {
            this.username=username;
        
        } else if(!isExistingPlayer) {
        	setUsername(username);
            setScore(score);
            myPokemons = new ArrayList<>();
            
        } else { //isExistingPlayer
        	if (!existingUsernames.contains(username)) {
                throw new IllegalArgumentException("Username does not exist");
            }
        	else {
        		this.username = username;
                this.score = 0; // Assuming score is not stored
                myPokemons = new ArrayList<>();
        	}

        }
        
    }

    // Methods
    public String getUsername() {
        return username;
    }
    
    public static HashSet<String> getAllUsername() {
        return existingUsernames;
    }
    
    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        } else if (username.contains(" ")) {
            throw new IllegalArgumentException("Username cannot contain space");
        } else if (username.length() > 15) {
            throw new IllegalArgumentException("Username cannot be more than 15 characters");
        } else if (existingUsernames.contains(username)) {
            throw new IllegalArgumentException("Username exists");
        }

        // If changing the username, remove the old one from the set
        if (this.username != null) {
            existingUsernames.remove(this.username);
        }

        this.username = username;
        
        if (!username.equalsIgnoreCase("Player2")) {
        	existingUsernames.add(username);
            saveUsernamesToFile();
        }
        
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<Pokemon> getPokemons() {
        return myPokemons;
    }

    public void addPokemon(Pokemon pokemon) {
        if (!myPokemons.contains(pokemon)) {
            myPokemons.add(pokemon);
        }
    }

    @Override
    public String toString() {
        return String.format("Username: %s, Score: %d, Pokemons: %s", username, score, myPokemons);
    }

    // Save Pokémon to a text file
    public void savePokemonsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(username + "_pokemons.txt"))) {
            for (Pokemon pokemon : myPokemons) {
                writer.write(pokemon.getName() + "," + pokemon.getGrade() + "," + pokemon.getHp() + "," + pokemon.getType());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load Pokémon from a text file
    public void loadPokemonsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(username + "_pokemons.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                String grade = parts[1];
                int hp = Integer.parseInt(parts[2]);
                String type = parts[3];

                Pokemon pokemon = null;
                switch (type) {
                    case "Electric":
                        pokemon = new Electric(name, grade, hp);
                        break;
                    case "Fire":
                        pokemon = new Fire(name, grade, hp);
                        break;
                    case "Water":
                        pokemon = new Water(name, grade, hp);
                        break;
                }
                if (pokemon != null) {
                    addPokemon(pokemon);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No previous Pokémon found for " + username + ". Starting with an empty list.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save existing usernames to a text file
    private static void saveUsernamesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERNAMES_FILE))) {
            for (String username : existingUsernames) {
                writer.write(username);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load existing usernames from a text file
    private static void loadUsernamesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERNAMES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                existingUsernames.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("No previous usernames found. Starting with an empty list.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}