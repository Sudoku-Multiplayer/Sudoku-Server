package io.github.himanshusajwan911.sudokuserver.service;

import java.util.List;

import org.springframework.stereotype.Service;

import io.github.himanshusajwan911.sudokuserver.model.BoardUpdate;
import io.github.himanshusajwan911.sudokuserver.model.GameChatMessage;
import io.github.himanshusajwan911.sudokuserver.model.GameSession;
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

	public GameSession createGameSession(String gameId, SudokuGame game, int timeLimit, int playerLimit) {
		GameSession gameSession = new GameSession(game, timeLimit, playerLimit);
		gameSessionRepository.addGameSession(gameId, gameSession);

		return gameSession;
	}

	public GameSessionManager createGameSessionManager(GameSession gameSession) {
		GameSessionManager gameSessionManager = new GameSessionManager(gameSession, notificationService);
		gameSessionRepository.addGameSessionManager(gameSession.getSessionId(), gameSessionManager);

		return gameSessionManager;
	}

	public void startGame(String gameId) {
		GameSession gameSession = gameSessionRepository.getGameSession(gameId);
		gameSession.startGame();
	}

	public void stopGame(String gameId) {
		GameSession gameSession = gameSessionRepository.getGameSession(gameId);
		gameSession.stopGame();
	}

	public void pauseGame(String gameId) {
		GameSession gameSession = gameSessionRepository.getGameSession(gameId);
		gameSession.pauseGame();
	}

	public void resumeGame(String gameId) {
		GameSession gameSession = gameSessionRepository.getGameSession(gameId);
		gameSession.resumeGame();
	}

	public void addBoardUpdate(String gameId, BoardUpdate boardUpdate) {
		GameSession gameSession = gameSessionRepository.getGameSession(gameId);
		gameSession.addBoardUpdate(boardUpdate);
	}

	public List<BoardUpdate> getBoardUpdates(String gameId) {
		GameSession gameSession = gameSessionRepository.getGameSession(gameId);

		return gameSession.getBoardUpdates();
	}

	public void addGameChatMessage(String gameId, GameChatMessage gameChatMessage) {
		GameSession gameSession = gameSessionRepository.getGameSession(gameId);
		gameSession.addGameChatMessage(gameChatMessage);
	}

	public List<GameChatMessage> getGameChatMessages(String gameId) {
		GameSession gameSession = gameSessionRepository.getGameSession(gameId);

		return gameSession.getGameChatMessages();
	}

}
