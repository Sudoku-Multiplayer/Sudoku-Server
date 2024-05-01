package io.github.himanshusajwan911.sudokuserver.model;

import io.github.himanshusajwan911.sudokuserver.service.SudokuService.Level;

public class CreateGameRequest {

	private String gameName;

	private Player player;

	private int boardSize;

	private Level level;

	private int playerLimit;

	private int timeLimit;

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public int getPlayerLimit() {
		return playerLimit;
	}

	public void setPlayerLimit(int playerLimit) {
		this.playerLimit = playerLimit;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	@Override
	public String toString() {
		return "CreateGameRequest [gameName=" + gameName + ", player=" + player + ", boardSize=" + boardSize
				+ ", level=" + level + ", playerLimit=" + playerLimit + ", timeLimit=" + timeLimit + "]";
	}

}
