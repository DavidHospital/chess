package object.piece;

import java.util.ArrayList;

import game.Resources;
import object.Board;
import object.BoardState;

public class King extends Piece {

	public King(int color, int x, int y) {
		super(color, x, y);
		image = Resources.getImage("king" + color);
		// TODO Auto-generated constructor stub
	}
	
	public King(Piece p) {
		super(p);
		this.hasMoved = p.hasMoved;
	}

	@Override
	public Move[] getMoves(Board b) {
		ArrayList<Move> moves = new ArrayList<Move>();
		
		for (Move m : getPossibleMoves(b)) {
			if (checkMove (b, m))
				moves.add(m);
		}
		
		
		//castling moves
		if (!hasMoved) {
			Piece p = b.getPiece(0, this.getY());			//get piece at position (rook) to the left
			if (p != null && !p.hasMoved) {					//if it hasn't moved
				boolean validCastle = true;
				for (int i = this.getX() - 1; i > 0; i --) {		//for all spaces between this and the rook
					if (b.getPiece(i, this.getY()) != null) {		//if it is not empty, can't castle
						validCastle = false;
					}	
					for (Piece piece : b.pieces) {				//for all pieces of the opposite color
						if (piece.COLOR != this.COLOR)
						for (Move m : piece.getPossibleMoves(b)) {			//no moves can be checking any of the spots between this and the rook
							if (m.getX() == i + 1 && m.getY() == this.getY())
								validCastle = false;
						}
					}
				}
				if (validCastle)
					moves.add(new CastleMove (this, p, this.getX() - 2, this.getY(), this.getX() - 1, this.getY()));
			}
			
			
			p = b.getPiece(7, this.getY());				//get piece at position (rook) to the right
			if (p != null && !p.hasMoved) {
				boolean validCastle = true;
				for (int i = this.getX() + 1; i < 7; i ++) {
					if (b.getPiece(i, this.getY()) != null) {
						validCastle = false;
					}
					for (Piece piece : b.pieces) {
						if (piece.COLOR != this.COLOR)
						for (Move m : piece.getPossibleMoves(b)) {
							if (m.getX() == i - 1 && m.getY() == this.getY())
								validCastle = false;
						}
					}
				}
				if (validCastle)
					moves.add(new CastleMove (this, p, this.getX() + 2, this.getY(), this.getX() + 1, this.getY()));
			}
		}		//end castling checking
		
		//convert the list of moves to an array
		Move [] arrayMoves = new Move [moves.size()];
		for (int i = 0 ; i < moves.size() ; i ++) {
			arrayMoves[i] = moves.get(i);
		}
		
		return arrayMoves;
	}

	@Override
	protected boolean checkMove(Board b, Move m) {
		if (m.getX() < 0 || m.getX() > 7 || m.getY() < 0 || m.getY() > 7) 
			return false;
		Piece p = b.getPiece(m.getX(), m.getY());
		if (p != null && p.COLOR == this.COLOR)
			return false;
		BoardState bs = new BoardState (b, m);
		if (bs.check(COLOR))
			return false;
		return true;
	}

	@Override
	public Move[] getPossibleMoves(Board b) {
		Move [] moves = {
			new Move (this, x - 1, y - 1),
			new Move (this, x, y - 1),
			new Move (this, x + 1, y - 1),
			new Move (this, x - 1, y),
			new Move (this, x + 1, y),
			new Move (this, x - 1, y + 1),
			new Move (this, x, y + 1),
			new Move (this, x + 1, y + 1)
		};
		return moves;
	}
	
	@Override
	public Piece copy() {
		return new King (this);
	}
	
	@Override
	public int getScore() {
		return 0;
	}

}
