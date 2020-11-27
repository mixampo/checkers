package rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import models.Scoreboard;

public interface ScoreboardJpaRepository extends JpaRepository<Scoreboard, Integer> {
}
