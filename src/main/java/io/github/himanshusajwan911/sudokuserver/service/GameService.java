package io.github.himanshusajwan911.sudokuserver.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.himanshusajwan911.sudokuserver.dto.SudokuGameDTO;
import io.github.himanshusajwan911.sudokuserver.exception.GameAlreadyExistsException;
import io.github.himanshusajwan911.sudokuserver.exception.NoSuchGameExistsException;
import io.github.himanshusajwan911.sudokuserver.model.BoardUpdate;
import io.github.himanshusajwan911.sudokuserver.model.CreateGameRequest;
import io.github.himanshusajwan911.sudokuserver.model.GameChatMessage;
import io.github.himanshusajwan911.sudokuserver.model.JoinGameResponse;
import io.github.himanshusajwan911.sudokuserver.model.Player;
import io.github.himanshusajwan911.sudokuserver.model.SudokuGame;
import io.github.himanshusajwan911.sudokuserver.model.SudokuGame.SudokuGameStatus;
import io.github.himanshusajwan911.sudokuserver.service.SudokuService.Level;

@Service
public class GameService {

	@Autowired
	SudokuService sudokuService;

	Map<String, SudokuGame> gamesMap = new HashMap<>();
	Map<String, List<BoardUpdate>> boardUpdatesMap = new HashMap<>();

	public String createGame(CreateGameRequest createGameRequest) {

		int boardSize = createGameRequest.getBoardSize();
		int playerLimit = createGameRequest.getPlayerLimit();
		Level level = createGameRequest.getLevel();

		String gameId = createGameId(createGameRequest);

		if (gameExists(gameId)) {
			throw new GameAlreadyExistsException("A game with id: " + gameId + " already exists.");
		}

		SudokuGame game = sudokuService.generateSudokuGame(boardSize, level, playerLimit);
		game.setStatus(SudokuGameStatus.NEW);
		game.setGameId(gameId);
		game.setHostName(createGameRequest.getPlayer().getName());
		game.setGameName(createGameRequest.getGameName());

		gamesMap.put(gameId, game);
		boardUpdatesMap.put(gameId, new ArrayList<BoardUpdate>());

		return gameId;
	}

	public void deleteGame(String gameId) {
		gamesMap.remove(gameId);
	}

	public JoinGameResponse joinGame(Player player, String gameId) {

		SudokuGame game = gamesMap.get(gameId);
		JoinGameResponse joinGameResponse = new JoinGameResponse();

		if (game == null) {
			joinGameResponse.setGameStatus(SudokuGameStatus.NO_SUCH_GAME_EXISTS);
			joinGameResponse.setStatusMessage("Game with id: " + gameId + " does not exists.");
		}

		else if (game.getPlayerCount() >= game.getPlayerLimit()) {
			joinGameResponse.setGameStatus(SudokuGameStatus.FULL);
			joinGameResponse.setStatusMessage("Game is full, cannot join.");
		}

		else if (game.getPlayers().contains(player)) {
			joinGameResponse.setGameStatus(SudokuGameStatus.PLAYER_ALREADY_JOINED);
			joinGameResponse.setStatusMessage("Player has already join this game.");
			joinGameResponse.setGame(game);
		} else {
			joinGameResponse.setGameStatus(SudokuGameStatus.PLAYER_ADDED);
			joinGameResponse.setStatusMessage("Game joined successfully.");
			joinGameResponse.setGame(game);
			game.addPlayer(player);
			game.setStatus(SudokuGameStatus.RUNNING);
		}

		return joinGameResponse;
	}

	public boolean leaveGame(Player player, String gameId) {

		SudokuGame game = gamesMap.get(gameId);

		if (game != null) {
			return game.removePlayer(player);
		}

		return false;
	}

	private String createGameId(CreateGameRequest createGameRequest) {

		Player player = createGameRequest.getPlayer();
		int boardSize = createGameRequest.getBoardSize();
		int playerLimit = createGameRequest.getPlayerLimit();
		Level level = createGameRequest.getLevel();

		byte[] playerNameBytes = player.getName().getBytes();
		byte[] playerIdBytes = ("" + player.getId()).getBytes();
		byte[] gameBytes = (level.toString() + boardSize + playerLimit).getBytes();

		byte[] playerBytes = Arrays.copyOf(playerNameBytes, playerNameBytes.length + playerIdBytes.length);
		System.arraycopy(playerIdBytes, 0, playerBytes, playerNameBytes.length, playerIdBytes.length);

		byte[] gameIdBytes = Arrays.copyOf(playerBytes, playerBytes.length + gameBytes.length);
		System.arraycopy(gameBytes, 0, gameIdBytes, playerBytes.length, gameBytes.length);

		UUID gameUUID = UUID.nameUUIDFromBytes(gameIdBytes);

		String gameId = gameUUID.toString();

		return gameId;
	}

	public List<SudokuGameDTO> getGames() {
		List<SudokuGameDTO> gamesList = new ArrayList<>();

		for (SudokuGame game : this.gamesMap.values()) {
			SudokuGameDTO gameDTO = new SudokuGameDTO();
			gameDTO.setHostName(game.getHostName());
			gameDTO.setGameName(game.getGameName());
			gameDTO.setInitialBoard(game.getInitialBoard());
			gameDTO.setCurrentBoard(game.getCurrentBoard());
			gameDTO.setSolution(game.getSolution());
			gameDTO.setLevel(game.getLevel());
			gameDTO.setPlayerCount(game.getPlayerCount());
			gameDTO.setPlayerLimit(game.getPlayerLimit());
			gameDTO.setStatus(game.getStatus());
			gameDTO.setGameId(game.getGameId());
			gameDTO.setBoardSize(game.getInitialBoard().length);

			gamesList.add(gameDTO);
		}

		return gamesList;
	}

	public boolean gameExists(String gameId) {
		return gamesMap.containsKey(gameId);
	}

	public void updateBoard(String gameId, BoardUpdate boardUpdate) {

		SudokuGame game = gamesMap.get(gameId);
		if (game == null) {
			throw new NoSuchGameExistsException("Game with id: " + gameId + " does not exists.");
		}

		game.updateCurrentBoard(boardUpdate.value, boardUpdate.row, boardUpdate.column);

		// List<BoardUpdate> boardUpdates = boardUpdatesMap.getOrDefault(gameId, new
		// ArrayList<BoardUpdate>());
		List<BoardUpdate> boardUpdates = boardUpdatesMap.get(gameId);
		boardUpdates.add(boardUpdate);

		// boardUpdatesMap.putIfAbsent(gameId, boardUpdates);
	}

	public List<BoardUpdate> getBoardUpdates(String gameId) {

		List<BoardUpdate> boardUpdates = boardUpdatesMap.get(gameId);

		if (boardUpdates == null) {
			throw new NoSuchGameExistsException("Game with id: " + gameId + " does not exists.");
		}

		return boardUpdates;
	}

	public List<Player> getJoinedPlayers(String gameId) {

		SudokuGame game = gamesMap.get(gameId);
		if (game == null) {
			throw new NoSuchGameExistsException("Game with id: " + gameId + " does not exists.");
		}

		return game.getPlayers();
	}

}
