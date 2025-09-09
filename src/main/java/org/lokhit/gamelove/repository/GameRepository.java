package org.lokhit.gamelove.repository;

import jakarta.persistence.LockModeType;
import org.lokhit.gamelove.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long>, JpaSpecificationExecutor<Game> {

    Optional<Game> findByGameName(String gameName);

    boolean existsByGameName(String gameName);

    @Query("SELECT g FROM Game g WHERE LOWER(g.gameName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Game> searchByName(@Param("name") String name);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT g FROM Game g WHERE LOWER(g.gameName) = LOWER(:name)")
    Optional<Game> findByGameNameIgnoreCaseForUpdate(@Param("name") String name);

    @Query("SELECT g FROM Game g LEFT JOIN g.loves l GROUP BY g.gameId ORDER BY COUNT(l) DESC")
    List<Game> findAllOrderByLoveCountDesc();

    @Query("SELECT g FROM Game g LEFT JOIN g.loves l GROUP BY g.gameId ORDER BY COUNT(l) DESC")
    Page<Game> findAllOrderByLoveCountDesc(Pageable pageable);
}