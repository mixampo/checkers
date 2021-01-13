package integration;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import rest.CheckersRestServer;

import static java.lang.String.format;

@TestPropertySource(locations="classpath:application-test.properties")
@SpringBootTest(
        classes = CheckersRestServer.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)

public class scoreboardTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String url;

    @BeforeEach
    public void init() {
        this.url = format("http://localhost:%d/scoreboard", port);
    }
}
