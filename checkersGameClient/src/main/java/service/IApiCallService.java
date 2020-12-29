package service;

import models.User;

public interface IApiCallService {
    Boolean register(String username, String Password);
    User login(String username, String password);
}
