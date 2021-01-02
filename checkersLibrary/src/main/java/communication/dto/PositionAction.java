package communication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import models.Piece;

public class PositionAction extends PlayerAction {
    private int posX;
    private int posY;
    private Piece piece;

    public PositionAction(@JsonProperty("player_nr") int playerNr, @JsonProperty("piece")Piece piece, @JsonProperty("pos_x") int posX, @JsonProperty("pos_y") int posY) {
        super(playerNr);
        this.piece = piece;
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

    public Piece getPiece() {
        return piece;
    }
}
