package io.github.himanshusajwan911.sudokuserver.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import io.github.himanshusajwan911.sudokuserver.model.GameSession;

@Repository
public class GameSessionRepository {

	Map<String, GameSession> gameSessionMap = new HashMap<>();

	public void addGameSession(String gameId, GameSession gameSession) {
		gameSessionMap.put(gameId, gameSession);
	}

	public GameSession getGameSession(String gameId) {
		return gameSessionMap.get(gameId);
	}

	public void removeGamesession(String gameId) {
		gameSessionMap.remove(gameId);
	}

}
