package io.github.himanshusajwan911.sudokuserver.model;

import java.util.Objects;

public class Player {

	public enum PlayerType {
		REGULAR, GUEST
	}

	private String name;
	private int id;
	private PlayerType playerType;

	public Player(String name, int id, PlayerType playerType) {
		this.name = name;
		this.id = id;
		this.playerType = playerType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PlayerType getPlayerType() {
		return playerType;
	}

	public void setPlayerType(PlayerType playerType) {
		this.playerType = playerType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return id == other.id && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Player [name=" + name + ", id=" + id + ", playerType=" + playerType + "]";
	}

}
