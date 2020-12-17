package shared;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class PostMessage {

    @JsonProperty("testMsg")
    private String bearerToken;

    @JsonSetter("testMsg")
    public void setBearerToken(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    public String getBearerToken() {
        return bearerToken;
    }

    public PostMessage(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    public PostMessage() {
    }
}
