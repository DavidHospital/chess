package object.piece;

import java.util.ArrayList;

import game.Resources;
import object.Board;
import object.BoardState;

public class Knight extends Piece {

	public Knight(int color, int x, int y) {
		super(color, x, y);
		image = Resources.getImage("knight" + color);
	}

	public Knight(Piece p) {
		super(p);
	}
	
	@Override
	public Move[] getMoves(Board b) {
		ArrayList<Move> moves = new ArrayList<Move>();
		for (Move m : getPossibleMoves(b)) {
			if (checkMove (b, m))
				moves.add(m);
		}
		Move [] arrayMoves = new Move [moves.size()];
		for (int i = 0 ; i < moves.size() ; i ++) {
			arrayMoves[i] = moves.get(i);
		}
		return arrayMoves;
	}

	@Override
	public Move[] getPossibleMoves(Board b) {
		Move [] moves = {
			new Move (this, x - 1, y - 2),
			new Move (this, x + 1, y - 2),
			new Move (this, x + 2, y - 1),
			new Move (this, x - 2, y - 1),
			new Move (this, x - 1, y + 2),
			new Move (this, x + 1, y + 2),
			new Move (this, x + 2, y + 1),
			new Move (this, x - 2, y + 1),
		};
		return moves;
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
	public Piece copy() {
		return new Knight (this);
	}

	@Override
	public int getScore() {
		return 3;
	}
	
}
