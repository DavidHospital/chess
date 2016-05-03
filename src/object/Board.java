package object;
import game.Game;
import game.Resources;
import gui.EndGameGUI;
import gui.GUI;
import gui.PauseGUI;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import object.piece.*;
import player.*;


public class Board implements GUI {
	
	protected int x, y;														//position of the top left corner of the board
	private double scale;													//width and height of each tile on the board
	public ArrayList<Piece> pieces = new ArrayList<Piece>();				//list of all pieces currently on the board
	public int turnPlayer;													//current turn player (0 for white, 1 for black)
	
	public Game g;															//game object this is stored in
	
	public Player [] players = {											//array of all players (Human or AI)
		new Human (this, 0),	
		new AI (1)
	};
	
	public Move [] moveSpaces = null;										//array of all moves to be rendered to the board
	
	BufferedImage tile [] = new BufferedImage [2];							//images of the tiles to be rendered on the board
	
	
	/**
	 * Constructor, initialize the board, set turnPlayer to white
	 * @param g
	 * @param x
	 * @param y
	 * @param scale
	 */
	public Board (Game g, int x, int y, int scale) {
		this.x = x;
		this.y = y;
		this.scale = scale;
		this.g = g;
		turnPlayer = 0;
		players[turnPlayer].startTurn(this);
		
		//load images from Resources
		tile [0] = Resources.getImage("redtile");
		tile [1] = Resources.getImage("whitetile");
		
		//add pieces to the board
		initPieces ();
	}
	
	public Board () {}
	
	
	/**
	 * restart the game
	 * @param players : replace old players
	 */
	public void restart (Player [] players) {
		this.players = players;
		turnPlayer = 0;
		moveSpaces = null;
		initPieces ();
		players[turnPlayer].startTurn(this);
	}
	
