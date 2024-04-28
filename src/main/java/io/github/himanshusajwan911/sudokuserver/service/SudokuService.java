package io.github.himanshusajwan911.sudokuserver.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import io.github.himanshusajwan911.sudokuserver.model.GameBoard;
import io.github.himanshusajwan911.sudokuserver.model.SudokuGame;
import io.github.himanshusajwan911.sudokuserver.util.Util;

@Service
public class SudokuService {

	public enum Level {
		EASY(15), MEDIUM(25), HARD(40), VERY_HARD(60), INSANE(70);

		private final int cellRemovePercent;

		Level(int value) {
			this.cellRemovePercent = value;
		}

		public int getCellRemovePercent() {
			return cellRemovePercent;
		}
	}

	public SudokuGame generateSudokuGame(int boardSize, Level level, int playerLimit, int timeLimitSeconds) {

		GameBoard gameBoard = generateSudokuBoard(boardSize, level);
		int[][] currentBoard = Util.clone2DArray(gameBoard.getBoard());
		SudokuGame sudokuGame = new SudokuGame(gameBoard.getBoard(), currentBoard, gameBoard.getSolution(), level,
				playerLimit, timeLimitSeconds);

		return sudokuGame;
	}

	public GameBoard generateSudokuBoard(int boardSize, Level level) {

		int cellCount = boardSize * boardSize;

		int cellToRemove = (int) (0.01d * level.getCellRemovePercent() * cellCount);

		return generateSudokuBoard(boardSize, cellToRemove);
	}

	public GameBoard generateSudokuBoard(int boardSize, int cellRemoveCount) {

		int[][] solvedBoard = generateSolvedSudokuBoard(boardSize);

		int[][] removedBoard = Util.clone2DArray(solvedBoard);

		removeRandomCells(removedBoard, cellRemoveCount);

		GameBoard gameBoard = new GameBoard(removedBoard, solvedBoard);

		return gameBoard;
	}

	protected int[][] generateSolvedSudokuBoard(int boardSize) {

		int[][] board = new int[boardSize][boardSize];

		if (boardSize < 9) {
			fillBoardRow(board);
		} else {

			int random = Util.getRandomInt(0, 3);

			if (random == 0) {
				fillBoardDiagonal(board);
			} else if (random == 1) {
				fillBoardRow(board);
			} else {
				fillBoardColumn(board);
			}
		}

		solveSudokuBoard(board);

		return board;
	}

	protected void fillBoardRow(int[][] board) {
		List<Integer> initialRowFiller = new ArrayList<>();
		for (int i = 1; i <= board.length; ++i) {
			initialRowFiller.add(i);
		}

		int randomRowNumber = Util.getRandomInt(0, board.length);

		Collections.shuffle(initialRowFiller);

		for (int j = 0; j < board.length; ++j) {
			board[randomRowNumber][j] = initialRowFiller.get(j);
		}
	}

	protected void fillBoardColumn(int[][] board) {
		List<Integer> initialColumnFiller = new ArrayList<>();
		for (int i = 1; i <= board.length; ++i) {
			initialColumnFiller.add(i);
		}

		int randomColumnNumber = Util.getRandomInt(0, board.length);

		Collections.shuffle(initialColumnFiller);

		for (int i = 0; i < board.length; ++i) {
			board[i][randomColumnNumber] = initialColumnFiller.get(i);
		}
	}

	private void fillBoardDiagonal(int[][] board) {

		int sqrtBoardSize = (int) Math.sqrt(board.length);

		for (int i = 0; i < board.length; i += sqrtBoardSize) {
			fillBox(board, i, i);
		}
	}

	private void fillBox(int[][] board, int row, int col) {

		List<Integer> boxFiller = new ArrayList<>();
		for (int i = 1; i <= board.length; ++i) {
			boxFiller.add(i);
		}

		Collections.shuffle(boxFiller);

		int sqrtBoardSize = (int) Math.sqrt(board.length);
		int boxFillerIndex = 0;
		int boxRowStartIndex = row;
		int boxRowEndIndex = boxRowStartIndex + sqrtBoardSize;
		int boxColStartIndex = col;
		int boxColEndIndex = boxColStartIndex + sqrtBoardSize;

		for (int i = boxRowStartIndex; i < boxRowEndIndex; i++) {
			for (int j = boxColStartIndex; j < boxColEndIndex; j++) {

				int cellValue = boxFiller.get(boxFillerIndex);
				board[i][j] = cellValue;
				++boxFillerIndex;
			}
		}
	}

	protected void removeRandomCells(int[][] board, int cellRemoveCount) {

		int low = 0;
		int high = board.length;

		int cellCount = board.length * board.length;
		if (cellRemoveCount > cellCount) {
			cellRemoveCount = cellCount;
		}

		while (cellRemoveCount > 0) {
			int randomRowToDel = Util.getRandomInt(low, high);
			int randomColToDel = Util.getRandomInt(low, high);

			if (board[randomRowToDel][randomColToDel] != 0) {
				board[randomRowToDel][randomColToDel] = 0;
				--cellRemoveCount;
			}
		}
	}

	public boolean solveSudokuBoard(int[][] board) {
		return solveSudokuBoard(board, 0);
	}

	public boolean solveSudokuBoard(int[][] board, int startingRow) {

		for (int i = startingRow; i < board.length; ++i) {
			for (int j = 0; j < board.length; ++j) {
				if (board[i][j] == 0) {
					for (int value = 1; value <= board.length; ++value) {
						if (isValidMove(board, i, j, value)) {
							board[i][j] = value;

							if (solveSudokuBoard(board, i)) {
								return true;
							} else {
								board[i][j] = 0;
							}
						}
					}

					return false;
				}
			}
		}

		return true;
	}

	private boolean isValidMove(int[][] board, int row, int col, int val) {
		return !isUsedInRow(board, row, val) & !isUsedInCol(board, col, val) & !isUsedInBox(board, row, col, val);
	}

	private boolean isUsedInRow(int[][] board, int row, int val) {
		for (int i = 0; i < board.length; ++i) {
			if (board[row][i] == val) {
				return true;
			}
		}

		return false;
	}

	private boolean isUsedInCol(int[][] board, int col, int val) {
		for (int i = 0; i < board.length; ++i) {
			if (board[i][col] == val) {
				return true;
			}
		}

		return false;
	}

	private boolean isUsedInBox(int[][] board, int row, int col, int val) {

		int sqrtBoardSize = (int) Math.sqrt(board.length);

		int boxRowStartIndex = sqrtBoardSize * (row / sqrtBoardSize);
		int boxRowEndIndex = boxRowStartIndex + sqrtBoardSize;
		int boxColStartIndex = sqrtBoardSize * (col / sqrtBoardSize);
		int boxColEndIndex = boxColStartIndex + sqrtBoardSize;

		for (int i = boxRowStartIndex; i < boxRowEndIndex; ++i) {
			for (int j = boxColStartIndex; j < boxColEndIndex; ++j) {
				if (board[i][j] == val) {
					return true;
				}
			}
		}

		return false;
	}

}
