import java.util.List;
import java.util.ArrayList;

class MinMaxAlgo implements Constants{

	public MinMaxAlgo(){}


	public int getValue(State state){
		if(state.isTerminal()) return state.getUtility();
		if(state.isMaxNode()) return getMaxValue(state);
		if(state.isMinNode()) return getMinValue(state);
		return 0;
	}

	private int getMaxValue(State state){
		int m = Integer.MIN_VALUE;
		List<Action> possibleActions = getPossibleActions(state, state.getPlayer());
		for(Action a: possibleActions){
			State nextState = getResult(state, a);
			state.addChild(nextState);
			int v = getValue(nextState);
			m = Math.max(m,v);
		}
		state.setUtility(m);
		return m;
	}

	private int getMinValue(State state){
		int m = Integer.MAX_VALUE;
		List<Action> possibleActions = getPossibleActions(state, state.getPlayer());
		for(Action a: possibleActions){
			State nextState = getResult(state, a);
			state.addChild(nextState);
			int v = getValue(nextState);
			m = Math.min(m,v);
		}
		state.setUtility(m);
		return m;
	}

	private List<Action> getPossibleActions(State state, int player){
		List<Action> possibleActions = new ArrayList<>();
		char[][] map = state.getGrid();
		for(int i = 0; i < GRID_SIZE; i++){
			for(int j = 0; j < GRID_SIZE; j++){
				if(map[i][j] == '\u0000') possibleActions.add(new Action(i,j,player));
			}
		}
		return possibleActions;
	}


	private State getResult(State state, Action action){
		char[][] newGrid = copyGrid(state.getGrid());
		int player = action.getPlayer();
		newGrid[action.getX()][action.getY()] = getToken(player).charAt(0);
		int nextPlayer = (player == PLAYER_TURN) ? AI_TURN : PLAYER_TURN;
		return new State(newGrid, nextPlayer);
	}

	private String getToken(int player){
		if(player == PLAYER_TURN) return "O";
		else return "X";
	}

	private char[][] copyGrid(char[][] grid){
		char[][] gridCopy = new char[GRID_SIZE][GRID_SIZE];

		for(int i = 0; i < GRID_SIZE; i++){
			for(int j = 0; j < GRID_SIZE; j++){
				gridCopy[i][j] = ("" +grid[i][j]).charAt(0);
			}
		}
		return gridCopy;
	}

	private void printGrid(char[][] map){
		for(int i = 0; i < GRID_SIZE; i++){
			for(int j = 0; j < GRID_SIZE; j++){
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
	}

	
}