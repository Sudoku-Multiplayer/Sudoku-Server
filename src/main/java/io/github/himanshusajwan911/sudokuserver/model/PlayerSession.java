package io.github.himanshusajwan911.sudokuserver.model;

import java.util.Objects;

public class PlayerSession {

	private Player player;

	private VoteStatus voteStatus;

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public VoteStatus getVoteStatus() {
		return voteStatus;
	}

	public void setVoteStatus(VoteStatus voteStatus) {
		this.voteStatus = voteStatus;
	}

	public void resetVoteStatus() {
		setVoteStatus(VoteStatus.WAITING);
	}

	@Override
	public int hashCode() {
		return Objects.hash(player, voteStatus);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerSession other = (PlayerSession) obj;
		return Objects.equals(player, other.player) && voteStatus == other.voteStatus;
	}

	@Override
	public String toString() {
		return "PlayerSession [player=" + player + ", voteStatus=" + voteStatus + "]";
	}

}
