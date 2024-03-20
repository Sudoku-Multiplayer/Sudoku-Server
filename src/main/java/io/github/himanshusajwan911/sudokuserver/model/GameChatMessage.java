package io.github.himanshusajwan911.sudokuserver.model;

public class GameChatMessage {

	private Player player;

	private String message;

	public GameChatMessage(Player player, String message) {
		super();
		this.player = player;
		this.message = message;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "GameChatMessage [player=" + player + ", message=" + message + "]";
	}

}
