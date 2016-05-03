package player;

public class Point {		//stores an x and y

	private int x, y;
	
	
	/**
	 * constructor
	 * @param x
	 * @param y
	 */
	public Point (int x, int y)	{	
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
}
