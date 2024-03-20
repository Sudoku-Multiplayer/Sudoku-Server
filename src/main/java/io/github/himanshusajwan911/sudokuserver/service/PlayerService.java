package io.github.himanshusajwan911.sudokuserver.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import io.github.himanshusajwan911.sudokuserver.model.Player;
import io.github.himanshusajwan911.sudokuserver.model.Player.PlayerType;

@Service
public class PlayerService {

	public Player generateGuestPlayer(String playerName) {

		long guestPlayerId = generateGuestPlayerId(playerName);

		Player guestPlayer = new Player(playerName, guestPlayerId, PlayerType.GUEST);

		return guestPlayer;
	}

	public static long generateGuestPlayerId(String playerName) {
		UUID uuid = UUID.randomUUID();

		return uuid.getLeastSignificantBits();
	}

}
