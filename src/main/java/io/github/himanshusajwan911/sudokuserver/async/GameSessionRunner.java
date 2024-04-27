package io.github.himanshusajwan911.sudokuserver.async;

import io.github.himanshusajwan911.sudokuserver.model.SudokuGame;
import io.github.himanshusajwan911.sudokuserver.model.SudokuGame.SudokuGameStatus;
import io.github.himanshusajwan911.sudokuserver.service.NotificationService;

public class GameSessionRunner implements Runnable {

	private SudokuGame game;
	private boolean isStopped;
	private boolean isPaused;

	private final NotificationService notificationService;

	public GameSessionRunner(SudokuGame game, NotificationService notificationService) {
		this.game = game;
		this.notificationService = notificationService;
	}

	@Override
	public void run() {

		synchronized (game) {

			while (!isStopped) {

				while (isPaused) {
					try {
						game.wait();
					} catch (InterruptedException e) {
						isStopped = true;
						game.setStatus(SudokuGameStatus.FINISHED);
						notificationService.notifyForGameSessionStatusUpdate(game.getGameId(), game.getStatus());
						e.printStackTrace();
					}
				}

				System.out.println(game.getGameId() + " runnig." + game.getRemainingTime());
				notificationService.notifyForGameSessionTimeUpdate(game.getGameId(), game.getRemainingTime());

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				game.decreaseRemainingTime(1);

				if (game.getRemainingTime() <= 0) {
					game.setStatus(SudokuGameStatus.FINISHED);
					notificationService.notifyForGameSessionStatusUpdate(game.getGameId(), game.getStatus());
					isStopped = true;
					break;
				}
			}
		}
	}

	public void pauseGame() {
		isPaused = true;
		game.setStatus(SudokuGameStatus.PAUSED);
		notificationService.notifyForGameSessionStatusUpdate(game.getGameId(), game.getStatus());
	}

	public void resumeGame() {

		synchronized (game) {
			game.notify();
			isPaused = false;
			game.setStatus(SudokuGameStatus.RUNNING);
			notificationService.notifyForGameSessionStatusUpdate(game.getGameId(), game.getStatus());
		}
	}

	public void stopGame() {
		isStopped = true;
		game.setStatus(SudokuGameStatus.FINISHED);
		notificationService.notifyForGameSessionStatusUpdate(game.getGameId(), game.getStatus());
	}

}
