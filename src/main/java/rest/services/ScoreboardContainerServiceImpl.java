package rest.services;

import models.ScoreboardItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.repositories.ScoreboardJpaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ScoreboardContainerServiceImpl implements ScoreboardContainerService {

    @Autowired
    private ScoreboardJpaRepository scoreboardJpaRepository;

    @Override
    public List<ScoreboardItem> getScoreboard() {
        return scoreboardJpaRepository.findAll();
    }

    @Override
    public List<ScoreboardItem> getScoreboardItemsByUserId(int id) {
        return scoreboardJpaRepository.findAllByUserId(id);
    }

    @Override
    public Optional<ScoreboardItem> getScoreboardItem(int id) {
        return scoreboardJpaRepository.findById(id);
    }

    @Override
    public void addScoreboardItem(ScoreboardItem scoreboardItem) {
        scoreboardJpaRepository.save(scoreboardItem);
    }

    @Override
    public void deleteScoreboardItem(int id) {
        scoreboardJpaRepository.deleteById(id);
    }
}
