package io.github.himanshusajwan911.sudokuserver.model;

import io.github.himanshusajwan911.sudokuserver.model.SudokuGame.SudokuGameStatus;

public class JoinGameResponse {

	SudokuGameStatus gameStatus;

	String statusMessage;

	SudokuGame game;

	public SudokuGameStatus getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(SudokuGameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public SudokuGame getGame() {
		return game;
	}

	public void setGame(SudokuGame game) {
		this.game = game;
	}

}
