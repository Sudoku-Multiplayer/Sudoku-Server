package io.github.himanshusajwan911.sudokuserver.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import io.github.himanshusajwan911.sudokuserver.model.WebSocketSession;

@Repository
public class WebSocketSessionRepository {

	private Map<String, WebSocketSession> webSocketSessionMap = new HashMap<>();

	public void saveWebSocketSession(String webSocketSessionId, WebSocketSession webSocketSession) {
		webSocketSessionMap.put(webSocketSessionId, webSocketSession);
	}

	public WebSocketSession getWebSocketSession(String webSocketSessionId) {
		return webSocketSessionMap.get(webSocketSessionId);
	}

	public void removeWebSocketSession(String webSocketSessionId) {
		webSocketSessionMap.remove(webSocketSessionId);
	}

	public int getWebSocketSessionCount() {
		return webSocketSessionMap.size();
	}

}
