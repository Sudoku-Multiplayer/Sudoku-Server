package io.github.himanshusajwan911.sudokuserver.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GameSession {

	private String sessionId;

	private SudokuGame game;

	private int timeLimit;
	private int remainingTime;

	private int playerLimit;

	private List<GameChatMessage> gameChatMessages;
	private List<BoardUpdate> boardUpdates;

	private Map<Player, PlayerSession> playerSessionMap;

	public GameSession(SudokuGame game, int timeLimit, int playerLimit) {
		this.game = game;
		this.timeLimit = timeLimit;
		this.remainingTime = timeLimit;
		this.playerLimit = playerLimit;
		this.sessionId = game.getGameId();
		this.gameChatMessages = new ArrayList<>();
		this.boardUpdates = new ArrayList<>();
		this.playerSessionMap = new LinkedHashMap<>();
	}

	public String getSessionId() {
		return sessionId;
	}

	public SudokuGame getGame() {
		return game;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public synchronized int getRemainingTime() {
		return remainingTime;
	}

	public int getPlayerLimit() {
		return playerLimit;
	}

	public synchronized void increaseRemainingTime(int increaseBy) {
		this.remainingTime += increaseBy;
	}

	public synchronized void decreaseRemainingTime(int decreaseBy) {
		this.remainingTime -= decreaseBy;
	}

	public void addPlayer(Player player) {
		PlayerSession playerSession = new PlayerSession();
		playerSession.setPlayer(player);
		playerSession.setVoteStatus(VoteStatus.WAITING);

		playerSessionMap.put(player, playerSession);
	}

	public boolean removePlayer(Player player) {
		PlayerSession playerSession = playerSessionMap.remove(player);
		Player removedPlayer = playerSession.getPlayer();

		return player.equals(removedPlayer);
	}

	public List<Player> getJoinedPlayers() {
		return new ArrayList<Player>(playerSessionMap.keySet());
	}

	public int getPlayerCount() {
		return playerSessionMap.size();
	}

	public void addBoardUpdate(BoardUpdate boardUpdate) {
		boardUpdates.add(boardUpdate);
	}

	public List<BoardUpdate> getBoardUpdates() {
		return boardUpdates;
	}

	public void addGameChatMessage(GameChatMessage gameChatMessage) {
		gameChatMessages.add(gameChatMessage);
	}

	public List<GameChatMessage> getGameChatMessages() {
		return gameChatMessages;
	}

}
