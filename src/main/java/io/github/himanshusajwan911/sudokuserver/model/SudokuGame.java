package io.github.himanshusajwan911.sudokuserver.model;

import java.util.ArrayList;
import java.util.List;

import io.github.himanshusajwan911.sudokuserver.service.SudokuService.Level;

public class SudokuGame {

	public enum SudokuGameStatus {
		NEW, RUNNING, FINISHED, FULL, PLAYER_ADDED, PLAYER_ALREADY_JOINED, NO_SUCH_GAME_EXISTS;
	}

	private int[][] board;

	private Level level;

	private List<Player> players;

	private int playerLimit;

	private SudokuGameStatus status;

	private String id;

	public SudokuGame(int[][] board, int playerLimit) {
		this.board = board;
		this.playerLimit = playerLimit;
		this.level = Level.EASY;
		this.players = new ArrayList<Player>();
	}

	public SudokuGame(int[][] board, Level level, int playerLimit) {
		this.board = board;
		this.level = level;
		this.playerLimit = playerLimit;
		this.players = new ArrayList<Player>();
	}

	public SudokuGame(int[][] board, Level level, int playerLimit, List<Player> players) {
		this.board = board;
		this.level = level;
		this.playerLimit = playerLimit;
		this.players = players;
	}

	public int[][] getBoard() {
		return board;
	}

	public Level getLevel() {
		return level;
	}

	public void addPlayer(Player player) {
		this.players.add(player);
	}

	public void removePlayer(Player player) {
		this.players.remove(player);
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
