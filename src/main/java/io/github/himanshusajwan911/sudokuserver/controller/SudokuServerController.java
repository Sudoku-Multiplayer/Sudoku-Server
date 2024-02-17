package io.github.himanshusajwan911.sudokuserver.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.himanshusajwan911.sudokuserver.exception.InvalidLevelException;
import io.github.himanshusajwan911.sudokuserver.service.SudokuService;
import io.github.himanshusajwan911.sudokuserver.service.SudokuService.Level;

@RestController
@RequestMapping("/sudoku-server")
public class SudokuServerController {

	@Autowired
	private SudokuService sudokuService;

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

		return new ResponseEntity<>(sudokuService.generateSudokuBoard(boardSize, levelEnum), HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@GetMapping("/generate-board-by-cell-remove")
	public ResponseEntity<int[][]> generateSudokuBoard(
			@RequestParam(name = "boardSize", defaultValue = "9") int boardSize,
			@RequestParam(name = "cellRemove", defaultValue = "0") int cellRemove) {

		return new ResponseEntity<>(sudokuService.generateSudokuBoard(boardSize, cellRemove), HttpStatus.OK);
	}

}
