package io.github.himanshusajwan911.sudokuserver.service;

import io.github.himanshusajwan911.sudokuserver.async.GameSessionRunner;
import io.github.himanshusajwan911.sudokuserver.exception.GameFinishedException;
import io.github.himanshusajwan911.sudokuserver.exception.GameNotStartedException;
import io.github.himanshusajwan911.sudokuserver.exception.VoteInitializationException;
import io.github.himanshusajwan911.sudokuserver.model.GameSession;
import io.github.himanshusajwan911.sudokuserver.model.GameSessionStatus;
import io.github.himanshusajwan911.sudokuserver.model.Player;
import io.github.himanshusajwan911.sudokuserver.model.VoteRecord;
import io.github.himanshusajwan911.sudokuserver.model.VoteSession;
import io.github.himanshusajwan911.sudokuserver.model.VoteStatus;

public class GameSessionManager {

	private GameSession gameSession;

	private GameSessionRunner gameSessionRunner;
	private Thread gameThread;

	private NotificationService notificationService;

	public GameSessionManager(GameSession gameSession, NotificationService notificationService) {
		this.gameSession = gameSession;
		this.notificationService = notificationService;
		gameSessionRunner = new GameSessionRunner(gameSession, notificationService);
		gameThread = new Thread(gameSessionRunner);
	}

	public void startGame() {

		if (gameSession.getGameSessionStatus() == GameSessionStatus.FINISHED) {
			throw new GameFinishedException("Game is already finished.");
		}

		if (gameSession.getGameSessionStatus() == GameSessionStatus.NEW) {

			gameSession.setGameSessionStatus(GameSessionStatus.RUNNING);

			notificationService.notifyForGameSessionStatusUpdate(gameSession.getSessionId(),
					gameSession.getGameSessionStatus());

			gameThread.start();
		}

	}

	public void stopGame() {
		gameSessionRunner.stopGame();
	}

	public synchronized void pauseGame() {

		if (gameSession.getGameSessionStatus() == GameSessionStatus.NEW) {
			notificationService.notifyForGameSessionMessageUpdate(gameSession.getSessionId(),
					"Cannot pause, Game is not started yet.");
		}

		else if (gameSession.getGameSessionStatus() == GameSessionStatus.FINISHED) {
			notificationService.notifyForGameSessionMessageUpdate(gameSession.getSessionId(),
					"Cannot pause, Game is Finished.");
		}

		else {
			gameSessionRunner.pauseGame();
		}
	}

	public synchronized void resumeGame() {

		if (gameSession.getGameSessionStatus() == GameSessionStatus.NEW) {
			notificationService.notifyForGameSessionMessageUpdate(gameSession.getSessionId(),
					"Cannot resume, Game is not started yet.");
		}

		else if (gameSession.getGameSessionStatus() == GameSessionStatus.FINISHED) {
			notificationService.notifyForGameSessionMessageUpdate(gameSession.getSessionId(),
					"Cannot resume, Game is Finished.");
		}

		else {
			if (gameSession.getGameSessionStatus() == GameSessionStatus.PAUSED) {
				gameSessionRunner.resumeGame();
			}
		}
	}

	public synchronized void initiateGameSubmitVoting(Player player) {

		if (gameSession.getGameSessionStatus() == GameSessionStatus.NEW) {
			throw new GameNotStartedException("cannot initiate Game Submit Voting, game is not Started yet.");
		}

		if (gameSession.getGameSessionStatus() == GameSessionStatus.FINISHED) {
			throw new GameFinishedException("cannot initiate Game Submit Voting, game is already Finsished.");
		}

		VoteSession voteSession = gameSession.getVoteSession();

		if (voteSession.getVoteCooldownTimer() > 0) {
			throw new VoteInitializationException(
					"Voting is in cooldown. Try again in " + voteSession.getVoteCooldownTimer() + " seconds.");
		}

		voteSession.resetVoteCooldownTimer();
		voteSession.resetVoteRecords();
		voteSession.setVoteInitiator(player);

		notificationService.notifyForGameSessionSubmissionVoteInitiated(gameSession.getSessionId(), player);
	}

	public synchronized void castGameSubmitVote(VoteRecord voteRecord) {

		VoteSession voteSession = gameSession.getVoteSession();

		if (voteSession.isVoterInList(voteRecord.getVoter())) {
			VoteRecord existingVoteRecord = voteSession.getVoteRecord(voteRecord.getVoter());

			if (existingVoteRecord.getVoteStatus() == VoteStatus.WAITING) {
				gameSession.getVoteSession().castVote(voteRecord);
			}

			if (isGameOverViaVoting(voteSession)) {

				if (gameSession.getGameSessionStatus() != GameSessionStatus.FINISHED) {
					stopGame();
					notificationService.notifyForGameSessionMessageUpdate(gameSession.getSessionId(),
							"Game Over via voting.");
				}
			}
		}
	}

	public boolean isGameOverViaVoting(VoteSession voteSession) {
		int gameOverThreshold = (int) ((1.0 / 2.0) * gameSession.getPlayerCount());

		if (voteSession.getAcceptedCount() > gameOverThreshold) {
			return true;
		}

		return false;
	}

}
