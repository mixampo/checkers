package rest.services;

import models.User;

import java.util.List;
import java.util.Optional;

public interface UserContainerService {
    Boolean addUser(User user);
    Optional<User> getUser(int id);
    List<User> getUsers();
    User getUserByUsername(String username);
    Boolean checkUserPwd(User user);
}
