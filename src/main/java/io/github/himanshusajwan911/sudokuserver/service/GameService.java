package io.github.himanshusajwan911.sudokuserver.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.himanshusajwan911.sudokuserver.dto.SudokuGameDTO;
import io.github.himanshusajwan911.sudokuserver.exception.GameAlreadyExistsException;
import io.github.himanshusajwan911.sudokuserver.exception.NoSuchGameExistsException;
import io.github.himanshusajwan911.sudokuserver.model.BoardUpdate;
import io.github.himanshusajwan911.sudokuserver.model.CreateGameRequest;
import io.github.himanshusajwan911.sudokuserver.model.GameSession;
import io.github.himanshusajwan911.sudokuserver.model.Player;
import io.github.himanshusajwan911.sudokuserver.model.SudokuGame;
import io.github.himanshusajwan911.sudokuserver.model.SudokuGame.SudokuGameStatus;
import io.github.himanshusajwan911.sudokuserver.repository.GameRepository;
import io.github.himanshusajwan911.sudokuserver.repository.GameSessionRepository;
import io.github.himanshusajwan911.sudokuserver.service.SudokuService.Level;

@Service
public class GameService {

	@Autowired
	private SudokuService sudokuService;

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private GameSessionService gameSessionService;

	@Autowired
	private GameSessionRepository gameSessionRepository;

	@Autowired
	private NotificationService notificationService;

	public String createGame(CreateGameRequest createGameRequest) {

		int boardSize = createGameRequest.getBoardSize();
		int playerLimit = createGameRequest.getPlayerLimit();
		Level level = createGameRequest.getLevel();
		int timeLimit = createGameRequest.getTimeLimit();

		String gameId = createGameId(createGameRequest);

		if (gameExists(gameId)) {
			throw new GameAlreadyExistsException("A game with id: " + gameId + " already exists.");
		}

		SudokuGame game = sudokuService.generateSudokuGame(boardSize, level, playerLimit, timeLimit);
		game.setStatus(SudokuGameStatus.NEW);
		game.setGameId(gameId);
		game.setHostPlayer(createGameRequest.getPlayer());
		game.setGameName(createGameRequest.getGameName());

		gameRepository.addGame(gameId, game);

		return gameId;
	}

	public void removeGame(String gameId) {
		gameRepository.removeGame(gameId);
	}

	private String createGameId(CreateGameRequest createGameRequest) {

		Player player = createGameRequest.getPlayer();
		int boardSize = createGameRequest.getBoardSize();
		int playerLimit = createGameRequest.getPlayerLimit();
		Level level = createGameRequest.getLevel();
		String gameName = createGameRequest.getGameName();

		byte[] playerNameBytes = player.getName().getBytes();
		byte[] playerIdBytes = ("" + player.getId()).getBytes();
		byte[] gameBytes = (gameName + level.toString() + boardSize + playerLimit).getBytes();

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

		for (SudokuGame game : gameRepository.getGames()) {
			GameSession gameSession = gameSessionRepository.getGameSession(game.getGameId());

			SudokuGameDTO gameDTO = new SudokuGameDTO();
			gameDTO.setHostPlayer(game.getHostPlayer());
			gameDTO.setGameName(game.getGameName());
			gameDTO.setInitialBoard(game.getInitialBoard());
			gameDTO.setCurrentBoard(game.getCurrentBoard());
			gameDTO.setSolution(game.getSolution());
			gameDTO.setLevel(game.getLevel());
			gameDTO.setPlayerCount(gameSession.getPlayerCount());
			gameDTO.setPlayerLimit(game.getPlayerLimit());
			gameDTO.setStatus(gameSession.getGameSessionStatus());
			gameDTO.setGameId(game.getGameId());
			gameDTO.setBoardSize(game.getInitialBoard().length);

			gamesList.add(gameDTO);
		}

		return gamesList;
	}

	public boolean gameExists(String gameId) {

		if (gameRepository.getGame(gameId) != null) {
			return true;
		}

		return false;
	}

}
