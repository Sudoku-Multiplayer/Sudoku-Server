package io.github.himanshusajwan911.sudokuserver.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoteSession {

	public final static int DEFAULT_VOTE_COOLDOWN_TIME = 20;

	private Player voteInitiator;

	private Map<Player, VoteRecord> votersMap;

	private int voteCooldownTime;

	private int voteCooldownTimer;

	private int acceptedCount;

	private int rejectedCount;

	public VoteSession() {
		this.votersMap = new HashMap<>();
		this.voteCooldownTime = DEFAULT_VOTE_COOLDOWN_TIME;
		this.voteCooldownTimer = DEFAULT_VOTE_COOLDOWN_TIME;
	}

	public VoteSession(int voteCooldownTime) {
		super();
		this.votersMap = new HashMap<>();
		this.voteCooldownTime = voteCooldownTime;
		this.voteCooldownTimer = voteCooldownTime;
	}

	public Player getVoteInitiator() {
		return voteInitiator;
	}

	public void setVoteInitiator(Player voteInitiator) {
		this.voteInitiator = voteInitiator;
	}

	public List<Player> getVoters() {
		return new ArrayList<>(votersMap.keySet());
	}

	public void addVoter(Player player) {
		VoteRecord voteRecord = new VoteRecord(player, VoteStatus.WAITING);
		votersMap.put(player, voteRecord);
	}

	public void removeVoter(Player player) {
		votersMap.remove(player);
	}

	public VoteRecord getVoteRecord(Player player) {
		return votersMap.get(player);
	}

	public List<VoteRecord> getVoteRecords() {
		return new ArrayList<>(votersMap.values());
	}

	public int getVoteCooldownTimer() {
		return voteCooldownTimer;
	}

	public void increaseVoteCooldownTimer(int increaseBy) {
		voteCooldownTimer += increaseBy;
	}

	public void decreaseVoteCooldownTimer(int decreaseBy) {
		voteCooldownTimer -= decreaseBy;
	}

	public void resetVoteCooldownTimer() {
		voteCooldownTimer = voteCooldownTime;
	}

	public int getAcceptedCount() {
		return acceptedCount;
	}

	public int getRejectedCount() {
		return rejectedCount;
	}

	public void resetVoteRecords() {
		acceptedCount = 0;
		rejectedCount = 0;

		for (VoteRecord voteRecord : votersMap.values()) {
			voteRecord.setVoteStatus(VoteStatus.WAITING);
		}
	}

	public void castVote(VoteRecord voteRecord) {

		votersMap.put(voteRecord.getVoter(), voteRecord);

		if (voteRecord.getVoteStatus() == VoteStatus.ACCEPTED) {
			++acceptedCount;
		}

		else if (voteRecord.getVoteStatus() == VoteStatus.ACCEPTED) {
			++rejectedCount;
		}

	}

	public boolean isVoterInList(Player voter) {
		return votersMap.containsKey(voter);
	}

}
