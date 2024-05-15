package io.github.himanshusajwan911.sudokuserver.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.github.himanshusajwan911.sudokuserver.async.GameSessionRunner;
import io.github.himanshusajwan911.sudokuserver.exception.GameFinishedException;
import io.github.himanshusajwan911.sudokuserver.exception.GameNotStartedException;
import io.github.himanshusajwan911.sudokuserver.model.SudokuGame.SudokuGameStatus;
import io.github.himanshusajwan911.sudokuserver.service.NotificationService;

public class GameSession {

	private SudokuGame game;

	private GameSessionRunner gameSessionRunner;
	private Thread gameThread;

	private NotificationService notificationService;

	private List<GameChatMessage> gameChatMessages;
	private List<BoardUpdate> boardUpdates;

	private Map<Player, PlayerSession> playerSessionMap;

	public GameSession(SudokuGame game, NotificationService notificationService) {
		this.game = game;
		this.notificationService = notificationService;
		gameSessionRunner = new GameSessionRunner(game, notificationService);
		gameThread = new Thread(gameSessionRunner);
		this.gameChatMessages = new ArrayList<>();
		this.boardUpdates = new ArrayList<>();
		this.playerSessionMap = new LinkedHashMap<>();
	}

	public SudokuGame getGame() {
		return game;
	}

	public GameSessionRunner getGameSessionRunner() {
		return gameSessionRunner;
	}

	public Thread getGameThread() {
		return gameThread;
	}

	public void startGame() {
		if (game.getStatus() == SudokuGameStatus.FINISHED) {
			throw new GameFinishedException("Game is already finished.");
		} else {
			if (game.getStatus() == SudokuGameStatus.NEW) {
				game.setStatus(SudokuGameStatus.RUNNING);
				notificationService.notifyForGameSessionStatusUpdate(game.getGameId(), game.getStatus());
				gameThread.start();
			}
		}
	}

	public void stopGame() {
		gameSessionRunner.stopGame();
	}

	public void pauseGame() {
		if (game.getStatus() == SudokuGameStatus.NEW) {
			notificationService.notifyForGameSessionMessageUpdate(game.getGameId(),
					"Cannot pause, Game is not started yet.");
		} else if (game.getStatus() == SudokuGameStatus.FINISHED) {
			notificationService.notifyForGameSessionMessageUpdate(game.getGameId(), "Cannot pause, Game is Finished.");
		} else {
			gameSessionRunner.pauseGame();
		}
	}

	public void resumeGame() {
		if (game.getStatus() == SudokuGameStatus.NEW) {
			notificationService.notifyForGameSessionMessageUpdate(game.getGameId(),
					"Cannot resume, Game is not started yet.");
		}

		else if (game.getStatus() == SudokuGameStatus.FINISHED) {
			notificationService.notifyForGameSessionMessageUpdate(game.getGameId(), "Cannot resume, Game is Finished.");
		}

		else {
			if (game.getStatus() == SudokuGameStatus.PAUSED) {
				gameSessionRunner.resumeGame();
			}
		}
	}

	public void addPlayer(Player player) {
		PlayerSession playerSession = new PlayerSession();
		playerSession.setPlayer(player);
		playerSession.setVoteStatus(VoteStatus.WAITING);

		playerSessionMap.put(player, playerSession);
	}

	public boolean removePlayer(Player player) {
		PlayerSession playerSession = playerSessionMap.remove(player);
		Player removedPlayer = playerSession.getPlayer();

		return player.equals(removedPlayer);
	}

	public List<Player> getJoinedPlayers() {
		return new ArrayList<Player>(playerSessionMap.keySet());
	}

	public int getPlayerCount() {
		return playerSessionMap.size();
	}

	public void addBoardUpdate(BoardUpdate boardUpdate) {
		boardUpdates.add(boardUpdate);
	}

	public List<BoardUpdate> getBoardUpdates() {
		return boardUpdates;
	}

	public void addGameChatMessage(GameChatMessage gameChatMessage) {
		gameChatMessages.add(gameChatMessage);
	}

	public List<GameChatMessage> getGameChatMessages() {
		return gameChatMessages;
	}

}
