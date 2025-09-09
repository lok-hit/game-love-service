package org.lokhit.gamelove.repository;

import org.lokhit.gamelove.entity.Love;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LoveRepository extends JpaRepository<Love, Long>, JpaSpecificationExecutor<Love> {

    List<Love> findByPlayerUsername(String username);

    List<Love> findByGame_GameName(String gameName);

    long countByGame_GameName(String gameName);

    boolean existsByPlayerUsernameAndGame_GameName(String username, String gameName);


    List<Love> findByLovedAtBetween(LocalDateTime from, LocalDateTime to);
}

