package gui.button;

import game.Resources;
import gui.PauseGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

public class Button {

	private String text;															// text to be displayed on the button
	private final int ACTION;														// the action to happen when the button is pressed
	private int x, y, width, height;												// size and position of the button
	
	PauseGUI gui;																	//GUI which this button exists

	
	/**
	 * constructor
	 * @param gui
	 * @param text
	 * @param ACTION
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Button (PauseGUI gui, String text, int ACTION, int x, int y, int width, int height) {
		this.text = text;
		this.ACTION = ACTION;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.gui = gui;
	}
	
	
	/**
	 * input for the button
	 * @param e
	 */
	public void mouseInput (MouseEvent e) {
		int button = e.getButton();
		
		//if the left button is pressed
		if (button == MouseEvent.BUTTON1) {
			
			//if the cursor is within the bounds of the button
			if (e.getX() >= this.getX() &&						
				e.getX() <= this.getX() + this.getWidth() &&
				e.getY() >= this.getY() &&
				e.getY() <= this.getY() + this.getHeight()) {
				
				//perform the action of ACTION located in ButtonAction
				ButtonAction.performAction(gui.g, gui, gui.g.getBoard(), this.ACTION);
				
			}
		}
	}
	
	
	/**
	 * render the button
	 * @param g2d
	 */
	public void render (Graphics2D g2d) {
		
		//create a Font object
		Font font = Resources.getFont("Demo_ConeriaScript").deriveFont(30f);
		
		//calculate the dimensions the text will take up on screen
		TextLayout tl = new TextLayout(text, font, g2d.getFontRenderContext());
		Rectangle2D rect = tl.getBounds();
		
		//calculate the offsets which the button has to be translated to render text in the center
		int xOffSet = (int) (rect.getWidth()/2); 
		int yOffSet = (int) (rect.getHeight()/2);
		
		//set the color of the button and draw it
		g2d.setColor(new Color (240, 214, 123, 200));
		g2d.fillRoundRect(getX(), getY(), getWidth(), getHeight(), 50, 50);
		
		//draw the text ontop of the button
		g2d.setFont(font);
		g2d.setColor(new Color (0.0f, 0.0f, 0.0f, 1.0f));
		g2d.drawString(text, getX() + getWidth()/2 - xOffSet, getY() + getHeight()/2 + yOffSet);
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getWidth () {
		return this.width;
	}
	
	public int getHeight () {
		return this.height;
	}
}
