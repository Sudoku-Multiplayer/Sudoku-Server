package io.github.himanshusajwan911.sudokuserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import io.github.himanshusajwan911.sudokuserver.model.Player;

@Service
public class NotificationService {

	@Autowired
	SimpMessagingTemplate simpMessagingTemplate;

	public void notifyGameSessionForPlayerJoined(String gameId, Player player) {
		simpMessagingTemplate.convertAndSend("/game-updates/" + gameId + "/player-joined", player);
	}

	public void notifyGameSessionForPlayerLeft(String gameId, Player player) {
		simpMessagingTemplate.convertAndSend("/game-updates/" + gameId + "/player-left", player);
	}

}
