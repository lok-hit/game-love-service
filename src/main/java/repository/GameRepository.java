package repository;

import entity.Game;
import jakarta.persistence.LockModeType;
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

    Optional<Game> findByName(String name);

    boolean existsByName(String name);

    @Query("SELECT g FROM Game g WHERE LOWER(g.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Game> searchByName(String keyword);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT g FROM Game g WHERE LOWER(g.name) = LOWER(:name)")
    Optional<Game> findByNameIgnoreCaseForUpdate(@Param("name") String name);


}


