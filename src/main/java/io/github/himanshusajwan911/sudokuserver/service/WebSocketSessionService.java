package io.github.himanshusajwan911.sudokuserver.service;

import org.springframework.stereotype.Service;

import io.github.himanshusajwan911.sudokuserver.model.Player;
import io.github.himanshusajwan911.sudokuserver.model.WebSocketSession;
import io.github.himanshusajwan911.sudokuserver.repository.WebSocketSessionRepository;

@Service
public class WebSocketSessionService {

	private WebSocketSessionRepository webSocketSessionRepository;

	public WebSocketSessionService(WebSocketSessionRepository webSocketSessionRepository) {
		this.webSocketSessionRepository = webSocketSessionRepository;
	}

	public void createWebSocketSession(String webSocketSessionId, Player player, String gameId, String subscriptionId) {
		WebSocketSession webSocketSession = new WebSocketSession(webSocketSessionId, player, gameId, subscriptionId);
		webSocketSessionRepository.saveWebSocketSession(webSocketSessionId, webSocketSession);
	}

	public void registerSessionPlayer(String webSocketSessionId, Player player) {

		WebSocketSession webSocketSession = webSocketSessionRepository.getWebSocketSession(webSocketSessionId);
		if (webSocketSession != null) {
			webSocketSession.setPlayer(player);
		}

		else {
			createWebSocketSession(webSocketSessionId, player, null, null);
		}
	}

	public void registerSubscribedGameSessionId(String webSocketSessionId, String gameId) {

		WebSocketSession webSocketSession = webSocketSessionRepository.getWebSocketSession(webSocketSessionId);
		if (webSocketSession != null) {
			webSocketSession.setSubscribedGameSessionId(gameId);
		}

		else {
			createWebSocketSession(webSocketSessionId, null, gameId, null);
		}
	}

	public void registerSubscriptionId(String webSocketSessionId, String subscriptionId) {

		WebSocketSession webSocketSession = webSocketSessionRepository.getWebSocketSession(webSocketSessionId);
		if (webSocketSession != null) {
			webSocketSession.setSubscriptionId(subscriptionId);
		}

		else {
			createWebSocketSession(webSocketSessionId, null, null, subscriptionId);
		}
	}

	public String getSubscriptionId(String webSocketSessionId) {
		WebSocketSession webSocketSession = webSocketSessionRepository.getWebSocketSession(webSocketSessionId);
		if (webSocketSession != null) {
			return webSocketSession.getSubscriptionId();
		}

		return null;
	}

	public void subscribeToGameSession(String webSocketSessionId, String gameSessionId, String subscriptionId) {

		WebSocketSession webSocketSession = webSocketSessionRepository.getWebSocketSession(webSocketSessionId);
		if (webSocketSession != null) {
			webSocketSession.setSubscribedGameSessionId(gameSessionId);
			webSocketSession.setSubscriptionId(subscriptionId);
		}

		else {
			createWebSocketSession(webSocketSessionId, null, gameSessionId, subscriptionId);
		}
	}

	public void unSubscribeFromGameSession(String webSocketSessionId) {

		WebSocketSession webSocketSession = webSocketSessionRepository.getWebSocketSession(webSocketSessionId);
		if (webSocketSession != null) {
			webSocketSession.setSubscribedGameSessionId(null);
			webSocketSession.setSubscriptionId(null);
		}
	}

	public WebSocketSession getWebSocketSession(String webSocketSessionId) {
		return webSocketSessionRepository.getWebSocketSession(webSocketSessionId);
	}

	public void removeWebsocketSession(String webSocketSessionId) {
		webSocketSessionRepository.removeWebSocketSession(webSocketSessionId);
	}

	public int getOnlinePlayerCount() {
		return webSocketSessionRepository.getWebSocketSessionCount();
	}

}
