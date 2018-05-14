import java.util.Scanner;

class TicTacToeRunner{
	public static void main(String[] args){
		Scanner console = new Scanner(System.in);
		System.out.println("Who goes first? [0] Player [1] AI");
		int firstTurn = console.nextInt();
		TicTacToeGUI gui = new TicTacToeGUI(firstTurn);
		gui.render();
	}
}