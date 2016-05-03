package object.piece;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import object.Board;


/**
 * No documentation / comments for the specific pieces as they are all very similar
 */




public abstract class Piece {

	public final int COLOR;							//color of the piece
	public BufferedImage image;						//image of the piece
	protected int x, y;								//grid position of the piece
	public boolean hasMoved;						//has the piece moved?
	
	
	/**
	 * constructor
	 * @param color
	 * @param x
	 * @param y
	 */
	public Piece (int color, int x, int y) {
		COLOR = color;
		this.x = x;
		this.y = y;
		hasMoved = false;
	}
	
	
	/**
	 * constructor to copy pieces
	 * @param p
	 */
	public Piece (Piece p) {
		this.COLOR = p.COLOR;
		this.x = p.getX();
		this.y = p.getY();
		this.hasMoved = false;
	}
	
	public abstract int getScore ();						//return the piece's score
	public abstract Piece copy();							//return a copy of the piece
	public abstract Move[] getMoves(Board b);				//return all moves for said piece
	public abstract Move [] getPossibleMoves (Board b);		//return all places the piece is giving check to
	protected abstract boolean checkMove (Board b, Move m);	//check if a possible move is valid
	
	
	/**
	 * return all horizontal moves for a piece (rook, queen)
	 * @param b
	 * @return
	 */
	protected Move[] getHorizontalMoves (Board b) {
		ArrayList<Move> moves = new ArrayList<Move>();
		int tempX = this.getX(), tempY = this.getY();
		
		//for both directions (left, right)
		for (int i = -1; i < 2; i += 2) {
			
			//keep increasing to the left/right unless the space is obstructed by another piece
			while (tempX >= 0 && tempX <= 7) {
				tempX += i;
				Piece p = b.getPiece (tempX, tempY);
				if (p == null) {
					moves.add(new Move (this, tempX, tempY));
				} else if (p != null && p.COLOR != this.COLOR) {
					moves.add(new Move (this, tempX, tempY));				//add the move
					break;
				} else {
					break;
				}
			}
			tempX = this.getX();
		}
		
		//turn the list into an array
		Move[] arrayMoves = new Move[moves.size()];
		for (int i = 0; i < moves.size(); i ++) {
			arrayMoves[i] = moves.get(i);
		}
		return arrayMoves;
	}
	
	
	/**
	 * return all vertical moves for the piece (rook, queen)
	 * @param b
	 * @return
	 */
	protected Move[] getVerticalMoves (Board b) {
		ArrayList<Move> moves = new ArrayList<Move>();
		int tempX = this.getX(), tempY = this.getY();
		for (int i = -1; i < 2; i += 2) {
			
			//keep increasing to the up/down unless the space is obstructed by another piece
			while (tempY >= 0 && tempY <= 7) {
				tempY += i;
				Piece p = b.getPiece (tempX, tempY);
				if (p == null) {
					moves.add(new Move (this, tempX, tempY));
				} else if (p != null && p.COLOR != this.COLOR) {
					moves.add(new Move (this, tempX, tempY));			//add the move
					break;
				} else {
					break;
				}
		
			}
			tempY = this.getY();
		}
		
		//turn the list into an array
		Move[] arrayMoves = new Move[moves.size()];
		for (int i = 0; i < moves.size(); i ++) {
			arrayMoves[i] = moves.get(i);
		}
		return arrayMoves;
	}
	
	
	/**
	 * return all diagonal moves for the piece
	 * @param b
	 * @return
	 */
	protected Move[] getDiagonalMoves (Board b) {
		ArrayList<Move> moves = new ArrayList<Move>();
		int tempX = this.getX(), tempY = this.getY();
		
		//up left, up right, down left, down right
		for (int i = -1; i < 2; i += 2) {
			for (int j = -1; j < 2; j += 2) {
				
				//keep increasing to all diagonal directions unless the space is obstructed by another piece
				while (tempY >= 0 && tempY <= 7 
					&& tempX >= 0 && tempX <= 7) {
					tempY += i;
					tempX += j;
					Piece p = b.getPiece (tempX, tempY);
					if (p == null) {
						moves.add(new Move (this, tempX, tempY));
					} else if (p != null && p.COLOR != this.COLOR) {
						moves.add(new Move (this, tempX, tempY));					//add the move
						break;
					} else {
						break;
					}
			
				}
				tempX = this.getX();
				tempY = this.getY();
			}
		}
		
		//turn the list into an array
		Move[] arrayMoves = new Move[moves.size()];
		for (int i = 0; i < moves.size(); i ++) {
			arrayMoves[i] = moves.get(i);
		}
		return arrayMoves;
	}
	
	
	/**
	 * ren
	 * @param b
	 * @param g
	 */
	public void render (Board b, Graphics g) {
		g.drawImage(image, (int) (x * b.getScale()), (int) (y * b.getScale()), (int) (b.getScale()), (int) (b.getScale()), null);
	}
	
	
	/**
	 * update the position of the piece
	 * @param x
	 * @param y
	 * @param b
	 */
	public void updatePos (int x, int y, Board b) {
		setX (x);
		setY (y);
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
