package io.github.himanshusajwan911.sudokuserver.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.himanshusajwan911.sudokuserver.dto.PlayerEntityDTO;
import io.github.himanshusajwan911.sudokuserver.entity.PlayerEntity;
import io.github.himanshusajwan911.sudokuserver.exception.MissingParameterException;
import io.github.himanshusajwan911.sudokuserver.exception.NoSuchPlayerExistsException;
import io.github.himanshusajwan911.sudokuserver.model.LoginRequest;
import io.github.himanshusajwan911.sudokuserver.model.Player;
import io.github.himanshusajwan911.sudokuserver.service.PlayerService;

@RestController
@RequestMapping("player-server")
public class PlayerController {

	@Autowired
	PlayerService playerService;

	@CrossOrigin("*")
	@PostMapping("/login")
	public ResponseEntity<Player> loginPlayer(@RequestBody LoginRequest loginRequest) {
		Player loggedInPlayer = playerService.loginPlayer(loginRequest.getEmail(), loginRequest.getPassword());

		return new ResponseEntity<>(loggedInPlayer, HttpStatus.ACCEPTED);
	}

	@CrossOrigin("*")
	@PostMapping("/signup")
	public ResponseEntity<PlayerEntityDTO> signupPlayer(@RequestBody PlayerEntity playerEntity) {

		PlayerEntity receivedPlayerEntity = playerService.signupPlayer(playerEntity);
		PlayerEntityDTO playerEntityDTO = new PlayerEntityDTO(receivedPlayerEntity.getId(),
				receivedPlayerEntity.getEmail(), receivedPlayerEntity.getPlayerName(),
				receivedPlayerEntity.getCreatedOn(), receivedPlayerEntity.getLastUpdatedOn());

		return new ResponseEntity<>(playerEntityDTO, HttpStatus.CREATED);
	}

	@CrossOrigin("*")
	@GetMapping("/player")
	public ResponseEntity<PlayerEntityDTO> getPlayer(@RequestParam(name = "id", required = false) Integer id,
			@RequestParam(name = "email", required = false) String email) {

		PlayerEntityDTO playerEntityDTO = null;

		if (id != null) {
			System.out.println("get play for id: " + id);
			Optional<PlayerEntity> playerEntityOptional = playerService.getPlayerById(id);

			PlayerEntity playerEntity = playerEntityOptional
					.orElseThrow(() -> new NoSuchPlayerExistsException("Player with id " + id + " does not exists."));

			playerEntityDTO = new PlayerEntityDTO(playerEntity.getId(), playerEntity.getEmail(),
					playerEntity.getPlayerName(), playerEntity.getCreatedOn(), playerEntity.getLastUpdatedOn());

		}

		else if (email != null) {
			Optional<PlayerEntity> playerEntityOptional = playerService.getPlayerByEmail(email);

			PlayerEntity playerEntity = playerEntityOptional.orElseThrow(
					() -> new NoSuchPlayerExistsException("Player with email " + email + " does not exists."));

			playerEntityDTO = new PlayerEntityDTO(playerEntity.getId(), playerEntity.getEmail(),
					playerEntity.getPlayerName(), playerEntity.getCreatedOn(), playerEntity.getLastUpdatedOn());

		}

		else {
			throw new MissingParameterException("ID or email parameter is required.");
		}

		return new ResponseEntity<>(playerEntityDTO, HttpStatus.OK);
	}

	@CrossOrigin("*")
	@GetMapping("/players")
	public ResponseEntity<List<PlayerEntityDTO>> getPlayers() {

		List<PlayerEntityDTO> playerEntityDTOs = new ArrayList<>();
		List<PlayerEntity> playerEntities = playerService.getPlayers();

		playerEntities.forEach(playerEntity -> {
			playerEntityDTOs.add(new PlayerEntityDTO(playerEntity.getId(), playerEntity.getEmail(),
					playerEntity.getPlayerName(), playerEntity.getCreatedOn(), playerEntity.getLastUpdatedOn()));
		});

		return new ResponseEntity<>(playerEntityDTOs, HttpStatus.OK);
	}

	@CrossOrigin("*")
	@PutMapping("/player")
	public ResponseEntity<PlayerEntityDTO> updatePlayer(@RequestBody PlayerEntity playerEntity) {

		PlayerEntity receivedPlayerEntity = playerService.updatePlayer(playerEntity);

		PlayerEntityDTO playerEntityDTO = new PlayerEntityDTO(receivedPlayerEntity.getId(),
				receivedPlayerEntity.getEmail(), receivedPlayerEntity.getPlayerName(),
				receivedPlayerEntity.getCreatedOn(), receivedPlayerEntity.getLastUpdatedOn());

		return new ResponseEntity<>(playerEntityDTO, HttpStatus.OK);
	}

	@CrossOrigin("*")
	@DeleteMapping("/player")
	public void deletePlayer(@RequestBody PlayerEntity playerEntity) {
		playerService.deletePlayer(playerEntity);
	}

	@CrossOrigin("*")
	@GetMapping("/login-guest-player")
	public ResponseEntity<Player> loginGuestPlayer(@RequestParam("playerName") String playerName) {
		return new ResponseEntity<Player>(playerService.generateGuestPlayer(playerName), HttpStatus.CREATED);
	}

}
