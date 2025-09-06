package repository;

import entity.Player;
import org.springframework.data.jpa.domain.Specification;

public class PlayerSpecification {

    public static Specification<Player> hasName(String name) {
        return (root, query, cb) -> cb.equal(cb.lower(root.get("name")), name.toLowerCase());
    }

    public static Specification<Player> nameContains(String keyword) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%");
    }

    public static Specification<Player> hasId(Long id) {
        return (root, query, cb) -> cb.equal(root.get("id"), id);
    }
}
