package communication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class PlayerAction {

    private int playerNr;

    public int getPlayerNr() {
        return playerNr;
    }

    @JsonSetter("player_nr")
    public void setPlayerNr(int playerNr) {
        this.playerNr = playerNr;
    }

    public PlayerAction(@JsonProperty("player_nr") int playerNr) {
        this.playerNr = playerNr;
    }

    public PlayerAction() {

    }
}
