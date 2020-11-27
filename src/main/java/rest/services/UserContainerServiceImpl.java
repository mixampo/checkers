package rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import models.User;
import rest.repositories.UserJpaRepository;
import rest.shared.PasswordHasher;
import rest.shared.interfaces.IPasswordHasher;

import java.util.List;
import java.util.Optional;

@Service
public class UserContainerServiceImpl implements UserContainerService {

    private IPasswordHasher passwordHasher = new PasswordHasher();

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Override
    public Boolean addUser(User user) {
        if (userJpaRepository.getUserByUsername(user.getUsername()) == null) {
            user.setPassword(passwordHasher.getPasswordHash(user.getPassword()));
            userJpaRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<User> getUser(int id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public List<User> getUsers() {
        return userJpaRepository.findAll();
    }

    @Override
    public User getUserByUsername(String username) {
        return userJpaRepository.getUserByUsername(username);
    }

    @Override
    public Boolean checkUserPwd(User user) {
        User fetchedUser = userJpaRepository.getUserByUsername(user.getUsername());

        return fetchedUser != null && passwordHasher.getPasswordCheckStatus(user.getPassword(), fetchedUser.getPassword());
    }
}
