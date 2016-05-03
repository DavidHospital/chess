package object.piece;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.Board;
import object.piece.Piece;

public class Move {
	
	protected int x;									//x co-ordinate to where the piece is to be moved
	protected int y;									//y co-ordinate to where the piece is to be moved
	protected Piece p;									//piece to be moved
	
	
	/**
	 * constructor
	 * @param p
	 * @param x
	 * @param y
	 */
	public Move (Piece p, int x, int y) {
		this.p = p;
		this.x = x;
		this.y = y;
	}
	
	
	/**
	 * move the piece
	 * @param b
	 */
	public void move (Board b) {
		Piece piece = b.getPiece (x, y);
		b.pieces.remove(piece);			//remove the piece that previously occupyed the position
		p.updatePos(x, y, b);
		p.hasMoved = true;
	}
	
	
	/**
	 * render the move on the grid (with faded pieces)
	 * @param b
	 * @param g
	 */
	public void render (Board b, Graphics g) {
		BufferedImage image = getP().image;
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(AlphaComposite.SrcOver.derive(0.4f));			//Transparency for the image
		g2d.drawImage(image, (int) (x * b.getScale()), (int) (y * b.getScale()), (int) (b.getScale()), (int) (b.getScale()), null);
		g2d.setComposite(AlphaComposite.SrcOver.derive(1f));
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Piece getP() {
		return p;
	}
	
	public void setP(Piece p) {
		this.p = p;
	}
	
}
