package io.github.himanshusajwan911.sudokuserver.model;

public class GameBoard {

	private int[][] board;
	
	private int[][] solution;
	
	public GameBoard(int[][] board, int[][] solution) {
		super();
		this.board = board;
		this.solution = solution;
	}

	public int[][] getBoard() {
		return board;
	}

	public int[][] getSolution() {
		return solution;
	}
	
}
