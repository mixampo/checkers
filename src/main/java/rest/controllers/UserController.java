package rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import models.User;
import rest.services.UserContainerService;
import rest.services.UserService;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserContainerService userContainerService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/user",
            headers = "Accept=application/json")
    public ResponseEntity<?> addUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        if (userContainerService.addUser(user)) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/account/{id}").buildAndExpand(user.getId()).toUri());
            return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value = "/user",
            headers = "Accept=application/json")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/user")
    public List<User> getUser() {
        return userContainerService.getUsers();
    }

    @GetMapping(value = "/user/{id}")
    public Optional<User> getUser(@PathVariable("id") int id) {
        return userContainerService.getUser(id);
    }
}
