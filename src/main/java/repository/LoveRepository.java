package repository;

import entity.Love;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LoveRepository extends JpaRepository<Love, Long>, JpaSpecificationExecutor<Love> {

    List<Love> findByPlayerUsername(String username);

    List<Love> findByGameName(String gameName);

    long countByGameName(String gameName);

    boolean existsByPlayerUsernameAndGameName(String username, String gameName);

    List<Love> findByLovedAtBetween(LocalDateTime from, LocalDateTime to);
}

