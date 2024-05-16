package io.github.himanshusajwan911.sudokuserver.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.github.himanshusajwan911.sudokuserver.exception.NoSuchGameExistsException;
import io.github.himanshusajwan911.sudokuserver.model.BoardUpdate;
import io.github.himanshusajwan911.sudokuserver.model.GameChatMessage;
import io.github.himanshusajwan911.sudokuserver.model.GameSession;
import io.github.himanshusajwan911.sudokuserver.model.GameSessionStatus;
import io.github.himanshusajwan911.sudokuserver.model.SudokuGame;
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
		gameSessionManager.startGame();
	}

	public void stopGame(String gameSessionId) {
		GameSessionManager gameSessionManager = gameSessionRepository.getGameSessionManager(gameSessionId);
		gameSessionManager.stopGame();
	}

	public void pauseGame(String gameSessionId) {
		GameSessionManager gameSessionManager = gameSessionRepository.getGameSessionManager(gameSessionId);
		gameSessionManager.pauseGame();
	}

	public void resumeGame(String gameSessionId) {
		GameSessionManager gameSessionManager = gameSessionRepository.getGameSessionManager(gameSessionId);
		gameSessionManager.resumeGame();
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
		gameSession.addBoardUpdate(boardUpdate);
	}

	public List<BoardUpdate> getBoardUpdates(String gameSessionId) {
		GameSession gameSession = gameSessionRepository.getGameSession(gameSessionId);

		return gameSession.getBoardUpdates();
	}

	public void addGameChatMessage(String gameSessionId, GameChatMessage gameChatMessage) {
		GameSession gameSession = gameSessionRepository.getGameSession(gameSessionId);
		gameSession.addGameChatMessage(gameChatMessage);
	}

	public List<GameChatMessage> getGameChatMessages(String gameSessionId) {
		GameSession gameSession = gameSessionRepository.getGameSession(gameSessionId);

		return gameSession.getGameChatMessages();
	}

}
