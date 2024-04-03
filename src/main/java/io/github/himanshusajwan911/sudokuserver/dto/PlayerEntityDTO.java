package io.github.himanshusajwan911.sudokuserver.dto;

import java.time.LocalDateTime;

public class PlayerEntityDTO {

	private Integer id;

	private String email;

	private String playerName;

	private LocalDateTime createdOn;

	private LocalDateTime lastUpdatedOn;

	public PlayerEntityDTO(Integer id, String email, String playerName, LocalDateTime createdOn,
			LocalDateTime lastUpdatedOn) {
		super();
		this.id = id;
		this.email = email;
		this.playerName = playerName;
		this.createdOn = createdOn;
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public Integer getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public LocalDateTime getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(LocalDateTime lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

}
