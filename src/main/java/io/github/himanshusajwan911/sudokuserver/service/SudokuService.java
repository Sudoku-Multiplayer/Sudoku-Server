package io.github.himanshusajwan911.sudokuserver.service;

import org.springframework.stereotype.Service;

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
	
	
}
