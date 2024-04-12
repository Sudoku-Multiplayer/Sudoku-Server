package io.github.himanshusajwan911.sudokuserver.dto;

import io.github.himanshusajwan911.sudokuserver.model.Player;
import io.github.himanshusajwan911.sudokuserver.model.SudokuGame.SudokuGameStatus;
import io.github.himanshusajwan911.sudokuserver.service.SudokuService.Level;

public class SudokuGameDTO {

	private String gameName;

	private Player hostPlayer;

	private int boardSize;

	private Level level;

	private int playerCount;

	private int playerLimit;

	private SudokuGameStatus status;

	private String gameId;

	private int[][] initialBoard;

	private int[][] currentBoard;

	private int[][] solution;

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public Player getHostPlayer() {
		return hostPlayer;
	}

	public void setHostPlayer(Player hostPlayer) {
		this.hostPlayer = hostPlayer;
	}

	public int[][] getInitialBoard() {
		return initialBoard;
	}

	public void setInitialBoard(int[][] board) {
		this.initialBoard = board;
	}

	public int[][] getCurrentBoard() {
		return currentBoard;
	}

	public void setCurrentBoard(int[][] currentBoard) {
		this.currentBoard = currentBoard;
	}

	public int[][] getSolution() {
		return solution;
	}

	public void setSolution(int[][] solution) {
		this.solution = solution;
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
