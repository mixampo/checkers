package rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.repositories.ScoreboardJpaRepository;

@Service
public class ScoreboardContainerServiceImpl implements ScoreboardContainerService {

    @Autowired
    private ScoreboardJpaRepository scoreboardJpaRepository;
}
