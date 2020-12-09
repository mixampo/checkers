package rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import models.User;

public interface UserJpaRepository extends JpaRepository<User, Integer> {
    User getUserByUsername(String username);
}
