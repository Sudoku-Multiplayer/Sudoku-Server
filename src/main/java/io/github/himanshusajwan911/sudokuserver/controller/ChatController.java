package io.github.himanshusajwan911.sudokuserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.himanshusajwan911.sudokuserver.model.GameChatMessage;
import io.github.himanshusajwan911.sudokuserver.service.ChatService;

@RestController
@RequestMapping("chat-server")
public class ChatController {

	@Autowired
	private ChatService chatService;

	@MessageMapping("/{gameId}/chat-message")
	@SendTo("/game-session/{gameId}/chat-message")
	public GameChatMessage handleGameSessionChatMessage(@DestinationVariable String gameId,
			@Payload GameChatMessage chatMessage) throws Exception {

		chatService.addGameSessionChatMessage(gameId, chatMessage);

		return chatMessage;
	}

	@CrossOrigin("*")
	@GetMapping("/game/chat")
	public ResponseEntity<List<GameChatMessage>> getGameChat(
			@RequestParam(name = "gameId", required = true) String gameId) {
		List<GameChatMessage> gameChat = chatService.getGameChat(gameId);

		return new ResponseEntity<>(gameChat, HttpStatus.OK);
	}

}
