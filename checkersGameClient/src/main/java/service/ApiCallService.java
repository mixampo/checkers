package service;

import com.google.gson.Gson;
import models.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class ApiCallService implements IApiCallService {

    private RestTemplate restTemplate = new RestTemplate();
    JSONParser parser = new JSONParser();
    Gson g = new Gson();
    final String uri = "http://localhost:8080";

    @Override
    public Boolean register(String username, String password) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<Object>(new User(username, password), headers);

        try {
            ResponseEntity<String> out = restTemplate.exchange((uri + "/user"), HttpMethod.POST, entity, String.class);
            return true;

        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User login(String username, String password) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<Object>(new User(username, password), headers);

        try {
            ResponseEntity<String> out = restTemplate.exchange((uri + "/login"), HttpMethod.POST, entity, String.class);

            Object obj = parser.parse(out.getBody());
            JSONObject jso = (JSONObject) obj;

            User user = g.fromJson(jso.get("user").toString(), User.class);
            user.setJwt(g.fromJson(jso.get("token").toString(), String.class));

            return user;

        } catch (HttpClientErrorException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
