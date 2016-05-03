package object.piece;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.Board;

public class CastleMove extends Move {

	private int x2, y2;						//x and y positions of the second piece to be moved (first piece is stored in Move)
	private Piece p2;						//second piece to be moved
	
	
	/**
	 * constructor
	 * @param p1
	 * @param p2
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public CastleMove(King p1, Piece p2, int x1, int y1, int x2, int y2) {
		super(p1, x1, y1);
		this.p2 = p2;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	
	/**
	 * move both pieces
	 */
	public void move (Board b) {
		getP1().updatePos(getX1(), getY1(), b);
		getP2().updatePos(getX2(), getY2(), b);
		getP1().hasMoved = true;
		getP2().hasMoved = true;
	}


	/**
	 * render the move
	 */
	public void render (Board b, Graphics g) {
		BufferedImage image = getP1().image;
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(AlphaComposite.SrcOver.derive(0.4f));			//transparency
		g2d.setColor(new Color(255, 0, 0, 255));						//red tile because it is a special move
		g2d.fillRect((int) (getX1() * b.getScale()), (int) (getY1() * b.getScale()), (int) (b.getScale()), (int) (b.getScale()));
		g2d.drawImage(image, (int) (getX1() * b.getScale()), (int) (getY1() * b.getScale()), (int) (b.getScale()), (int) (b.getScale()), null);
	}
	
	public int getX1 () {
		return x;
	}
	
	public int getY1 () {
		return y;
	}
	
	public int getX2 () {
		return x2;
	}
	
	public int getY2 () {
		return y2;
	}
	
	public Piece getP1 () {
		return p;
	}
	
	public Piece getP2 () {
		return p2;
	}

}
