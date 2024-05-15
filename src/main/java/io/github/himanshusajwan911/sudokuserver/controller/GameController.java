package io.github.himanshusajwan911.sudokuserver.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.himanshusajwan911.sudokuserver.dto.SudokuGameDTO;
import io.github.himanshusajwan911.sudokuserver.model.BoardUpdate;
import io.github.himanshusajwan911.sudokuserver.model.CreateGameRequest;
import io.github.himanshusajwan911.sudokuserver.model.CreateGameResponse;
import io.github.himanshusajwan911.sudokuserver.model.GameSession;
import io.github.himanshusajwan911.sudokuserver.model.JoinGameResponse;
import io.github.himanshusajwan911.sudokuserver.model.Player;
import io.github.himanshusajwan911.sudokuserver.model.SudokuGame;
import io.github.himanshusajwan911.sudokuserver.model.SudokuGame.SudokuGameStatus;
import io.github.himanshusajwan911.sudokuserver.model.VoteRecord;
import io.github.himanshusajwan911.sudokuserver.repository.GameRepository;
import io.github.himanshusajwan911.sudokuserver.service.GameService;
import io.github.himanshusajwan911.sudokuserver.service.GameSessionService;
import io.github.himanshusajwan911.sudokuserver.service.NotificationService;

@RestController
@RequestMapping("game-server")
public class GameController {

	private GameService gameService;

	private GameRepository gameRepository;

	private GameSessionService gameSessionService;

	private NotificationService notificationService;

	public GameController(GameService gameService, GameRepository gameRepository, GameSessionService gameSessionService,
			NotificationService notificationService) {
		this.gameService = gameService;
		this.gameRepository = gameRepository;
		this.gameSessionService = gameSessionService;
		this.notificationService = notificationService;
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/create-game")
	public ResponseEntity<CreateGameResponse> createGame(@RequestBody CreateGameRequest createGameRequest) {

		String gameId = gameService.createGame(createGameRequest);

		CreateGameResponse createGameResponse = new CreateGameResponse();
		createGameResponse.setGameId(gameId);

		SudokuGame game = gameRepository.getGame(gameId);
		GameSession gameSession = gameSessionService.createGameSession(gameId, game, createGameRequest.getTimeLimit(),
				createGameRequest.getPlayerLimit());

		gameSessionService.createGameSessionManager(gameSession);

		return new ResponseEntity<>(createGameResponse, HttpStatus.CREATED);
	}

	@CrossOrigin(origins = "*")
	@DeleteMapping("/delete-game")
	public void deleteGame(@RequestParam String gameId) {
		gameService.removeGame(gameId);
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/join-game")
	public ResponseEntity<JoinGameResponse> joinGame(@RequestBody Player player,
			@RequestParam("gameId") String gameId) {

		JoinGameResponse joinGameResponse = gameSessionService.joinGame(player, gameId);

		if (joinGameResponse.getJoinStatus() == SudokuGameStatus.PLAYER_ADDED
				|| joinGameResponse.getJoinStatus() == SudokuGameStatus.PLAYER_ALREADY_JOINED) {
			notificationService.notifyForGameSessionPlayerJoined(gameId, player);
		}

		return new ResponseEntity<>(joinGameResponse, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/leave-game")
	public boolean leaveGame(@RequestParam("gameId") String gameId, @RequestBody Player player) {
		boolean playerLeft = gameSessionService.leaveGame(player, gameId);

		if (playerLeft) {
			notificationService.notifyForGameSessionPlayerLeft(gameId, player);
		}

		return playerLeft;
	}

	@CrossOrigin(origins = "*")
	@PatchMapping("/start-game")
	public void startGame(@RequestParam("gameId") String gameId) {
		gameSessionService.startGame(gameId);
	}

	@CrossOrigin(origins = "*")
	@PatchMapping("/stop-game")
	public void stopGame(@RequestParam("gameId") String gameId) {
		gameSessionService.stopGame(gameId);
	}

	@CrossOrigin(origins = "*")
	@PatchMapping("/pause-game")
	public void pauseGame(@RequestParam("gameId") String gameId) {
		gameSessionService.pauseGame(gameId);
	}

	@CrossOrigin(origins = "*")
	@PatchMapping("/resume-game")
	public void resumeGame(@RequestParam("gameId") String gameId) {
		gameSessionService.resumeGame(gameId);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/games")
	public ResponseEntity<List<SudokuGameDTO>> getGames() {
		return new ResponseEntity<>(gameService.getGames(), HttpStatus.OK);
	}

	@MessageMapping("/{gameId}/update-board")
	public void handleBoardUpdate(@DestinationVariable String gameId, @Payload BoardUpdate boardUpdate)
			throws Exception {

		gameSessionService.updateBoard(gameId, boardUpdate);
	}

	@CrossOrigin("*")
	@GetMapping("/game/board-updates")
	public ResponseEntity<List<BoardUpdate>> getBoardUpdates(
			@RequestParam(name = "gameId", required = true) String gameId) {
		List<BoardUpdate> boardUpdates = gameSessionService.getBoardUpdates(gameId);

		return new ResponseEntity<>(boardUpdates, HttpStatus.OK);
	}

	@CrossOrigin("*")
	@GetMapping("/game/joined-players")
	public ResponseEntity<List<Player>> getJoinedPlayers(
			@RequestParam(name = "gameId", required = true) String gameId) {

		return new ResponseEntity<>(gameSessionService.getJoinedPlayers(gameId), HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@PatchMapping("/game-submit-voting/initiate")
	public ResponseEntity<Boolean> initiateGameSubmitVoting(@RequestParam("gameId") String gameId,
			@RequestBody Player player) {
		gameSessionService.initiateGameSubmitVoting(gameId, player);

		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}

	@MessageMapping("/{gameId}/cast-vote")
	public void handleGameSubmitVoteCasted(@DestinationVariable String gameId, @Payload VoteRecord voteRecord)
			throws Exception {

		gameSessionService.castGameSubmitVote(gameId, voteRecord);
	}

}
