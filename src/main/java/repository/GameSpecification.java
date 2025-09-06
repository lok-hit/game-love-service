package repository;

import entity.Game;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class GameSpecification {

    public static Specification<Game> hasName(String name) {
        return (root, query, cb) -> cb.equal(cb.lower(root.get("name")), name.toLowerCase());
    }

    public static Specification<Game> nameContains(String keyword) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%");
    }
}


