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
import io.github.himanshusajwan911.sudokuserver.model.CreateGameRequest;
import io.github.himanshusajwan911.sudokuserver.model.Player;
import io.github.himanshusajwan911.sudokuserver.model.SudokuGame;
import io.github.himanshusajwan911.sudokuserver.model.SudokuGame.SudokuGameStatus;
import io.github.himanshusajwan911.sudokuserver.service.SudokuService.Level;

@Service
public class GameService {

	@Autowired
	SudokuService sudokuService;

	Map<String, SudokuGame> gamesMap = new HashMap<>();

	public String createGame(CreateGameRequest createGameRequest) {

		int boardSize = createGameRequest.getBoardSize();
		int playerLimit = createGameRequest.getPlayerLimit();
		Level level = createGameRequest.getLevel();

		String gameId = createGameId(createGameRequest);

		if (gamesMap.containsKey(gameId)) {
			throw new GameAlreadyExistsException("A game with id: " + gameId + " already exists.");
		}

		SudokuGame game = sudokuService.generateSudokuGame(boardSize, level, playerLimit);
		game.setStatus(SudokuGameStatus.NEW);
		game.setId(gameId);

		gamesMap.put(gameId, game);

		return gameId;
	}

	public void deleteGame(String gameId) {
		gamesMap.remove(gameId);
	}

	public SudokuGameStatus joinGame(Player player, String gameId) {

		SudokuGame game = gamesMap.get(gameId);

		if (game == null) {
			return SudokuGameStatus.NO_SUCH_GAME_EXISTS;
		}

		if (game.getPlayerCount() >= game.getPlayerLimit()) {
			return SudokuGameStatus.FULL;
		}

		if (game.getPlayers().contains(player)) {
			return SudokuGameStatus.PLAYER_ALREADY_JOINED;
		}

		game.addPlayer(player);

		return SudokuGameStatus.PLAYER_ADDED;
	}

	public void leaveGame(Player player, String gameId) {

		SudokuGame game = gamesMap.get(gameId);

		if (game != null) {
			game.removePlayer(player);
		}
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
			gameDTO.setBoard(game.getBoard());
			gameDTO.setLevel(game.getLevel());
			gameDTO.setPlayerCount(game.getPlayerCount());
			gameDTO.setPlayerLimit(game.getPlayerLimit());
			gameDTO.setStatus(game.getStatus());
			gameDTO.setGameId(game.getId());
			gameDTO.setBoardSize(game.getBoard().length);

			gamesList.add(gameDTO);
		}

		return gamesList;
	}

}
