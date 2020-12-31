package communication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PositionAction extends PlayerAction {
    private int posX;

    private int posY;

    public PositionAction(@JsonProperty("player_nr") int playerNr, @JsonProperty("pos_x") int posX, @JsonProperty("pos_y") int posY) {
        super(playerNr);
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
