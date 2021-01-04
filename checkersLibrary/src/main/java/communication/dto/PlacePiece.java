package communication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

public class PlacePiece {
    private int posX;
    private int posY;
    private boolean hasPiece;


    public PlacePiece(@JsonProperty("pos_x") int posX, @JsonProperty("pos_y") int posY, @JsonProperty("hasPiece") boolean hasPiece) {
        this.posX = posX;
        this.posY = posY;
        this.hasPiece = hasPiece;
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

    public boolean getHasPiece() {
        return hasPiece;
    }

    @JsonSetter("hasPiece")
    public void setHasPiece(boolean hasPiece) {
        this.hasPiece = hasPiece;
    }
}
