package io.github.himanshusajwan911.sudokuserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.himanshusajwan911.sudokuserver.service.SudokuService;

@RestController
@RequestMapping("/sudoku-server")
public class SudokuServerController {

	@Autowired
	private SudokuService sudokuService;
	
	
	@GetMapping("/generate-random-board")
	public ResponseEntity<int[][]> getRandomBoard(@RequestParam(name = "boardSize", defaultValue = "9") int boardSize){
		
		return new ResponseEntity<>(sudokuService.generateRandomBoard(boardSize), HttpStatus.OK);
	}
	
}
