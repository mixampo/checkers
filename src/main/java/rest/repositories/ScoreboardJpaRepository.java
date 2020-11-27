package rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import models.ScoreboardItem;

public interface ScoreboardJpaRepository extends JpaRepository<ScoreboardItem, Integer> {
}
