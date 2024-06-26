package io.github.himanshusajwan911.sudokuserver.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.github.himanshusajwan911.sudokuserver.dto.GameSessionDTO;
import io.github.himanshusajwan911.sudokuserver.exception.NoSuchGameExistsException;
import io.github.himanshusajwan911.sudokuserver.model.BoardUpdate;
import io.github.himanshusajwan911.sudokuserver.model.GameChatMessage;
import io.github.himanshusajwan911.sudokuserver.model.GameSession;
import io.github.himanshusajwan911.sudokuserver.model.GameSessionStatus;
import io.github.himanshusajwan911.sudokuserver.model.JoinGameResponse;
import io.github.himanshusajwan911.sudokuserver.model.JoinStatus;
import io.github.himanshusajwan911.sudokuserver.model.Player;
import io.github.himanshusajwan911.sudokuserver.model.SudokuGame;
import io.github.himanshusajwan911.sudokuserver.model.VoteRecord;
import io.github.himanshusajwan911.sudokuserver.repository.GameSessionRepository;

@Service
public class GameSessionService {

	private GameSessionRepository gameSessionRepository;

	private NotificationService notificationService;

	public GameSessionService(GameSessionRepository gameSessionRepository, NotificationService notificationService) {
		this.gameSessionRepository = gameSessionRepository;
		this.notificationService = notificationService;
	}

	public GameSession createGameSession(String gameSessionId, SudokuGame game, int timeLimit, int playerLimit) {
		GameSession gameSession = new GameSession(game, timeLimit, playerLimit);
		gameSessionRepository.addGameSession(gameSessionId, gameSession);

		return gameSession;
	}

	public GameSessionManager createGameSessionManager(GameSession gameSession) {
		GameSessionManager gameSessionManager = new GameSessionManager(gameSession, notificationService);
		gameSessionRepository.addGameSessionManager(gameSession.getSessionId(), gameSessionManager);

		return gameSessionManager;
	}

	public void startGame(String gameSessionId) {
		GameSessionManager gameSessionManager = gameSessionRepository.getGameSessionManager(gameSessionId);

		if (gameSessionManager == null) {
			throw new NoSuchGameExistsException("Game with id: " + gameSessionId + " does not exists.");
		}

		gameSessionManager.startGame();
	}

	public void stopGame(String gameSessionId) {
		GameSessionManager gameSessionManager = gameSessionRepository.getGameSessionManager(gameSessionId);

		if (gameSessionManager == null) {
			throw new NoSuchGameExistsException("Game with id: " + gameSessionId + " does not exists.");
		}

		gameSessionManager.stopGame();
	}

	public void pauseGame(String gameSessionId) {
		GameSessionManager gameSessionManager = gameSessionRepository.getGameSessionManager(gameSessionId);

		if (gameSessionManager == null) {
			throw new NoSuchGameExistsException("Game with id: " + gameSessionId + " does not exists.");
		}

		gameSessionManager.pauseGame();
	}

	public void resumeGame(String gameSessionId) {
		GameSessionManager gameSessionManager = gameSessionRepository.getGameSessionManager(gameSessionId);

		if (gameSessionManager == null) {
			throw new NoSuchGameExistsException("Game with id: " + gameSessionId + " does not exists.");
		}

		gameSessionManager.resumeGame();
	}

	public JoinGameResponse joinGame(Player player, String gameSessionId) {
		GameSession gameSession = gameSessionRepository.getGameSession(gameSessionId);

		if (gameSession == null) {
			throw new NoSuchGameExistsException("Game with id: " + gameSessionId + " does not exists.");
		}

		JoinGameResponse joinGameResponse = new JoinGameResponse();

		if (gameSession.getPlayerCount() >= gameSession.getGame().getPlayerLimit()) {
			joinGameResponse.setJoinStatus(JoinStatus.GAME_FULL);
			joinGameResponse.setStatusMessage("Game is full, cannot join.");
		}

		else {
			joinGameResponse.setJoinStatus(JoinStatus.PLAYER_ADDED);
			joinGameResponse.setStatusMessage("Game joined successfully.");

			gameSession.addPlayer(player);

			GameSessionDTO gameSessionDTO = new GameSessionDTO();
			gameSessionDTO.setSessionId(gameSession.getSessionId());
			gameSessionDTO.setGame(gameSession.getGame());
			gameSessionDTO.setTimeLimit(gameSession.getTimeLimit());
			gameSessionDTO.setRemainingTime(gameSession.getRemainingTime());
			gameSessionDTO.setGameSessionStatus(gameSession.getGameSessionStatus());
			gameSessionDTO.setGameBoard(gameSession.getGameBoard());
			gameSessionDTO.setGameChatMessages(gameSession.getGameChatMessages());
			gameSessionDTO.setBoardUpdates(gameSession.getBoardUpdates());
			gameSessionDTO.setPlayers(gameSession.getJoinedPlayers());

			joinGameResponse.setGameSession(gameSessionDTO);
		}

		return joinGameResponse;
	}

