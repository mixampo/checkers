package rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.repositories.ScoreboardJpaRepository;

@Service
public class ScoreboardServiceImpl implements ScoreboardService {

    @Autowired
    private ScoreboardJpaRepository scoreboardJpaRepository;
}
