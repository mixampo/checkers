package rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import models.User;
import rest.security.JwtTokenProvider;
import rest.security.models.JwtResponse;
import rest.services.UserContainerService;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserContainerService userContainerService;

    @PostMapping(value = "/login", headers = "Accept=application/json")
    public ResponseEntity<?> authorize(@RequestBody User user) {
        if (userContainerService.checkUserPwd(user)) {
            try {
                //Authenticate user that wants to log in to application's frontend
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), userContainerService.getUserByUsername(user.getUsername()).getPassword()));

                //Create token if user can be authorized (if user exists)
                JwtResponse response = new JwtResponse(jwtTokenProvider.createToken(user.getUsername()), userContainerService.getUserByUsername(user.getUsername()));

                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (AuthenticationException e) {
                return new ResponseEntity<>("Could not authenticate", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("Invalid username/password supplied", HttpStatus.BAD_REQUEST);
    }
}
