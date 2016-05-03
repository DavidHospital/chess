package gui;

import game.Game;
import game.Resources;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class EndGameGUI implements GUI {

	Game g;																		//game which the GUI belongs to
	int color, x, y, width, height;												//screen positions
	String msg [] = new String [2];												//text to be displayed
	
	
	/**
	 * constructor
	 * @param g
	 * @param color
	 * @param checkMate
	 */
	public EndGameGUI (Game g, int color, boolean checkMate) {
		this.g = g;
		this.color = color;
		this.x = g.getX();
		this.y = g.getY();
		this.width = g.getWidth();
		this.height = g.getHeight();
		String colorStr = (color == 0 ? "Black" : "White");
		
		//set the text to be displayed depending on if the game is checkmate or stalemate
		if (checkMate) {
			this.msg [0] = "Checkmate!";
			this.msg [1] = colorStr + " player wins!";
		}
		else {
			this.msg [0] = "Stalemate!";
			this.msg [1] = "It's a draw!";
		}
	}
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see gui.GUI#mouseInput(game.Game, java.awt.event.MouseEvent)
	 */
	public void mouseInput(Game g, MouseEvent e) {
		//restart the game against the same player as before
		g.getBoard().restart(g.getBoard().players);
		g.removeGUI (this);
	}
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see gui.GUI#keyInput(game.Game, java.awt.event.KeyEvent)
	 */
	public void keyInput (Game g, KeyEvent e) {
		//restart the game against the same player as before
		g.getBoard().restart(g.getBoard().players);
		g.removeGUI(this);
	}

	@Override
	public void update(Game g) {}

	@Override
	/*
	 * (non-Javadoc)
	 * @see gui.GUI#render(java.awt.Graphics)
	 */
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		GUI.dimScreen (this.g, g2d, 0.5f);
		
		final float fontSize = 50f;
		Font font = Resources.getFont("Harrington").deriveFont(fontSize);
		

		//render both the texts in the middle of the screen
		for (int i = 0; i < msg.length; i ++) {
			Rectangle2D rect = font.getStringBounds(msg [i], g2d.getFontRenderContext());
			int xOffSet = (int) (rect.getWidth()/2); 
			int yOffSet = (int) (rect.getHeight()/2 - (fontSize * i));
			
			g2d.setFont(font);
			g2d.setColor(new Color (1f, 1f, 1f, 1f));
			g2d.drawString(msg[i], getMiddleX() - xOffSet, getMiddleY() - yOffSet);
		}
	}

	public int getX() {
		return x;
	}
	
	public int getY () {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight () {
		return height;
	}
	
	public int getMiddleX() {
		return (getWidth() / 2) + getX();
	}
	
	public int getMiddleY() {
		return (getHeight() / 2 + getY());
	}
}
