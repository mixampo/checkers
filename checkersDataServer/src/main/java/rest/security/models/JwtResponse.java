package rest.security.models;

import models.User;

public class JwtResponse {
    private String token;
    private User user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUserId(User user) {
        this.user = user;
    }

    public JwtResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }
}
