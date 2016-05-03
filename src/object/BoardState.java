package object;

import object.piece.*;

public class BoardState extends Board {
	
	
	/**
	 * Constructor
	 * @param b
	 * @param m
	 */
	public BoardState (Board b, Move m) {
		super();
		for (Piece p : b.pieces) {		//make copies of all pieces in b and add them to a new list
			pieces.add(p.copy());
		}
		pieces.remove(getPiece(m.getX(), m.getY()));					//remove the piece the move's position
		Piece movePiece = getPiece (m.getP().getX(), m.getP().getY());	//get the piece in the move	
		Move copy = new Move (movePiece, m.getX(), m.getY());			//copy the move
		copy.move (this);												//move the copied piece
	}	

	
	/**
	 * constructor
	 * @param b
	 */
	public BoardState(Board b) {
		super();
		for (Piece p : b.pieces) {
			pieces.add(p.copy());
		}
	}

	
	/**
	 * is the king of color in check?
	 */
	public boolean check (int color) {	
		
		//see check (int color) in the Board class
		
		int otherColor = (int) (((color - 0.5) * -1) + 0.5);
		King k = getKing(color);
		for (Piece p : this.pieces) {					
			if (p.COLOR == otherColor) {
				if (p.getPossibleMoves(this) != null)
				for (Move m : p.getPossibleMoves(this)) {
					if (k.getX() == m.getX() && k.getY() == m.getY()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private King getKing (int color) {
		
		//see getKing (int color) in the Board class
		
		for (Piece p : this.pieces) {
			if (p instanceof King && p.COLOR == color) {
				return (King) p;
			}
		}
		return null;
	}
	
	public Piece getPiece (int x, int y) {
		
		//see getPiece (int x, int y) in the Board class
		
		for (Piece p : this.pieces) {
			if (p.getX() == x && p.getY() == y) {
				return p;
			}
		}
		return null;
	}
	
}
