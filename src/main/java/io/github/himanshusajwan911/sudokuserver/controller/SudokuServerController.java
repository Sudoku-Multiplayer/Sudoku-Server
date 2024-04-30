package io.github.himanshusajwan911.sudokuserver.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.himanshusajwan911.sudokuserver.exception.InvalidLevelException;
import io.github.himanshusajwan911.sudokuserver.exception.NoSolutionException;
import io.github.himanshusajwan911.sudokuserver.model.GameBoard;
import io.github.himanshusajwan911.sudokuserver.repository.GameSubscriptionRespository;
import io.github.himanshusajwan911.sudokuserver.service.SudokuService;
import io.github.himanshusajwan911.sudokuserver.service.SudokuService.Level;

@RestController
@RequestMapping("/sudoku-server")
public class SudokuServerController {

	@Autowired
	private SudokuService sudokuService;

	@Autowired
	private GameSubscriptionRespository gameSubscriptionRespository;

	@CrossOrigin(origins = "*")
	@GetMapping("/generate-board-by-level")
	public ResponseEntity<int[][]> generateSudokuBoard(
			@RequestParam(name = "boardSize", defaultValue = "9") int boardSize,
			@RequestParam(name = "level", defaultValue = "EASY") String level) {

		if (level != null) {
			if (!Set.of("EASY", "MEDIUM", "HARD", "VERY_HARD", "INSANE").contains(level.toUpperCase())) {
				throw new InvalidLevelException(
						"Invalid level provided.\nValid levels are: EASY, MEDIUM, HARD, VERY_HARD, INSANE");
			}
		}

		Level levelEnum = Level.valueOf(level.toUpperCase());
		int[][] sudokuBoard = null;
		try {
			sudokuBoard = sudokuService.generateSudokuBoard(boardSize, levelEnum).getBoard();

		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Error in creating a sudoku board of size: " + boardSize);
		}

		return new ResponseEntity<>(sudokuBoard, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/generate-board-solution-by-level")
	public ResponseEntity<GameBoard> generateSudokuBoardAndSolution(
			@RequestParam(name = "boardSize", defaultValue = "9") int boardSize,
			@RequestParam(name = "level", defaultValue = "EASY") String level) {

		if (level != null) {
			if (!Set.of("EASY", "MEDIUM", "HARD", "VERY_HARD", "INSANE").contains(level.toUpperCase())) {
				throw new InvalidLevelException(
						"Invalid level provided.\nValid levels are: EASY, MEDIUM, HARD, VERY_HARD, INSANE");
			}
		}

		Level levelEnum = Level.valueOf(level.toUpperCase());

		GameBoard gameBoard = null;
		try {
			gameBoard = sudokuService.generateSudokuBoard(boardSize, levelEnum);

		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException("Error in creating a sudoku board of size: " + boardSize);
		}

		return new ResponseEntity<>(gameBoard, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/generate-board-by-cell-remove")
	public ResponseEntity<int[][]> generateSudokuBoard(
			@RequestParam(name = "boardSize", defaultValue = "9") int boardSize,
			@RequestParam(name = "cellRemove", defaultValue = "0") int cellRemove) {

		return new ResponseEntity<>(sudokuService.generateSudokuBoard(boardSize, cellRemove).getBoard(), HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/generate-board-solution-by-cell-remove")
	public ResponseEntity<GameBoard> generateSudokuBoardAndSolution(
			@RequestParam(name = "boardSize", defaultValue = "9") int boardSize,
			@RequestParam(name = "cellRemove", defaultValue = "0") int cellRemove) {

		return new ResponseEntity<>(sudokuService.generateSudokuBoard(boardSize, cellRemove), HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@PostMapping("/solve-sudoku-board")
	public ResponseEntity<int[][]> solveSudokuBoard(@RequestBody int[][] sudokuBoard) {

		boolean isSolvable = sudokuService.solveSudokuBoard(sudokuBoard);
		if (!isSolvable) {
			throw new NoSolutionException("No Solution exists for given board.");
		}

		return new ResponseEntity<>(sudokuBoard, HttpStatus.OK);
	}

	@CrossOrigin("*")
	@GetMapping("/online-player-count")
	public ResponseEntity<Integer> getOnlinePlayerCount() {
		System.out.println("getOnlinePlayerCount()");
		System.out.println(gameSubscriptionRespository.getOnlinePlayerCount());
		return new ResponseEntity<Integer>(gameSubscriptionRespository.getOnlinePlayerCount(), HttpStatus.OK);
	}

}
