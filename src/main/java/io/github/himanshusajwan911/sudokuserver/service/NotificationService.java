package io.github.himanshusajwan911.sudokuserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import io.github.himanshusajwan911.sudokuserver.model.BoardUpdate;
import io.github.himanshusajwan911.sudokuserver.model.GameSessionStatus;
import io.github.himanshusajwan911.sudokuserver.model.Player;
import io.github.himanshusajwan911.sudokuserver.model.VoteRecord;

@Service
public class NotificationService {

	@Autowired
	SimpMessagingTemplate simpMessagingTemplate;

	private String gameSessionBrokerPath = "/game-session";

	public void notifyForGameSessionPlayerJoined(String gameId, Player player) {
		String destination = gameSessionBrokerPath + "/" + gameId + "/player-joined";
		simpMessagingTemplate.convertAndSend(destination, player);
	}

	public void notifyForGameSessionPlayerLeft(String gameId, Player player) {
		String destination = gameSessionBrokerPath + "/" + gameId + "/player-left";
		simpMessagingTemplate.convertAndSend(destination, player);
	}

	public void notifyForGameSessionTimeUpdate(String gameId, int updatedTime) {
		String destination = gameSessionBrokerPath + "/" + gameId + "/time-update";
		simpMessagingTemplate.convertAndSend(destination, updatedTime);
	}

	public void notifyForGameSessionStatusUpdate(String gameId, GameSessionStatus gameSessionStatus) {
		String destination = gameSessionBrokerPath + "/" + gameId + "/status-update";
		simpMessagingTemplate.convertAndSend(destination, gameSessionStatus);
	}

	public void notifyForGameSessionBoardUpdate(String gameId, BoardUpdate boardUpdate) {
		String destination = gameSessionBrokerPath + "/" + gameId + "/board-update";
		simpMessagingTemplate.convertAndSend(destination, boardUpdate);
	}

	public void notifyForGameSessionMessageUpdate(String gameId, String message) {
		String destination = gameSessionBrokerPath + "/" + gameId + "/message-update";
		simpMessagingTemplate.convertAndSend(destination, message);
	}

	public void notifyForGameSessionSubmissionVoteInitiated(String gameId, Player player) {
		String destination = gameSessionBrokerPath + "/" + gameId + "/vote-initiated";
		simpMessagingTemplate.convertAndSend(destination, player);
	}

	public void notifyForGameSessionSubmissionVoteCasted(String gameId, VoteRecord voteRecord) {
		String destination = gameSessionBrokerPath + "/" + gameId + "/vote-casted";
		simpMessagingTemplate.convertAndSend(destination, voteRecord);
	}

}
