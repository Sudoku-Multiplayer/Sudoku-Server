package io.github.himanshusajwan911.sudokuserver.event;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.himanshusajwan911.sudokuserver.model.Player;
import io.github.himanshusajwan911.sudokuserver.model.WebSocketSession;
import io.github.himanshusajwan911.sudokuserver.service.GameService;
import io.github.himanshusajwan911.sudokuserver.service.NotificationService;
import io.github.himanshusajwan911.sudokuserver.service.WebSocketSessionService;

@Configuration
public class WebSocketEventListener {

	private WebSocketSessionService webSocketSessionService;

	private GameService gameService;

	private NotificationService notificationService;

	public WebSocketEventListener(WebSocketSessionService webSocketSessionService, GameService gameService,
			NotificationService notificationService) {
		this.webSocketSessionService = webSocketSessionService;
		this.gameService = gameService;
		this.notificationService = notificationService;
	}

	@EventListener
	public void handleSessionConnectEvent(SessionConnectEvent event) {

		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		String sessionId = headerAccessor.getSessionId();
		String playerJson = (String) headerAccessor.getFirstNativeHeader("player");

		if (playerJson != null) {
			Player player = null;
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				player = objectMapper.readValue(playerJson, Player.class);
				webSocketSessionService.registerSessionPlayer(sessionId, player);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}

	@EventListener
	public void handleSessionDisconnectEvent(SessionDisconnectEvent event) {

		String sessionId = event.getSessionId();

		WebSocketSession webSocketSession = webSocketSessionService.getWebSocketSession(sessionId);
		Player player = webSocketSession.getPlayer();
		String gameSessionId = webSocketSession.getSubscribedGameSessionId();

		boolean playerLeft = gameService.leaveGame(player, gameSessionId);

		if (playerLeft) {
			notificationService.notifyForGameSessionPlayerLeft(gameSessionId, player);
		}

		webSocketSessionService.removeWebsocketSession(sessionId);
	}

	@EventListener
	public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {

		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

		String destination = headerAccessor.getDestination();
		String sessionId = headerAccessor.getSessionId();
		String subscriptionId = headerAccessor.getSubscriptionId();

		// Subscription is for board updates.
		if (destination.startsWith("/game-session") && destination.endsWith("/board-update")) {
			String[] destinationSplit = destination.split("/");
			String gameSessionId = destinationSplit[destinationSplit.length - 2];

			webSocketSessionService.subscribeToGameSession(sessionId, gameSessionId, subscriptionId);
		}
	}

	@EventListener
	public void handleSessionUnsubscribeEvent(SessionUnsubscribeEvent event) {

		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

		String sessionId = headerAccessor.getSessionId();
		String subscriptionId = headerAccessor.getSubscriptionId();

		String registeredSubscriptionId = webSocketSessionService.getSubscriptionId(sessionId);

		if (registeredSubscriptionId != null) {
			if (registeredSubscriptionId.equals(subscriptionId)) {
				webSocketSessionService.unSubscribeFromGameSession(sessionId);
			}
		}
	}

}
