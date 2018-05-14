import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

class TicTacToeGUI implements Constants{
	private JFrame gameFrame;
	private JPanel controlPanel, gamePanel;
	private JButton[][] grid;
	private JButton resetButton;

	private int turn;
	private int numOfDraws = 0;
	private int numOfAIWins = 0;
	private int numOfPlayerWins = 0;

	public TicTacToeGUI(int firstTurn){
		this.turn = firstTurn;
		this.buildControlPanel();
		this.buildGamePanel();
		this.buildGameFrame();
		Random randomizer = new Random();

		if(this.turn == AI_TURN){
			Cell cell = new Cell(randomizer.nextInt(GRID_SIZE),randomizer.nextInt(GRID_SIZE),"X");
			this.grid[cell.getX()][cell.getY()].setText(cell.getText());
			turn = PLAYER_TURN;
		}
	}

	public void render(){
		this.gameFrame.setVisible(true);
	}

	private void buildControlPanel(){
		this.controlPanel = new JPanel();
		this.resetButton = new JButton(RESET_TITLE);
		this.resetButton.addActionListener(this.provideResetListener());
		this.controlPanel.add(this.resetButton);
	}

	private void buildGamePanel(){
		this.gamePanel = new JPanel();
		this.gamePanel.setLayout(new GridLayout(GRID_SIZE,GRID_SIZE));
		
		this.grid = new JButton[GRID_SIZE][GRID_SIZE];

		for(int i = 0; i < GRID_SIZE; i++){
			for(int j = 0; j < GRID_SIZE; j++){
				this.grid[i][j] = new JButton();
				this.grid[i][j].addActionListener(this.provideOnClickListener());
				this.gamePanel.add(this.grid[i][j]);
			}
		}
	}

	private void buildGameFrame(){
		this.gameFrame = new JFrame(GAME_TITLE);
		this.gameFrame.setLayout(new BorderLayout());

		this.gameFrame.add(this.controlPanel, BorderLayout.PAGE_START);
		this.gameFrame.add(this.gamePanel, BorderLayout.CENTER);
		
		this.gameFrame.setSize(FRAME_WIDTH,FRAME_HEIGHT);
		this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private ActionListener provideResetListener(){
		ActionListener listener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				resetGrid();
			}
		};
		return listener;
	}

	private ActionListener provideOnClickListener(){
		ActionListener listener = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				JButton cell = (JButton) ae.getSource();
				if(cell.getText().equals(EMPTY)){
					updateGrid(cell);
					turn = AI_TURN;

					char[][] charMap = translateGridToCharMap();

					State state = new State(charMap,turn);
					MinMaxAlgo algo = new MinMaxAlgo();
					state.setUtility(algo.getValue(state));
					List<State> children = state.getChildren();
					int stateUtility = state.getUtility();

					if(isTerminal()){
						JOptionPane.showMessageDialog(gameFrame, "PLAYER: "+numOfPlayerWins+" AI: "+numOfAIWins+" Draw:" +numOfDraws);
						resetGrid();
					}else{
						for(State s: children){
							if( stateUtility == s.getUtility()){
								char[][] nextGrid = s.getGrid();
								System.out.println("NEXT STATE GRID I CHOSE:");
								System.out.println(s);
								Cell cellDifference = getDifference(grid,nextGrid);
								grid[cellDifference.getX()][cellDifference.getY()].setText(cellDifference.getText());
								turn = PLAYER_TURN;
								System.out.println("Cell["+cellDifference.getX()+"]["+cellDifference.getY()+"]: " + cellDifference.getText());
								break;
							}
						}
					}

					
					if(isTerminal()){
						JOptionPane.showMessageDialog(gameFrame, "PLAYER: "+numOfPlayerWins+" AI: "+numOfAIWins+" Draw:" +numOfDraws);
						resetGrid();
					}
				}
			}
		};

		return listener;
	}

	private Cell getDifference(JButton[][] currentMap, char[][] nextMap){
		for(int i = 0; i < GRID_SIZE; i++){
			for(int j = 0; j < GRID_SIZE; j++){
				if(!currentMap[i][j].getText().equals(""+((nextMap[i][j] == '\u0000')? "": nextMap[i][j]))){
					return new Cell(i,j,""+nextMap[i][j]);
				}
			}
		}
		return null;
	}

	private void resetGrid(){
		turn = PLAYER_TURN;
		for(int i = 0; i < GRID_SIZE; i++){
			for(int j = 0 ; j < GRID_SIZE; j++){
				this.grid[i][j].setText(EMPTY);
			}
		}
	}

	private String getToken(){
		if(this.turn == PLAYER_TURN) return "O";	
		else return "X";
	}

	private void updateGrid(JButton currentCell){
			currentCell.setText(getToken());
	}
	
	private char[][] translateGridToCharMap(){
		char[][] map = new char[GRID_SIZE][GRID_SIZE];
		for(int i = 0; i < GRID_SIZE; i++){
			for(int j = 0; j < GRID_SIZE; j++){
				map[i][j] = ((grid[i][j].getText().equals(EMPTY)) ? '\u0000': grid[i][j].getText().charAt(0));
			}
		}
		return map;	
	}

	private boolean isTerminal(){
		String s00 = grid[0][0].getText();
		String s01 = grid[0][1].getText();
		String s02 = grid[0][2].getText();
		String s10 = grid[1][0].getText();
		String s11 = grid[1][1].getText();
		String s12 = grid[1][2].getText();
		String s20 = grid[2][0].getText();
		String s21 = grid[2][1].getText();
		String s22 = grid[2][2].getText();

		//left to right, top to bottom
		if(!hasEmpty(s00,s01,s02) && isTrioTheSame(s00,s01,s02)){
			if(s00.equals("O"))numOfPlayerWins++;
			else numOfAIWins++;
			return true;
		}

		if(!hasEmpty(s10,s11,s12) && isTrioTheSame(s10,s11,s12)){
			if(s10.equals("O"))numOfPlayerWins++;
			else numOfAIWins++;
			return true;
		}
		if(!hasEmpty(s20,s21,s22) && isTrioTheSame(s20,s21,s22)){
			if(s20.equals("O"))numOfPlayerWins++;
			else numOfAIWins++;
			return true;
		}

		//top to bottom, left to right
		if(!hasEmpty(s00,s10,s20) && isTrioTheSame(s00,s10,s20)){
			if(s00.equals("O"))numOfPlayerWins++;
			else numOfAIWins++;
			return true;
		}
		if(!hasEmpty(s01,s11,s21) && isTrioTheSame(s01,s11,s21)){
			if(s01.equals("O"))numOfPlayerWins++;
			else numOfAIWins++;
			return true;
		}
		if(!hasEmpty(s02,s12,s22) && isTrioTheSame(s02,s12,s22)){
			if(s02.equals("O"))numOfPlayerWins++;
			else numOfAIWins++;
			return true;
		}

		//check diagonals
		if(!hasEmpty(s00,s11,s22) && isTrioTheSame(s00,s11,s22)){
			if(s00.equals("O"))numOfPlayerWins++;
			else numOfAIWins++;
			return true;
		}

		if(!hasEmpty(s02,s11,s20) && isTrioTheSame(s02,s11,s20)){
			if(s02.equals("O"))numOfPlayerWins++;
			else numOfAIWins++;
			return true;
		}

		//DRAW
		if(!hasEmpty(s00,s01,s02) && !hasEmpty(s10,s11,s12) && !hasEmpty(s20,s21,s22)){
			numOfDraws++;
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

}