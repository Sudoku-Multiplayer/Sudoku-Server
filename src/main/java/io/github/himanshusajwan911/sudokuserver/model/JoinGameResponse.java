package io.github.himanshusajwan911.sudokuserver.model;

import io.github.himanshusajwan911.sudokuserver.dto.GameSessionDTO;
import io.github.himanshusajwan911.sudokuserver.model.SudokuGame.SudokuGameStatus;

public class JoinGameResponse {

	SudokuGameStatus joinStatus;

	String statusMessage;

	GameSessionDTO gameSession;

	public SudokuGameStatus getJoinStatus() {
		return joinStatus;
	}

	public void setJoinStatus(SudokuGameStatus gameStatus) {
		this.joinStatus = gameStatus;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public GameSessionDTO getGameSession() {
		return gameSession;
	}

	public void setGameSession(GameSessionDTO gameSession) {
		this.gameSession = gameSession;
	}

}
