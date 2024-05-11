package io.github.himanshusajwan911.sudokuserver.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import io.github.himanshusajwan911.sudokuserver.model.GameSession;

@Repository
public class GameSessionRepository {

	private Map<String, GameSession> gameSessionMap = new HashMap<>();

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

}
