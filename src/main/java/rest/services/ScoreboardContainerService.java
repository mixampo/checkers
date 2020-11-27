package rest.services;

import models.ScoreboardItem;

import java.util.List;
import java.util.Optional;


public interface ScoreboardContainerService {
    List<ScoreboardItem> getScoreboard();
    Optional<ScoreboardItem> getScoreboardItem(int id);
    void addScoreboardItem(ScoreboardItem scoreboardItem);
    void deleteScoreboardItem(int id);
}
