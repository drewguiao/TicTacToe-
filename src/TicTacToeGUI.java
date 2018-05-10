import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
class TicTacToeGUI implements Constants{
	private JFrame gameFrame;
	private JPanel controlPanel, gamePanel;
	private JButton[][] grid = new JButton[GRID_SIZE][GRID_SIZE];
	private JButton resetButton;

	private int numOfClicks = 0;

	public TicTacToeGUI(){
		buildControlPanel();
		buildGamePanel();
		buildGameFrame();
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
				JButton clickedButton = (JButton) ae.getSource();
				if(clickedButton.getText().equals(EMPTY_STRING)){
					clickedButton.setText(getToken());

				}
			}
		};
		return listener;
	}

	private void resetGrid(){
		for(int i = 0; i < GRID_SIZE; i++){
			for(int j = 0 ; j < GRID_SIZE; j++){
				this.grid[i][j].setText(EMPTY_STRING);
			}
		}
	}

	private String getToken(){
		if(numOfClicks % 2 == 0){
			numOfClicks = 1;
			return "O";	
		}
		else{
			numOfClicks = 0;
			 return "X";
		}
	}

}