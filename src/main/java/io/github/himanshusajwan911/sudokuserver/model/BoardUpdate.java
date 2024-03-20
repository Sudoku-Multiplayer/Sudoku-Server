package io.github.himanshusajwan911.sudokuserver.model;

public class BoardUpdate {

	private Player player;

	public int value;
	public int row;
	public int column;

	public BoardUpdate(int value, int row, int column, Player player) {
		super();
		this.value = value;
		this.row = row;
		this.column = column;
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public String toString() {
		return "BoardUpdate [player=" + player + ", value=" + value + ", row=" + row + ", column=" + column + "]";
	}

}
