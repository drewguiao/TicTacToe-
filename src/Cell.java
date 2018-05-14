class Cell{
	private int x, y;
	private String text;

	public Cell(int x, int y, String text){
		this.x = x;
		this.y = y;
		this.text = text;
	}

	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}

	public String getText(){
		return this.text;
	}
}