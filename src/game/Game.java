package game;
import gui.GUI;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import object.Board;


/**
 * Chess game for bonus marks
 * David Hospital
 * ISC 4U
 * January 21, 2015
 * 
 * Fully functional chess game with the option of playing against
 * another player or an AI. complete with pause menu (esc) to restart the game with 1 or 2 players
 * AI is designed to take the opponents pieces whenever it can and is very aggressive.
 * When a player is checkmate or stalemate, the game will end and a score screen will be displayed,
 * indicating who won.
 */



public class Game extends JPanel implements MouseListener, KeyListener {
	
	
	private int width, height;											//width and height of the screen (includes the window border)
	private Board board;												//board which is played on
	private ArrayList<GUI> interfaces = new ArrayList<GUI>();			//list of all GUI objects to be rendered / updated / receive inputs
	private ArrayList<GUI> add = new ArrayList<GUI>();					//list of all GUI objects to be added to interfaces
	private ArrayList<GUI> remove = new ArrayList<GUI>();				//list of all GUI objects to be removed from interfaces
	
	
	
	/**
	 * Constructor
	 * @param width of the screen										
	 * @param height of the screen
	 */
	private Game (int width, int height) {
		this.setWidth(width);
		this.setHeight(height);
		
		addMouseListener (this);										//allow this to receive inputs from the mouse
		addKeyListener (this);											//allow this to receive inputs from the keyboard
		this.setFocusable(true);										
		
		Resources.loadImages();											//load all images
		Resources.loadFonts();											//load all fonts
		initObjects ();													//add the board to interfaces
		repaint ();														//start game loop (calls paintComponent(Graphics g) )
	}
	
	
	
	/**
	 * add all current GUIs to interfaces
	 */
	private void initObjects () {
		board = new Board (this, 0, 0, Math.min(getHeight(), getWidth()) / 8);
		interfaces.add (board);
	}
	
	
	
	/**
	 * add GUI g to 'add', so it can be added to interfaces later
	 * @param g
	 */
	public void addGUI (GUI g) {
		add.add(g);
	}
	
	
	
	/**
	 * add GUI g to 'remove', so it can be removed from interfaces later
	 * @param g
	 */
	public void removeGUI (GUI g) {
		remove.add(g);
	}
	
	
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		
		//update the GUI which is last in the list (on top graphically)
		interfaces.get(interfaces.size() - 1).update(this);
		
		//render all GUIs
		for (GUI o : interfaces) {
			o.render (g);
		}
		
		//add all GUIs from 'add' into interfaces
		for (GUI o : add) {
			interfaces.add(o);
		}
		
		//remove all GUIs in 'remove' from interfaces
		for (GUI o : remove) {
			interfaces.remove(o);
		}
		
		//clear 'add' and 'remove'
		add.clear();
		remove.clear();
		
		//recall this method
		repaint ();
	}
	
	/**
	 * main method
	 * initialize the window, make a new Game object and add it to the window
	 * @param args
	 */
	public static void main (String [] args) {
		int width = 600, height = 600;
		
		Game g = new Game (width, height);
		JFrame jf = new JFrame ();
		jf.setTitle("Chess");
		jf.setSize(width + 6, height + 29);								//accounts for window border
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setResizable(false);
		jf.add(g);	
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		//check which button is pressed
		switch (e.getButton()) {
		case MouseEvent.BUTTON1: 
			//call the mouseInput method for the last (rendered on top) GUI in the list
			interfaces.get(interfaces.size() - 1).mouseInput(this, e);
			break;
		}		
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		interfaces.get(interfaces.size() - 1).keyInput(this, e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Board getBoard () {
		return this.board;
	}
	
	private static final long serialVersionUID = 1L;
	
}
