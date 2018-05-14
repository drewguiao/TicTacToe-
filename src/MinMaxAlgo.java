import java.util.List;
import java.util.ArrayList;
import javax.swing.JButton;
class MinMaxAlgo implements Constants{

	public MinMaxAlgo(){}


	public Cell performAlphaBeta(State state){
		int alpha = Integer.MAX_VALUE;
		int beta = Integer.MIN_VALUE;
		int v = getValue(state, beta, alpha);
		List<State> children = state.getChildren();
		for(State s: children){
			if(s.getUtility() == v){
				char[][] grid = state.getGrid();
				Cell cellDifference = getDifference(grid,s.getGrid());
				return cellDifference;
			}
		}
		return null;
	}

	private Cell getDifference(char[][] currentMap, char[][] nextMap){
		for(int i = 0; i < GRID_SIZE; i++){
			for(int j = 0; j < GRID_SIZE; j++){
				// if(!currentMap[i][j].getText().equals(""+((nextMap[i][j] == '\u0000')? "": nextMap[i][j]))){
				// 	return new Cell(i,j,""+nextMap[i][j]);
				// }
				if(currentMap[i][j] != nextMap[i][j]){
					return new Cell(i,j,""+nextMap[i][j]);
				}
			}
		}
		return null;
	}

	public int getValue(State state, int alpha, int beta){
		if(state.isTerminal()) return state.getUtility();
		if(state.isMaxNode()) return getMaxValue(state,alpha,beta);
		if(state.isMinNode()) return getMinValue(state,alpha,beta);
		return 0;
	}

	private int getMaxValue(State state, int alpha, int beta){
		
		int v = Integer.MIN_VALUE;
		List<Action> possibleActions = getPossibleActions(state, state.getPlayer());
		for(Action a: possibleActions){
			State nextState = getResult(state,a);
			v = Math.max(v, getValue(nextState,alpha,beta));
			state.addChild(nextState);

			if(v >= beta){
				state.setUtility(v);
				return v;
			}
			 // alpha.setValue(Math.max(alpha.getValue(),v));
			alpha = Math.max(alpha,v);
		}
		state.setUtility(v);
		return v;
	}

	private int getMinValue(State state, int alpha, int beta){
		
		int v = Integer.MAX_VALUE;
		List<Action> possibleActions = getPossibleActions(state, state.getPlayer());
		for(Action a: possibleActions){
			State nextState = getResult(state,a);
			 v = Math.min(v, getValue(nextState,alpha,beta));
			 state.addChild(nextState);

			if(v <= alpha){
				state.setUtility(v);
				return v;	
			}
			 // beta.setValue(Math.min(beta.getValue(),v));
			beta = Math.min(beta,v);

		}
		state.setUtility(v);
		return v;
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