package io.github.himanshusajwan911.sudokuserver.dto;

import io.github.himanshusajwan911.sudokuserver.model.SudokuGame.SudokuGameStatus;
import io.github.himanshusajwan911.sudokuserver.service.SudokuService.Level;

public class SudokuGameDTO {

	private int boardSize;
	
	private Level level;
	
	private int playerCount;

	private int playerLimit;

	private SudokuGameStatus status;
	
	private String gameId;
	
	private int[][] board;

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public int getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	public int getPlayerLimit() {
		return playerLimit;
	}

	public void setPlayerLimit(int playerLimit) {
		this.playerLimit = playerLimit;
	}

	public SudokuGameStatus getStatus() {
		return status;
	}

	public void setStatus(SudokuGameStatus status) {
		this.status = status;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}
	
}
