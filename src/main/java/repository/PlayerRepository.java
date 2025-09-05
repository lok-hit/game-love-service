package repository;


import entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long>, JpaSpecificationExecutor<Player> {

    Optional<Player> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("SELECT p FROM Player p WHERE LOWER(p.username) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Player> searchByUsername(String keyword);
}


