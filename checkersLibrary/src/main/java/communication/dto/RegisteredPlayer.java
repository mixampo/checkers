package communication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisteredPlayer {
    private String name;
    private int number;
    private String session_id;

    public RegisteredPlayer(@JsonProperty("name") String name, @JsonProperty("number") int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
