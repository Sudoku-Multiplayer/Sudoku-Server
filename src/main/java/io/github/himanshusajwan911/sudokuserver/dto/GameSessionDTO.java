package io.github.himanshusajwan911.sudokuserver.dto;

import java.util.List;

import io.github.himanshusajwan911.sudokuserver.model.BoardUpdate;
import io.github.himanshusajwan911.sudokuserver.model.GameChatMessage;
import io.github.himanshusajwan911.sudokuserver.model.GameSessionStatus;
import io.github.himanshusajwan911.sudokuserver.model.Player;
import io.github.himanshusajwan911.sudokuserver.model.SudokuGame;

public class GameSessionDTO {

	private String sessionId;

	private GameSessionStatus gameSessionStatus;

	private SudokuGame game;

	private int timeLimit;

	private int remainingTime;

	private int[][] gameBoard;

	private List<GameChatMessage> gameChatMessages;

	private List<BoardUpdate> boardUpdates;

	private List<Player> players;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public GameSessionStatus getGameSessionStatus() {
		return gameSessionStatus;
	}

	public void setGameSessionStatus(GameSessionStatus gameSessionStatus) {
		this.gameSessionStatus = gameSessionStatus;
	}

	public SudokuGame getGame() {
		return game;
	}

	public void setGame(SudokuGame game) {
		this.game = game;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	public int getRemainingTime() {
		return remainingTime;
	}

	public void setRemainingTime(int remainingTime) {
		this.remainingTime = remainingTime;
	}

	public int[][] getGameBoard() {
		return gameBoard;
	}

	public void setGameBoard(int[][] gameBoard) {
		this.gameBoard = gameBoard;
	}

	public List<GameChatMessage> getGameChatMessages() {
		return gameChatMessages;
	}

	public void setGameChatMessages(List<GameChatMessage> gameChatMessages) {
		this.gameChatMessages = gameChatMessages;
	}

	public List<BoardUpdate> getBoardUpdates() {
		return boardUpdates;
	}

	public void setBoardUpdates(List<BoardUpdate> boardUpdates) {
		this.boardUpdates = boardUpdates;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

}
