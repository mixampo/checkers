package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import models.ScoreboardItem;
import models.User;

import java.util.List;

public interface IApiCallService {
    Boolean register(String username, String Password);
    User login(String username, String password);
    List<ScoreboardItem> getScoreboard(User user) throws JsonProcessingException;
}
