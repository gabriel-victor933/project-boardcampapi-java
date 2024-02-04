package com.boardcamp.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.boardcamp.api.models.RentalsModel;

public interface RentalsRepository extends JpaRepository<RentalsModel, Long> {
    @Query(value = "SELECT count(*) FROM rentals WHERE game_id = :gameId", nativeQuery = true)
    int countRentalsByGameId(@Param("gameId") Long postId);
}
