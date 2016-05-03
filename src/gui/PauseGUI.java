package gui;

import game.Game;
import gui.button.Button;
import gui.button.ButtonAction;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class PauseGUI implements GUI {

	public Game g;												// game which this belongs
	private Button [] buttons;									// array of buttons existing in this GUI
	
	
	/**
	 * constructor
	 * @param g
	 */
	public PauseGUI (Game g) {
		this.g = g;
		
		//initialize the buttons
		Button [] buttons = {
				new Button (this, "Continue", ButtonAction.CONTINUE, 50, 100, g.getWidth() - 100, 70),
				new Button (this, "1 Player", ButtonAction.NEW_AI, 50, 200, g.getWidth() - 100, 70),
				new Button (this, "2 Player", ButtonAction.NEW_HUMAN, 50, 300, g.getWidth() - 100, 70),
				new Button (this, "Exit", ButtonAction.EXIT, 50, 400, g.getWidth() - 100, 70)
		};
		this.buttons = buttons;
	}
	
	
	/**
	 * up-pause the game
	 * @param g
	 */
	public void unPause (Game g) {
		g.removeGUI(this);
	}
	
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see gui.GUI#mouseInput(game.Game, java.awt.event.MouseEvent)
	 */
	public void mouseInput(Game g, MouseEvent e) {
		for (Button b : buttons) {
			b.mouseInput(e);
		}
	}

	
	@Override
	/*
	 * (non-Javadoc)
	 * @see gui.GUI#keyInput(game.Game, java.awt.event.KeyEvent)
	 */
	public void keyInput(Game g, KeyEvent e) {
		int key = e.getKeyCode();
		
		switch (key) {
		//if the key pressed is escape, up-pause
		case KeyEvent.VK_ESCAPE:
			unPause(g);
			break;
		}
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
		
		//render all buttons
		for (Button b : buttons) {
			b.render(g2d);
		}
	}

}
