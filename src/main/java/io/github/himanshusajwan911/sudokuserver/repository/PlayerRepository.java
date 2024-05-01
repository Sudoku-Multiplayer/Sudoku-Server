package io.github.himanshusajwan911.sudokuserver.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.himanshusajwan911.sudokuserver.entity.PlayerEntity;

public interface PlayerRepository extends JpaRepository<PlayerEntity, Integer> {

	boolean existsByEmail(String email);

	Optional<PlayerEntity> findByEmail(String email);

}
