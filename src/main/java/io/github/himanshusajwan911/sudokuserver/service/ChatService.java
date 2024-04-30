package io.github.himanshusajwan911.sudokuserver.service;

import java.util.List;
import org.springframework.stereotype.Service;

import io.github.himanshusajwan911.sudokuserver.model.GameChatMessage;

@Service
public class ChatService {

	private GameSessionService gameSessionService;

	public ChatService(GameSessionService gameSessionService) {
		this.gameSessionService = gameSessionService;
	}

	public void addGameSessionChatMessage(String gameId, GameChatMessage gameChatMessage) {
		gameSessionService.addGameChatMessage(gameId, gameChatMessage);
	}

	public List<GameChatMessage> getGameChat(String gameId) {
		return gameSessionService.getGameChatMessages(gameId);
	}

}
