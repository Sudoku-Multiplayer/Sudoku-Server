package io.github.himanshusajwan911.sudokuserver.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.github.himanshusajwan911.sudokuserver.util.Util;

public class GameSession {

	private final Object boardUpdateLock = new Object();

	private String sessionId;

	private GameSessionStatus gameSessionStatus;

	private SudokuGame game;

	private int timeLimit;
	private int remainingTime;

	private int playerLimit;

	private int[][] gameBoard;

	private VoteSession voteSession;

	private List<GameChatMessage> gameChatMessages;
	private List<BoardUpdate> boardUpdates;

	private Map<Player, PlayerSession> playerSessionMap;

	public GameSession(SudokuGame game, int timeLimit, int playerLimit) {
		this.game = game;
		this.timeLimit = timeLimit;
		this.remainingTime = timeLimit;
		this.playerLimit = playerLimit;
		this.sessionId = game.getGameId();
		this.gameSessionStatus = GameSessionStatus.NEW;
		this.gameBoard = Util.clone2DArray(game.getInitialBoard());
		this.voteSession = new VoteSession();
		this.gameChatMessages = new ArrayList<>();
		this.boardUpdates = new ArrayList<>();
		this.playerSessionMap = new LinkedHashMap<>();
	}

	public String getSessionId() {
		return sessionId;
	}

	public synchronized GameSessionStatus getGameSessionStatus() {
		return gameSessionStatus;
	}

	public void setGameSessionStatus(GameSessionStatus gameSessionStatus) {
		this.gameSessionStatus = gameSessionStatus;
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

	public synchronized void increaseRemainingTime(int increaseBy) {
		this.remainingTime += increaseBy;
	}

	public synchronized void decreaseRemainingTime(int decreaseBy) {
		this.remainingTime -= decreaseBy;
	}

	public int getPlayerLimit() {
		return playerLimit;
	}

	public synchronized int[][] getGameBoard() {
		return gameBoard;
	}

	public VoteSession getVoteSession() {
		return voteSession;
	}

	public void updateBoard(BoardUpdate boardUpdate) {
		synchronized (boardUpdateLock) {
			this.gameBoard[boardUpdate.row][boardUpdate.column] = boardUpdate.value;
		}
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
