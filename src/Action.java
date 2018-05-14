class Action{
	private int x;
	private int y;
	private int player;
	
	public Action(int x, int y, int player){
		this.x = x;
		this.y = y;
		this.player = player;
	}

	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}

	public int getPlayer(){
		return this.player;
	}

	@Override
	public String toString(){
		return ""+this.x+","+this.y+"";
	}
}