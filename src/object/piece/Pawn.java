package object.piece;

import java.util.ArrayList;

import object.Board;
import object.BoardState;
import player.AI;
import player.Human;
import game.Resources;
import gui.PawnSelectPiece;

public class Pawn extends Piece {

	public int direction = 0;
	
	public Pawn(int color, int x, int y) {
		super(color, x, y);
		image = Resources.getImage("pawn" + color);
		direction = (int) (COLOR * 2 - 1);
	}	
	
	public Pawn(Piece p) {
		super(p);
		direction = (int) (COLOR * 2 - 1);
		hasMoved = p.hasMoved;
	}
	
	
	@Override
	public Move[] getMoves(Board b) {
		ArrayList<Move> moves = new ArrayList<Move>();
		for (Move m : getPossibleMoves(b)) {
			Piece p = b.getPiece(m.getX(), m.getY());
			if (p != null && p.COLOR != this.COLOR && checkMove(b, m)) {
				moves.add(m);
			}
		}
		if (checkForwardMove(b, 2)) {
			moves.add(new Move (this, getX(), getY() + direction * 2));
		}
		if (checkForwardMove(b, 1)) {
			moves.add(new Move (this, getX(), getY() + direction));
		}
		
		Move [] arrayMoves = new Move [moves.size()];
		for (int i = 0; i < arrayMoves.length; i ++) {
			arrayMoves[i] = moves.get(i);
		}
		return arrayMoves;
	}
	
	
	@Override
	public Move[] getPossibleMoves(Board b) {
		Move [] moves = {
			new Move (this, x - 1, y + direction),
			new Move (this, x + 1, y + direction),
		};
		return moves;
	}


	public void updatePos (int x, int y, Board b) {
		setX (x);
		setY (y);
		hasMoved = true;
		if (y == this.COLOR * 7 && b instanceof Board) {
			if (b.players[this.COLOR] instanceof Human) {
				if (b.g != null)
					b.g.addGUI(new PawnSelectPiece (b.g, b, this, this.COLOR));
			} else if (b.players[this.COLOR] instanceof AI) {
				transform (new Queen (this.COLOR, getX(), getY()), b);
			}
		}
	}
	
	@Override
	protected boolean checkMove(Board b, Move m) {
		if (m.getX() < 0 || m.getX() > 7 || m.getY() < 0 || m.getY() > 7) 
			return false;
		BoardState bs = new BoardState (b, m);
		if (bs.check(COLOR))
			return false;
		return true;
	}
	
	
	private boolean checkForwardMove (Board b, int distance) {
		Move m = new Move (this, getX(), getY() + direction * distance);
		if (b.getPiece (m.getX(), m.getY()) != null)
			return false;
		if (distance == 2)
			if (hasMoved || b.getPiece (m.getX(), m.getY() - direction) != null)
				return false;
		BoardState bs = new BoardState (b, m);
		if (bs.check(COLOR))
			return false;
		return true;
	}
	
	public void transform (Piece p, Board b) {
		b.pieces.add(p);
		b.pieces.remove(this);
	}

	@Override
	public Piece copy() {
		return new Pawn (this);
	}
	
	@Override
	public int getScore() {
		return 1;
	}
}
