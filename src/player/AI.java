package player;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import object.Board;
import object.BoardState;
import object.piece.Move;
import object.piece.Piece;

public class AI extends Player {

	
	/**
	 * constructor
	 * @param color
	 */
	public AI (int color) {
		super(color);
	}
	
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see player.Player#startTurn(object.Board)
	 */
	public void startTurn(Board b) {
		activate();
	}

	
	@Override
	/*
	 * (non-Javadoc)
	 * @see player.Player#endTurn(object.Board)
	 */
	public void endTurn(Board b) {
		deactivate();
		b.nextTurn();
	}
	
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see player.Player#update(object.Board)
	 */
	public void update(Board b) {
		if (active) {					//if it is the AI's turn
			BoardState bs = new BoardState (b);
			Move m = selectMove (bs, this.COLOR, 4);			//select a move
			
			
			//set the move's piece to the actual piece on the board,
			//rather than the corresponding one from the boardstate
			m.setP(b.getPiece(m.getP().getX(), m.getP().getY()));	
			m.move(b);			//make the move on the real board
			endTurn(b);			//end the turn
		}
	}
	
	
	/**
	 * select the move to be made (Actually AI part of the code)
	 * @param b
	 * @param color
	 * @return
	 */
	private Move selectMove (BoardState b, int color, int round) {

		//hashmap for all moves and scores of each move to be stored
		HashMap<Move, Integer> moves = new HashMap<Move, Integer>();
		int topScore = -100;		//calculates the highest score out of the list of moves
		
		//for all possible moves to be made of color
		for (Move m : getMoves(b, color)) {
			BoardState bs = new BoardState (b, m);			//new virtual board to test the move
			int moveScore;
			moveScore = boardScore (bs, color);			//score of the new board after the move has been made
			moves.put(m, moveScore);						//add the move along with its score to the board
			if (moveScore > topScore)						//if the score is greater than topScore, set topScore to the current score
				topScore = moveScore;
		}
		
		//list for the top scoring moves to be stored in 
		ArrayList<Move> highestMoves = new ArrayList<Move>();
		
		//for all moves in the hashmap
		for (Move m : moves.keySet()) {
			//if the score of m is equal to topScore, add it to highestMoves
			if (moves.get(m) == topScore) {
				highestMoves.add(m);
			}
		}
		
		
		//select a random move from the list of top scoring moves
		Random r = new Random ();
		Move selected = highestMoves.get(r.nextInt(highestMoves.size()));
		return selected;
	}
	
	
	/**
	 * calculate the score of the board based off 
	 * the sums of the individual piece scores.
	 * @param b
	 * @param color
	 * @return
	 */
	private int boardScore (BoardState b, int color) {
		//array to store the total score of each color
		int [] scores = {0, 0};
		for (Piece p : b.pieces) {
			scores[p.COLOR] += p.getScore ();		//add the current piece's score to the index of its color
		}
		
		//sum of color subtract the sum of the other color is the score
		int difference = scores[color] - scores[(int) ((color - 0.5) * -1 + 0.5)];
		return difference;
	}
	
	
	/**
	 * return all possible moves
	 * @param b
	 * @param color
	 * @return
	 */
	private ArrayList<Move> getMoves (BoardState b, int color) {
		ArrayList<Move> moves = new ArrayList<Move>();
		
		//for all pieces of that color
		for (Piece p : b.pieces) {
			if (p.COLOR == color) {
				for (Move m : p.getMoves(b)) {
					moves.add(m);			//add all their moves to the list
				}
			}
		}
		return moves;
	}
	
	
	
	@Override
	public void mouseEvent (MouseEvent e) {}
	
}
