package player;

import java.awt.event.MouseEvent;

import object.Board;

public abstract class Player {

	protected final int COLOR;								//player color
	protected boolean active;								//is it the player's turn?
	
	
	/**
	 * abstract constructor
	 * @param color
	 */
	protected Player (int color) {
		this.COLOR = color;
		
		//not your turn yet!
		deactivate();
	}
	
	public abstract void startTurn (Board b);				//start this player's turn
	public abstract void endTurn (Board b);					//end this player's turn
	public abstract void update (Board b);					//update this player
	public abstract void mouseEvent (MouseEvent e);			//send mouse input to this player
	
	/**
	 * activate this
	 */
	protected void activate () {
		active = true;
	}
	
	/**
	 * deactivate this
	 */
	protected void deactivate () {
		active = false;
	}
	
	public int getColor () {
		return COLOR;
	}
	
}
