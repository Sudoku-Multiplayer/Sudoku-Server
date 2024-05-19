package io.github.himanshusajwan911.sudokuserver.async;

import io.github.himanshusajwan911.sudokuserver.model.GameSession;
import io.github.himanshusajwan911.sudokuserver.model.GameSessionStatus;
import io.github.himanshusajwan911.sudokuserver.service.NotificationService;

public class GameSessionRunner implements Runnable {

	private final Object gameSessionLock = new Object();

	private GameSession gameSession;
	private boolean isStopped;
	private boolean isPaused;

	private final NotificationService notificationService;

	public GameSessionRunner(GameSession gameSession, NotificationService notificationService) {
		this.gameSession = gameSession;
		this.notificationService = notificationService;
	}

	@Override
	public void run() {

		synchronized (gameSessionLock) {

			while (!isStopped) {

				while (isPaused) {
					try {
						gameSessionLock.wait();
					} catch (InterruptedException e) {
						isStopped = true;
						gameSession.setGameSessionStatus(GameSessionStatus.FINISHED);
						notificationService.notifyForGameSessionStatusUpdate(gameSession.getSessionId(),
								gameSession.getGameSessionStatus());

						e.printStackTrace();
					}
				}

				notificationService.notifyForGameSessionTimeUpdate(gameSession.getSessionId(),
						gameSession.getRemainingTime());

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				gameSession.decreaseRemainingTime(1);

				if (gameSession.getVoteSession().getVoteCooldownTimer() > 0) {
					gameSession.getVoteSession().decreaseVoteCooldownTimer(1);
				}

				if (gameSession.getRemainingTime() <= 0) {
					gameSession.setGameSessionStatus(GameSessionStatus.FINISHED);
					notificationService.notifyForGameSessionStatusUpdate(gameSession.getSessionId(),
							gameSession.getGameSessionStatus());
					isStopped = true;

					break;
				}
			}
		}
	}

	public void pauseGame() {
		isPaused = true;
		gameSession.setGameSessionStatus(GameSessionStatus.PAUSED);
		notificationService.notifyForGameSessionStatusUpdate(gameSession.getSessionId(),
				gameSession.getGameSessionStatus());
	}

	public void resumeGame() {

		synchronized (gameSessionLock) {
			gameSessionLock.notify();
			isPaused = false;
			gameSession.setGameSessionStatus(GameSessionStatus.RUNNING);
			notificationService.notifyForGameSessionStatusUpdate(gameSession.getSessionId(),
					gameSession.getGameSessionStatus());
		}
	}

	public void stopGame() {
		isStopped = true;
		gameSession.setGameSessionStatus(GameSessionStatus.FINISHED);
		notificationService.notifyForGameSessionStatusUpdate(gameSession.getSessionId(),
				gameSession.getGameSessionStatus());
	}

}
