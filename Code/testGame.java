import java.util.Scanner;

public class testGame {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		Game g;
		while (true) {
			g = new Game();
			g.start();
			
	        // Ask if the user wants to restart or exit
	        System.out.println("Press [0] to exit or enter any key to restart the game: ");
	        String restartChoice = input.nextLine();
	 
	        if (restartChoice.equals("0")) {
	            System.out.println("Exiting the game. Goodbye!");
	            input.close();
	            break;
	        }
	        System.out.println("Restarting the game...");
	    }
	}
}