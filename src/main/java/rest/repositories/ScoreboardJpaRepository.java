package rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import models.ScoreboardItem;

import java.util.List;

public interface ScoreboardJpaRepository extends JpaRepository<ScoreboardItem, Integer> {
    List<ScoreboardItem> findAllByUserId(int id);
}
