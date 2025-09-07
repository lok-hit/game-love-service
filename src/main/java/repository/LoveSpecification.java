package repository;

import entity.Love;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class LoveSpecification {

    public static Specification<Love> hasPlayerId(Long playerId) {
        return (root, query, cb) -> cb.equal(root.get("player").get("id"), playerId);
    }

    public static Specification<Love> hasPlayerUsername(String username) {
        return (root, query, cb) -> cb.equal(root.get("player").get("username"), username);
    }

    public static Specification<Love> hasGameId(Long gameId) {
        return (root, query, cb) -> cb.equal(root.get("game").get("id"), gameId);
    }

    public static Specification<Love> lovedAfter(LocalDateTime dateTime) {
        return (root, query, cb) -> cb.greaterThan(root.get("lovedAt"), dateTime);
    }

    public static Specification<Love> lovedBefore(LocalDateTime dateTime) {
        return (root, query, cb) -> cb.lessThan(root.get("lovedAt"), dateTime);
    }

    public static Specification<Love> hasGame(String gameName) {
        return (root, query, cb) -> cb.equal(root.get("game").get("gameName"), gameName);
    }

}


