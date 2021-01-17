package communication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class MovePiece {
    private int posX;
    private int posY;
    private int oldX;
    private int oldY;

    public MovePiece(@JsonProperty("pos_x") int posX, @JsonProperty("pos_y") int posY, @JsonProperty("old_x") int oldX, @JsonProperty("old_y") int oldY) {
        this.posX = posX;
        this.posY = posY;
        this.oldX = oldX;
        this.oldY = oldY;
    }

    public MovePiece() {

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

    public int getOldX() {
        return oldX;
    }

    @JsonSetter("old_x")
    public void setOldX(int oldX) {
        this.oldX = oldX;
    }

    public int getOldY() {
        return oldY;
    }

    @JsonSetter("old_y")
    public void setOldY(int oldY) {
        this.oldY = oldY;
    }
}
