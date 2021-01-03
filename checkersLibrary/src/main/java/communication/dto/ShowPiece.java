package communication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class ShowPiece {
    private int posX;
    private int posY;


    public ShowPiece(@JsonProperty("pos_x") int posX, @JsonProperty("pos_y") int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    @JsonSetter("pos_x")
    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    @JsonSetter("pos_y")
    public void setPosY(int posY) {
        this.posY = posY;
    }
}
