package rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import models.User;
import rest.repositories.UserJpaRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Override
    public void updateUser(User user) {
        userJpaRepository.save(user);
    }
}