	public boolean leaveGame(Player player, String gameSessionId) {
		GameSession gameSession = gameSessionRepository.getGameSession(gameSessionId);

		if (gameSession != null) {
			return gameSession.removePlayer(player);
		}

		return false;
	}

	public List<Player> getJoinedPlayers(String gameSessionId) {
		GameSession gameSession = gameSessionRepository.getGameSession(gameSessionId);

		if (gameSession == null) {
			throw new NoSuchGameExistsException("Game with id: " + gameSessionId + " does not exists.");
		}

		return gameSession.getJoinedPlayers();
	}

	public void updateBoard(String gameSessionId, BoardUpdate boardUpdate) {

		GameSession gameSession = gameSessionRepository.getGameSession(gameSessionId);

		if (gameSession == null) {
			throw new NoSuchGameExistsException("Game with id: " + gameSessionId + " does not exists.");
		}

		if (gameSession.getGameSessionStatus() == GameSessionStatus.RUNNING) {
			gameSession.updateBoard(boardUpdate);
			gameSession.addBoardUpdate(boardUpdate);
			notificationService.notifyForGameSessionBoardUpdate(gameSessionId, boardUpdate);
		}

		else if (gameSession.getGameSessionStatus() == GameSessionStatus.NEW) {
			notificationService.notifyForGameSessionMessageUpdate(gameSessionId,
					"Cannot updated board, Game is not started yet.");
		}

		else if (gameSession.getGameSessionStatus() == GameSessionStatus.PAUSED) {
			notificationService.notifyForGameSessionMessageUpdate(gameSessionId,
					"Cannot updated board, Game is Paused.");
		}

		else if (gameSession.getGameSessionStatus() == GameSessionStatus.FINISHED) {
			notificationService.notifyForGameSessionMessageUpdate(gameSessionId,
					"Cannot updated board, Game is Finished.");
		}
	}

	public void addBoardUpdate(String gameSessionId, BoardUpdate boardUpdate) {
		GameSession gameSession = gameSessionRepository.getGameSession(gameSessionId);

		if (gameSession == null) {
			throw new NoSuchGameExistsException("Game with id: " + gameSessionId + " does not exists.");
		}

		gameSession.addBoardUpdate(boardUpdate);
	}

	public List<BoardUpdate> getBoardUpdates(String gameSessionId) {
		GameSession gameSession = gameSessionRepository.getGameSession(gameSessionId);

		if (gameSession == null) {
			throw new NoSuchGameExistsException("Game with id: " + gameSessionId + " does not exists.");
		}

		return gameSession.getBoardUpdates();
	}

	public void addGameChatMessage(String gameSessionId, GameChatMessage gameChatMessage) {
		GameSession gameSession = gameSessionRepository.getGameSession(gameSessionId);

		if (gameSession == null) {
			throw new NoSuchGameExistsException("Game with id: " + gameSessionId + " does not exists.");
		}

		gameSession.addGameChatMessage(gameChatMessage);
	}

	public List<GameChatMessage> getGameChatMessages(String gameSessionId) {
		GameSession gameSession = gameSessionRepository.getGameSession(gameSessionId);

		if (gameSession == null) {
			throw new NoSuchGameExistsException("Game with id: " + gameSessionId + " does not exists.");
		}

		return gameSession.getGameChatMessages();
	}

	public void initiateGameSubmitVoting(String gameSessionId, Player player) {
		GameSessionManager gameSessionManager = gameSessionRepository.getGameSessionManager(gameSessionId);

		if (gameSessionManager == null) {
			throw new NoSuchGameExistsException("Game with id: " + gameSessionId + " does not exists.");
		}

		gameSessionManager.initiateGameSubmitVoting(player);
	}

	public void castGameSubmitVote(String gameSessionId, VoteRecord voteRecord) {
		GameSessionManager gameSessionManager = gameSessionRepository.getGameSessionManager(gameSessionId);

		if (gameSessionManager == null) {
			throw new NoSuchGameExistsException("Game with id: " + gameSessionId + " does not exists.");
		}

		gameSessionManager.castGameSubmitVote(voteRecord);
	}

}
