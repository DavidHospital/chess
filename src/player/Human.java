package player;

import java.awt.event.MouseEvent;

import object.Board;
import object.piece.Move;
import object.piece.Piece;

public class Human extends Player {

	private int lastX, lastY;							//last position that was clicked by mouse
	private Piece lastClicked = null;					//last piece that was clicked
	
	
	/**
	 * constructor
	 * @param b
	 * @param color
	 */
	public Human(Board b, int color) {
		super(color);
		setX(b.getX() - 1);
		setY(b.getY() - 1);
	}

	
	@Override
	/*
	 * (non-Javadoc)
	 * @see player.Player#startTurn(object.Board)
	 */
	public void startTurn(Board b) {
		activate();
		setX(b.getX() - 1);
		setY(b.getY() - 1);
	}
 
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see player.Player#endTurn(object.Board)
	 */
	public void endTurn(Board b) {
		deactivate();
		deselectPiece(b);
		b.nextTurn();
	}
	
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see player.Player#update(object.Board)
	 */
	public void update(Board b) {
		if (active) {					//if it is this player's turn
			
			Point p = getLastPoint (b);			//make a new point which is where the user clicked last
			
			if (p != null) {
				Piece piece = b.getPiece(p.getX(), p.getY());				
				if (piece != null && piece.COLOR == this.COLOR) {		//if the piece at that point is not null, select it to move it
					lastClicked = b.getPiece(p.getX(), p.getY());
					
					if (lastClicked != null) {
						b.moveSpaces = lastClicked.getMoves(b);			//set the board's move spaces to the piece's moves
						return;
					}
				}
				
				if (lastClicked != null)
				for (Move m : lastClicked.getMoves(b))					//for all the lastClicked piece's moves
					if (p.getX() == m.getX() && p.getY() == m.getY()) {		//if a movable space is clicked, move there and end turn
						m.move(b);
						endTurn(b);			
						return;
					}
			}
			
		}
	}
	
	
	/**
	 * return the last point the user clicked
	 * @param b
	 * @return
	 */
	private Point getLastPoint (Board b) {
		if (lastX >= b.getX() && 
			lastX <= b.getX() + b.getWidth() &&
			lastY >= b.getY() &&
			lastY <= b.getY() + b.getHeight()) {
			/*
			 * if the last position the mouse clicked was inside the board's boundaries
			 */
			int posX = (int) ((lastX + b.getX()) / b.getScale());
			int posY = (int) ((lastY + b.getY()) / b.getScale());
			
			return new Point (posX, posY);			//return the grid point (0 - 7, 0 - 7)
		}
		return null;
	}
	
	
	/**
	 * deselect the currently selected piece
	 * @param b
	 */
	private void deselectPiece (Board b) {
		lastClicked = null;
		b.moveSpaces = null;
	}
	
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see player.Player#mouseEvent(java.awt.event.MouseEvent)
	 */
	public void mouseEvent (MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			//set the lastX and lastY to the position clicked
			setX(e.getX());
			setY(e.getY());
		}
	}

	
	private void setX(int x) {
		lastX = x;
	}
	
	private void setY(int y) {
		lastY = y;
	}
	
}
