package service;

import org.springframework.web.client.RestTemplate;

public class ApiCallService implements IApiCallService {

    private RestTemplate restTemplate = new RestTemplate();
    final String uri = "http://localhost:8080";

    @Override
    public void updateScoreBoardItem(int playerNumber, boolean win) {

    }
}
