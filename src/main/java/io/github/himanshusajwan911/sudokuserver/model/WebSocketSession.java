package io.github.himanshusajwan911.sudokuserver.model;

public class WebSocketSession {

	private String sessionId;

	private Player player;

	private String subscribedGameSessionId;

	private String subscriptionId;

	public WebSocketSession() {

	}

	public WebSocketSession(String sessionId, Player player, String subscribedGameSessionId, String subscriptionId) {
		this.sessionId = sessionId;
		this.player = player;
		this.subscribedGameSessionId = subscribedGameSessionId;
		this.subscriptionId = subscriptionId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getSubscribedGameSessionId() {
		return subscribedGameSessionId;
	}

	public void setSubscribedGameSessionId(String subscribedGameSessionId) {
		this.subscribedGameSessionId = subscribedGameSessionId;
	}

	public String getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

}
