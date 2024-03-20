package io.github.himanshusajwan911.sudokuserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.himanshusajwan911.sudokuserver.model.GameChatMessage;
import io.github.himanshusajwan911.sudokuserver.service.GameChatService;

@RestController
public class GameChatController {

	@Autowired
	private GameChatService gameChatService;

	@MessageMapping("/{gameId}/chat")
	@SendTo("/game-updates/{gameId}/chat")
	public GameChatMessage handleGameChat(@DestinationVariable String gameId, @Payload GameChatMessage chatMessage)
			throws Exception {

		gameChatService.addChatMessage(gameId, chatMessage);

		return chatMessage;
	}

	@CrossOrigin("*")
	@GetMapping("/game/chat")
	public List<GameChatMessage> getGameChat(@RequestParam(name = "gameId", required = true) String gameId) {
		List<GameChatMessage> gameChat = gameChatService.getGameChat(gameId);

		return gameChat;
	}

}
