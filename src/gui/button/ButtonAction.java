package gui.button;

import object.Board;
import player.*;
import game.Game;
import gui.PauseGUI;

public class ButtonAction {

	public static final int CONTINUE = 0;								//continue the game
	public static final int NEW_HUMAN = 1;								//start a new game against a human
	public static final int NEW_AI = 2;									//start a new game against the AI
	public static final int EXIT = 3;									//close the program
	
	
	
	/**
	 * run the action method corresponding to the ACTION
	 * @param g
	 * @param gui
	 * @param b
	 * @param ACTION
	 */
	public static void performAction (Game g, PauseGUI gui, Board b, int ACTION) {
		switch (ACTION) {
		case CONTINUE:
			actionContinue(g, gui);
			break;
		case NEW_HUMAN:
			actionNewHuman(g, b, gui);
			break;
		case NEW_AI:
			actionNewAI(g, b, gui);
			break;
		case EXIT:
			actionExit();
			break;
		}
	}
	
	
	/**
	 * continue un-pause the menu
	 * @param g
	 * @param gui
	 */
	private static void actionContinue (Game g, PauseGUI gui) {
		gui.unPause(g);
	}
	
	
	/**
	 * new game vs human
	 * @param g
	 * @param b
	 * @param gui
	 */
	private static void actionNewHuman(Game g, Board b, PauseGUI gui) {
		gui.unPause(g);
		b.restart(new Player []{
			new Human (b, 0),
			new Human (b, 1)
		});
	}
	
	
	/**
	 * new game vs AI
	 * @param g
	 * @param b
	 * @param gui
	 */
	private static void actionNewAI (Game g, Board b, PauseGUI gui) {
		gui.unPause(g);
		b.restart(new Player []{
			new Human (b, 0),
			new AI (1)
		});
	}
	
	
	/**
	 * terminate the program
	 */
	private static void actionExit () {
		System.exit(0);
	}
}
