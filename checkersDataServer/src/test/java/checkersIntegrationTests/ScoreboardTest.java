package checkersIntegrationTests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import models.ScoreboardItem;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import rest.CheckersRestServer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(
        classes = CheckersRestServer.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreboardTest {

    private JSONParser parser;
    private Gson g;
    private ObjectMapper mapper;

    private String url;
    private User user;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    public void init() throws ParseException {
        this.url = format("http://localhost:%d/login", port);

        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        parser = new JSONParser();
        g = new Gson();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<Object>(new User("Test", "TesT"), headers);

        //Login
        ResponseEntity<String> out = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        //Get Jwt from response body
        Object obj = parser.parse(out.getBody());
        JSONObject jso = (JSONObject) obj;

        //Create user
        user = g.fromJson(jso.get("user").toString(), User.class);
        user.setJwt(g.fromJson(jso.get("token").toString(), String.class));

        //Change URL for scoreboard tests
        this.url = format("http://localhost:%d/scoreboard", port);
    }

    @Test
    public void testGetScoreboard() {
        ResponseEntity<String> out = restTemplate.exchange(url, HttpMethod.GET, user.getAuthHeader(), String.class);

        Assertions.assertEquals(out.getStatusCode(), HttpStatus.OK);
        Assertions.assertTrue(out.getBody().length() > 0);
    }

    @Test
    public void testAddScoreboardItem() {
//        models.User user = new models.User("Test4", "TesT");
//        LocalDate date = LocalDate.now();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", "Bearer " + this.user.getJwt());
//        HttpEntity<?> entity = new HttpEntity<Object>(new ScoreboardItem(user, 1, date, true), headers);
//
//        restTemplate.postForObject(url, entity, ScoreboardItem.class);
//
//        Assertions.assertEquals(HttpStatus.CREATED, out.getStatusCode());
    }


    @Test
    public void testGetScoreboardItem() throws JsonProcessingException {
        int scoreboardItemId = 1;
        url = format("http://localhost:%d/scoreboard/%d", port, scoreboardItemId);

        ResponseEntity<String> out = restTemplate.exchange(url, HttpMethod.GET, user.getAuthHeader(), String.class);

        ScoreboardItem item = mapper.readValue(out.getBody(), new TypeReference<>() {
        });

        Assertions.assertEquals(HttpStatus.OK, out.getStatusCode());
        Assertions.assertTrue(out.getBody().length() > 0);
        Assertions.assertEquals(scoreboardItemId, item.getId());
    }

    @Test
    public void testUpdateScoreboardItem() throws JsonProcessingException {
        int scoreboardItemId = 1;
        url = format("http://localhost:%d/scoreboard/%d", port, scoreboardItemId);

        ResponseEntity<String> out = restTemplate.exchange(url, HttpMethod.GET, user.getAuthHeader(), String.class);
        ScoreboardItem item = mapper.readValue(out.getBody(), new TypeReference<>() {
        });

        item.setScore(3);

        url = format("http://localhost:%d/scoreboard", port);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + user.getJwt());
        HttpEntity<?> entity = new HttpEntity<Object>(item, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteScoreboardItem() {
        int scoreboardItemId = 1;
        url = format("http://localhost:%d/scoreboard/%d", port, scoreboardItemId);

        ResponseEntity<String> out = restTemplate.exchange(url, HttpMethod.DELETE, user.getAuthHeader(), String.class);

        Assertions.assertEquals(HttpStatus.OK, out.getStatusCode());
    }

    @Test
    public void testGetScoreboardItemsByUserId() throws JsonProcessingException {
        url = format("http://localhost:%d/scoreboard/user/%d", port, user.getId());

        ResponseEntity<String> out = restTemplate.exchange(url, HttpMethod.GET, user.getAuthHeader(), String.class);

        Assertions.assertSame(out.getStatusCode(), HttpStatus.OK);
        Assertions.assertTrue(out.getBody().length() > 0);

        List<ScoreboardItem> items = mapper.readValue(out.getBody(), new TypeReference<ArrayList<ScoreboardItem>>() {
        });

        for (ScoreboardItem item : items) {
            Assertions.assertEquals(user.getId(), item.getUser().getId());
        }
    }

    private static class User {
        @JsonProperty("id")
        private int id;

        @JsonProperty("username")
        private String username;

        @JsonProperty("password")
        private String password;

        @JsonProperty("token")
        private String jwt;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getJwt() {
            return jwt;
        }

        public void setJwt(String jwt) {
            this.jwt = jwt;
        }

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public HttpEntity<?> getAuthHeader() {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + getJwt());
            HttpEntity<?> entity = new HttpEntity<>(headers);
            return entity;
        }
    }
}
