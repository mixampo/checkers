package communication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class GameMessage {
    @JsonProperty("player_nr")
    private int playerNr;

    @JsonProperty("message_type")
    private String messageType;

    @JsonProperty("message_data")
    private String messageData;

    public GameMessage() {}

    public GameMessage(String messageType, String messageData) {
        this.messageType = messageType;
        this.messageData = messageData;
    }

    public GameMessage(int playerNr, String messageType, String messageData) {
        this.messageType = messageType;
        this.messageData = messageData;
        this.playerNr = playerNr;
    }

    public String getMessageType() {
        return messageType;
    }

    @JsonSetter("message_type")
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageData() {
        return messageData;
    }

    @JsonSetter("message_data")
    public void setMessageData(String messageData) {
        this.messageData = messageData;
    }

    public int getPlayerNr() {
        return playerNr;
    }

    @JsonSetter("player_nr")
    public void setPlayerNr(int playerNr) {
        this.playerNr = playerNr;
    }
}
