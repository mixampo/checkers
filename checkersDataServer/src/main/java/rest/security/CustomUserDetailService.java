package rest.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import models.User;
import rest.repositories.UserJpaRepository;

@Component
public class CustomUserDetailService implements UserDetailsService {
    private UserJpaRepository userJpaRepository;

    public CustomUserDetailService(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userJpaRepository.getUserByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("User with username : " + username + " could not be found");
        }
        return new User(user.getId(), user.getUsername(), user.getPassword());
    }
}
