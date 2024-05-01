package io.github.himanshusajwan911.sudokuserver.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import io.github.himanshusajwan911.sudokuserver.model.SudokuGame;

@Repository
public class GameRepository {

	Map<String, SudokuGame> gamesMap = new HashMap<>();

	public void addGame(String gameId, SudokuGame sudokuGame) {
		gamesMap.put(gameId, sudokuGame);
	}

	public void removeGame(String gameId) {
		gamesMap.remove(gameId);
	}

	public SudokuGame getGame(String gameId) {
		return gamesMap.get(gameId);
	}

	public List<SudokuGame> getGames() {
		return new ArrayList<SudokuGame>(gamesMap.values());
	}

}
