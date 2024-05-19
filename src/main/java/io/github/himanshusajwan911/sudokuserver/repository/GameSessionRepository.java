package io.github.himanshusajwan911.sudokuserver.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import io.github.himanshusajwan911.sudokuserver.model.GameSession;
import io.github.himanshusajwan911.sudokuserver.service.GameSessionManager;

@Repository
public class GameSessionRepository {

	private Map<String, GameSession> gameSessionMap = new HashMap<>();

	private Map<String, GameSessionManager> gameSessionManagerMap = new HashMap<>();

	public void addGameSession(String gameId, GameSession gameSession) {
		gameSessionMap.put(gameId, gameSession);
	}

	public GameSession getGameSession(String gameId) {
		return gameSessionMap.get(gameId);
	}

	public void removeGamesession(String gameId) {
		gameSessionMap.remove(gameId);
	}

	public List<GameSession> getGameSessions() {
		return new ArrayList<GameSession>(gameSessionMap.values());
	}

	public void addGameSessionManager(String gameSessionId, GameSessionManager gameSessionManager) {
		gameSessionManagerMap.put(gameSessionId, gameSessionManager);
	}

	public GameSessionManager getGameSessionManager(String gameSessionId) {
		return gameSessionManagerMap.get(gameSessionId);
	}

	public void removeGamesessionManager(String gameSessionId) {
		gameSessionManagerMap.remove(gameSessionId);
	}

	public List<GameSessionManager> getGameSessionManagers() {
		return new ArrayList<GameSessionManager>(gameSessionManagerMap.values());
	}

}
