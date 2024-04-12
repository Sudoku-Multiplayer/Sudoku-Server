package io.github.himanshusajwan911.sudokuserver.model;

import java.util.ArrayList;
import java.util.List;

import io.github.himanshusajwan911.sudokuserver.service.SudokuService.Level;

public class SudokuGame {

	public enum SudokuGameStatus {
		NEW, RUNNING, FINISHED, FULL, PLAYER_ADDED, PLAYER_ALREADY_JOINED, NO_SUCH_GAME_EXISTS;
	}

	private String gameName;

	private String hostName;

	private int[][] initialBoard;

	private int[][] currentBoard;

	private int[][] solution;

	private Level level;

	private List<Player> players;

	private int playerLimit;

	private SudokuGameStatus status;

	private String gameId;

	public SudokuGame(int[][] initialBoard, int[][] currentBoard, int[][] solution, int playerLimit) {
		this.initialBoard = initialBoard;
		this.currentBoard = currentBoard;
		this.solution = solution;
		this.playerLimit = playerLimit;
		this.level = Level.EASY;
		this.players = new ArrayList<Player>();
	}

	public SudokuGame(int[][] initialBoard, int[][] currentBoard, int[][] solution, Level level, int playerLimit) {
		this.initialBoard = initialBoard;
		this.currentBoard = currentBoard;
		this.solution = solution;
		this.level = level;
		this.playerLimit = playerLimit;
		this.players = new ArrayList<Player>();
	}

	public SudokuGame(int[][] initialBoard, int[][] currentBoard, int[][] solution, Level level, int playerLimit,
			List<Player> players) {
		this.initialBoard = initialBoard;
		this.currentBoard = currentBoard;
		this.solution = solution;
		this.level = level;
		this.playerLimit = playerLimit;
		this.players = players;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int[][] getInitialBoard() {
		return initialBoard;
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

	public Level getLevel() {
		return level;
	}

	public void addPlayer(Player player) {
		this.players.add(player);
	}

	public boolean removePlayer(Player player) {
		if (this.players.contains(player)) {
			this.players.remove(player);
			return true;
		}

		return false;
	}

	public SudokuGameStatus getStatus() {
		return status;
	}

	public void setStatus(SudokuGameStatus status) {
		this.status = status;
	}

	public int getPlayerLimit() {
		return playerLimit;
	}

	public void setPlayerLimit(int playerLimit) {
		this.playerLimit = playerLimit;
	}

	public int getPlayerCount() {
		return players.size();
	}

	public List<Player> getPlayers() {
		return players;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public void updateCurrentBoard(int value, int row, int column) {
		this.currentBoard[row][column] = value;
	}

}
