import javax.swing.JButton;
import java.util.List;
import java.util.ArrayList;

class State implements Constants{

	private	char[][] grid;
	private int utility;
	private int player;
	private List<State> children = new ArrayList<>();

	public State(char[][] grid, int player){
		this.player = player;
		this.grid = grid;
	}

	public char[][] getGrid(){
		return this.grid;
	}

	public int getUtility(){
		return this.utility;
	}

	public int getPlayer(){
		return this.player;
	}

	public void addChild(State state){
		this.children.add(state);
	}

	public List<State> getChildren(){
		return this.children;
	}

	public void setUtility(int utility){
		this.utility = utility;
	}

	public boolean isMaxNode(){
		return (player == AI_TURN);
	}

	public boolean isMinNode(){
		
		return (player == PLAYER_TURN);
	}

	public boolean isTerminal(){
		String s00 = (grid[0][0] == '\u0000')? "":""+grid[0][0];
		String s01 = (grid[0][1] == '\u0000')? "":""+grid[0][1];
		String s02 = (grid[0][2] == '\u0000')? "":""+grid[0][2];
		String s10 = (grid[1][0] == '\u0000')? "":""+grid[1][0];
		String s11 = (grid[1][1] == '\u0000')? "":""+grid[1][1];
		String s12 = (grid[1][2] == '\u0000')? "":""+grid[1][2];
		String s20 = (grid[2][0] == '\u0000')? "":""+grid[2][0];
		String s21 = (grid[2][1] == '\u0000')? "":""+grid[2][1];
		String s22 = (grid[2][2] == '\u0000')? "":""+grid[2][2];

		//left to right, top to bottom
		if(!hasEmpty(s00,s01,s02) && isTrioTheSame(s00,s01,s02)){
			this.utility = (s00.equals("O") ? -10 : 10);
			return true;
		}

		if(!hasEmpty(s10,s11,s12) && isTrioTheSame(s10,s11,s12)){
			this.utility = (s10.equals("O") ? -10 : 10);
			return true;
		}
		if(!hasEmpty(s20,s21,s22) && isTrioTheSame(s20,s21,s22)){
			this.utility = (s20.equals("O") ? -10 : 10);
			return true;
		}

		//top to bottom, left to right
		if(!hasEmpty(s00,s10,s20) && isTrioTheSame(s00,s10,s20)){
			this.utility = (s00.equals("O") ? -10 : 10);
						System.out.println("NANI HERE");
			System.out.println(this);
			System.out.println("UTILITY HERE:"+this.utility);
			return true;
		}
		if(!hasEmpty(s01,s11,s21) && isTrioTheSame(s01,s11,s21)){
			this.utility = (s01.equals("O") ? -10 : 10);
			return true;
		}
		if(!hasEmpty(s02,s12,s22) && isTrioTheSame(s02,s12,s22)){
			this.utility = (s02.equals("O") ? -10 : 10);
			return true;
		}

		//check diagonals
		if(!hasEmpty(s00,s11,s22) && isTrioTheSame(s00,s11,s22)){
			this.utility = (s00.equals("O") ? -10 : 10);
			return true;
		}

		if(!hasEmpty(s02,s11,s20) && isTrioTheSame(s02,s11,s20)){
			this.utility = (s02.equals("O") ? -10 : 10);
			return true;
		}

		//DRAW
		if(!hasEmpty(s00,s01,s02) && !hasEmpty(s10,s11,s12) && !hasEmpty(s20,s21,s22)){
			this.utility = 0;
			return true;
		}

		return false;
	}

	private boolean isTrioTheSame(String a, String b, String c){
		return (a.equals(b) && b.equals(c));
	}

	private boolean hasEmpty(String a, String b, String c){
		return (a.equals(EMPTY) || b.equals(EMPTY) || c.equals(EMPTY));
	}

	@Override
	public String toString(){
		String retVal ="";

		for(int i = 0; i < GRID_SIZE; i++){
			for(int j = 0; j < GRID_SIZE; j++){
				retVal += ((grid[i][j] == '\u0000')?"E":grid[i][j]) + " ";
			}
			retVal += "\n";
		}
		return retVal;
	}

}