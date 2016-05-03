package gui;

import game.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface GUI {
	
	public void mouseInput(Game g, MouseEvent e);			//send mouse input to the object
	public void keyInput (Game g, KeyEvent e);				//send keyboard input to the object
	public void update(Game g);								//update the object
	public void render(Graphics g);							//render the object
	
	/**
	 * dim the screen to the value specified (0 - 1, 0 being the dimmest)
	 * @param g
	 * @param g2d
	 * @param alpha
	 */
	public static void dimScreen (Game g, Graphics2D g2d, float alpha) {
		g2d.setColor(new Color (0, 0, 0, alpha));
		g2d.fillRect(0, 0, g.getWidth(), g.getHeight());
	}
}