	@Override
	/**
	 * render all graphics to the board
	 */
	public void render(Graphics g) {
		//draw the board
		for (int x = 0; x < 8; x ++) {
			for (int y = 0; y < 8; y ++) {
				g.drawImage (tile [(x + y) % 2], (int) (x * scale), (int) (y * scale), (int) (scale), (int) (scale), null);
			}
		}
		
		//draw all the pieces
		for (Piece p : pieces) {
			p.render(this, g);
		}
		
		//draw the possible movable spaces
		if (moveSpaces != null) {
			for (Move m : moveSpaces) {
				m.render (this, g);
			}
		}
	}
	
	
	/**
	 * all new pieces to the board as if starting the game over
	 */
	private void initPieces () {
		pieces.clear();						//clear the board first
		
		
		//black pieces
		pieces.add(new Rook (1, 0, 0));
		pieces.add(new Knight (1, 1, 0));
		pieces.add(new Bishop (1, 2, 0));
		pieces.add(new King (1, 3, 0));
		pieces.add(new Queen (1, 4, 0));
		pieces.add(new Bishop (1, 5, 0));
		pieces.add(new Knight (1, 6, 0));
		pieces.add(new Rook (1, 7, 0));
		pieces.add(new Pawn (1, 0, 1));
		pieces.add(new Pawn (1, 1, 1));
		pieces.add(new Pawn (1, 2, 1));
		pieces.add(new Pawn (1, 3, 1));
		pieces.add(new Pawn (1, 4, 1));
		pieces.add(new Pawn (1, 5, 1));
		pieces.add(new Pawn (1, 6, 1));
		pieces.add(new Pawn (1, 7, 1));
		
		//white pieces
		pieces.add(new Rook (0, 0, 7));
		pieces.add(new Knight (0, 1, 7));
		pieces.add(new Bishop (0, 2, 7));
		pieces.add(new King (0, 3, 7));
		pieces.add(new Queen (0, 4, 7));
		pieces.add(new Bishop (0, 5, 7));
		pieces.add(new Knight (0, 6, 7));
		pieces.add(new Rook (0, 7, 7));
		pieces.add(new Pawn (0, 0, 6));
		pieces.add(new Pawn (0, 1, 6));
		pieces.add(new Pawn (0, 2, 6));
		pieces.add(new Pawn (0, 3, 6));
		pieces.add(new Pawn (0, 4, 6));
		pieces.add(new Pawn (0, 5, 6));
		pieces.add(new Pawn (0, 6, 6));
		pieces.add(new Pawn (0, 7, 6));
	}
	
	
	/**
	 * get the piece at a certain position on the board's grid
	 * @param x
	 * @param y
	 * @return : if no piece exists there, return null
	 */
	public Piece getPiece (int x, int y) {
		for (Piece p : pieces) {
			if (p.getX() == x && p.getY() == y) {
				return p;
			}
		}
		return null;
	}
	
	
	/**
	 * start the next turn
	 */
	public void nextTurn () {
		turnPlayer = (int) ((turnPlayer - 0.5) * -1 + 0.5);				// if 0, make 1. if 1, make 0
		moveSpaces = null;												// remove all moveSpaces
		if (!checkEndGame()) {											// if a player is not in checkmate or stalemate, start the next player's turn
			players[turnPlayer].startTurn(this);
		}
	}
	
	
	/**
	 * check if the game should be over
	 * @return : true if it is over
	 */
	private boolean checkEndGame() {								
		for (int i = 0; i < 2; i ++) {						//check both colors (0, 1) for checkmate and stalemate
			if (isCheckMate(i)) {							
				EndGameGUI gg = new EndGameGUI (g, i, true);		//add a new EndGameGUI to g's interface list
				g.addGUI(gg);
				return true;
			} else if (isStaleMate(i)) {
				EndGameGUI gg = new EndGameGUI (g, i, false);		//add a new EndGameGUI to g's interface list
				g.addGUI(gg);
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * is the king of color in check?
	 */
	public boolean check (int color) {				
		int otherColor = (int) (((color - 0.5) * -1) + 0.5);
		King k = getKing(color);
		for (Piece p : this.pieces) {											//for all pieces on the board				
			if (p.COLOR == otherColor && p.getPossibleMoves(this) != null) {	//if p is of the opposite color
				for (Move m : p.getPossibleMoves(this)) {						//for all spots that p is checking
					if (k.getX() == m.getX() && k.getY() == m.getY()) {			//if the king is in one of those spots, it is in check
						return true;	//in check
					}
				}
			}
		}
		return false;		//not in check
	}
	
	
	/**
	 * return the piece that represents the king from pieces
	 * @param color
	 * @return
	 */
	private King getKing (int color) {
		for (Piece p : this.pieces) {
			if (p instanceof King && p.COLOR == color) {
				return (King) p;
			}
		}
		return null;
	}
	

	/**
	 * is the player of a color in checkmate?
	 * @param color
	 * @return
	 */
	public boolean isCheckMate (int color) {
		if (check(color)) {					//if the player is in check
			for (Piece p : pieces) {				//for all pieces of the same color
				if (p.COLOR == color) {
					if (p.getMoves(this).length > 0) {			//if none of them have any moves, checkmate
						return false;		//statemate
					}
				}
			}
			return true;		//checkmate
		}
		return false;		//if not in check, not checkmate
	}
	
	
	/**
	 * if the player of a color in stalemate?
	 * @param color
	 * @return
	 */
	public boolean isStaleMate (int color) {
		
		//same as isCheckMate, but the player has to not be in check
		
		if (!check(color)) {
			for (Piece p : pieces) {
				if (p.COLOR == color) {
					if (p.getMoves(this).length > 0) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}
	
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see gui.GUI#mouseInput(game.Game, java.awt.event.MouseEvent)
	 */
	public void mouseInput (Game g, MouseEvent e) {
		players[turnPlayer].mouseEvent(e);				//send input to the player whose turn it is
	}
	
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see gui.GUI#keyInput(game.Game, java.awt.event.KeyEvent)
	 */
	public void keyInput (Game g, KeyEvent e) {
		int key = e.getKeyCode();
		
		//if the user presses escape, bring up the pause menu
		switch (key) {
		case KeyEvent.VK_ESCAPE:
			g.addGUI(new PauseGUI (g));			//add a new PauseGUI to g's interfaces list
			break;
		}
	}

	
	@Override 
	/*
	 * (non-Javadoc)
	 * @see gui.GUI#update(game.Game)
	 */
	public void update (Game g) {
		players[turnPlayer].update(this);
	}
	
	public double getScale() {
		return scale;
	}

	public int getWidth() {
		return (int) (scale * 8);
	}
	
	public int getHeight() {
		return (int) (scale * 8);
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return x;
	}

	
}
