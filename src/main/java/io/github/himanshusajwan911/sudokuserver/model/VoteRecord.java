package io.github.himanshusajwan911.sudokuserver.model;

public class VoteRecord {

	private Player voter;

	private VoteStatus voteStatus;

	public VoteRecord() {

	}

	public VoteRecord(Player voter, VoteStatus voteStatus) {
		this.voter = voter;
		this.voteStatus = voteStatus;
	}

	public Player getVoter() {
		return voter;
	}

	public void setVoter(Player voter) {
		this.voter = voter;
	}

	public VoteStatus getVoteStatus() {
		return voteStatus;
	}

	public void setVoteStatus(VoteStatus voteStatus) {
		this.voteStatus = voteStatus;
	}

	@Override
	public String toString() {
		return "VoteRecord [voter=" + voter + ", voteStatus=" + voteStatus + "]";
	}

}
