package io.github.himanshusajwan911.sudokuserver.model;

import io.github.himanshusajwan911.sudokuserver.dto.GameSessionDTO;

public class JoinGameResponse {

	JoinStatus joinStatus;

	String statusMessage;

	GameSessionDTO gameSession;

	public JoinStatus getJoinStatus() {
		return joinStatus;
	}

	public void setJoinStatus(JoinStatus joinStatus) {
		this.joinStatus = joinStatus;
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
