import java.util.Scanner;

public class main {
	private static int amountOfPlayers = 2;
	private static String[] playerNames = null;
	private static boolean gameRunning = true;
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Welcome to dominion");
		
		System.out.print("Please choose the amount of players: ");
		amountOfPlayers = Integer.parseInt(in.nextLine());
		playerNames = new String[amountOfPlayers];
		
		for (int i = 0; i < amountOfPlayers; i++) {
			System.out.print("Player " + (i + 1) + " name: ");
			playerNames[i] = in.nextLine();
		}
		
		
	}
	
	private static void gameLoop() {
		while (gameRunning) {
			
		}
	}

}
