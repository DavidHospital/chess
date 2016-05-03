package gui;

import game.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import object.Board;
import object.piece.*;

public class PawnSelectPiece implements GUI{

	private int x, y, width, height;									//position and size of the object
	double scaleX, scaleY;												//scale of the pieces rendered
	private boolean terminate;											//if true, close GUI
	Piece [] pieces = new Piece [4];									//array of pieces to be rendered
	Board b;															//board which this belongs
	Game g;																//game which this belongs
	Pawn p;																//pawn which activated this
	
	
	/**
	 * constructor
	 * @param g
	 * @param b
	 * @param p
	 * @param color
	 */
	public PawnSelectPiece (Game g, Board b, Pawn p, int color) {
		this.x = g.getX();
		this.y = g.getHeight() * 3 / 8;
		this.width = g.getWidth();
		this.height = g.getHeight() * 2 / 8;
		this.b = b;
		this.g = g;
		this.p = p;
		
		
		// four pieces, therefore scaleX is four times less than the width
		scaleX = width/4;
		scaleY = height;
		
		//initialize the pieces
		pieces[0] = new Queen (color, (int) (x + (scaleX * 0)), y);
		pieces[1] = new Bishop (color, (int) (x + (scaleX * 1)), y);
		pieces[2] = new Knight (color, (int) (x + (scaleX * 2)), y);
		pieces[3] = new Rook (color, (int) (x + (scaleX * 3)), y);
		
	}
	
	
	/**
	 * once the user has selected a piece, terminate and return the piece selected
	 * @param x
	 * @return
	 */
	private Piece selectNewPiece (int x) {
		terminate = true;
		return pieces [x];
	}
	
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see gui.GUI#mouseInput(game.Game, java.awt.event.MouseEvent)
	 */
	public void mouseInput(Game g, MouseEvent e) {
		int eventX = e.getX();
		int eventY = e.getY();
		
		//determine which piece was clicked on, and transform the pawn into it
		if (eventX >= x && eventX <= x + width && eventY >= y && eventY <= y + height) {
			int posX = (int) ((eventX - x) / scaleX);
			Piece newPiece = selectNewPiece (posX);
			newPiece.updatePos(p.getX(), p.getY(), b);
			p.transform(newPiece, b);
		}
	}
	
	
	@Override
	public void keyInput (Game g, KeyEvent e) {}

	
	@Override
	/*
	 * (non-Javadoc)
	 * @see gui.GUI#update(game.Game)
	 */
	public void update(Game g) {
		if (terminate) {
			g.removeGUI(this);		//remove this GUI from g's list
		}
	}

	
	@Override
	/*
	 * (non-Javadoc)
	 * @see gui.GUI#render(java.awt.Graphics)
	 */
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		GUI.dimScreen(this.g, g2d, 0.5f);
		
		//draw the stripe across the screen
		g2d.setColor(new Color (0.9f, 0.8f, 0.6f, 1));
		g2d.fillRect(x, y, width, height);
		
		//draw each piece
		for (int i = 0; i < pieces.length; i ++) {
			g2d.drawImage(pieces[i].image, (int) ((x + (i * scaleX))), (int) (y), (int) scaleX, (int) scaleY, null);
		}
	}
}
