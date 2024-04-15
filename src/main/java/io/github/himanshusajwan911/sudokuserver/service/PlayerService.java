package io.github.himanshusajwan911.sudokuserver.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.himanshusajwan911.sudokuserver.entity.PlayerEntity;
import io.github.himanshusajwan911.sudokuserver.exception.InvalidCredentialException;
import io.github.himanshusajwan911.sudokuserver.exception.NoSuchPlayerExistsException;
import io.github.himanshusajwan911.sudokuserver.model.Player;
import io.github.himanshusajwan911.sudokuserver.model.Player.PlayerType;
import io.github.himanshusajwan911.sudokuserver.repository.PlayerRepository;

@Service
public class PlayerService {

	@Autowired
	private PlayerRepository playerRepository;

	private AtomicInteger guestPlayerIdAtomicInteger = new AtomicInteger(1);

	private Map<Integer, Player> guestPlayerMap = new HashMap<>();

	public Player loginPlayer(String email, String password) {

		Optional<PlayerEntity> optionalPlayerEntity = getPlayerByEmail(email);

		PlayerEntity playerEntity = optionalPlayerEntity
				.orElseThrow(() -> new NoSuchPlayerExistsException("Player with email " + email + " does not exists."));

		Player player;
		if (password.equals(playerEntity.getPassword())) {
			player = new Player(playerEntity.getPlayerName(), playerEntity.getId(), PlayerType.REGULAR);
		} else {
			throw new InvalidCredentialException("Invalid password");
		}

		return player;
	}

	public PlayerEntity signupPlayer(PlayerEntity player) {
		return playerRepository.save(player);
	}

	public List<PlayerEntity> getPlayers() {
		return playerRepository.findAll();
	}

	public Optional<PlayerEntity> getPlayerById(int id) {
		return playerRepository.findById(id);
	}

	public Optional<PlayerEntity> getPlayerByEmail(String email) {
		return playerRepository.findByEmail(email);
	}

	public PlayerEntity updatePlayer(PlayerEntity player) {
		Optional<PlayerEntity> optionalPlayerEntity = playerRepository.findById(player.getId());
		PlayerEntity playerEntity = optionalPlayerEntity.orElseThrow(
				() -> new NoSuchPlayerExistsException("Player with id " + player.getId() + " does not exists."));

		playerEntity.setPlayerName(player.getPlayerName());

		return playerRepository.save(playerEntity);
	}

	public void deletePlayer(PlayerEntity playerEntity) {
		playerRepository.delete(playerEntity);
	}

	public Player generateGuestPlayer(String playerName) {

		int guestPlayerId = generateGuestPlayerId(playerName);
		Player guestPlayer = new Player(playerName, guestPlayerId, PlayerType.GUEST);
		guestPlayerMap.put(guestPlayerId, guestPlayer);

		return guestPlayer;
	}

	public int generateGuestPlayerId(String playerName) {
		return guestPlayerIdAtomicInteger.getAndIncrement();
	}

}
