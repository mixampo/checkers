package models;

import java.util.ArrayList;
import java.util.List;

public class CheckersPlayer {
    private String name;
    private int playerNr;
    private Boolean onTurn;
    private Boolean ready;
    private List<Piece> pieces;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayerNr() {
        return playerNr;
    }

    public void setPlayerNr(int playerNr) {
        this.playerNr = playerNr;
    }

    public Boolean getOnTurn() {
        return onTurn;
    }

    public void setOnTurn(Boolean onTurn) {
        this.onTurn = onTurn;
    }

    public Boolean getReady() {
        return ready;
    }

    public void setReady(Boolean ready) {
        this.ready = ready;
    }

    public CheckersPlayer(String name, int playerNr, Boolean onTurn, Boolean ready, List<Piece> pieces) {
        this.name = name;
        this.playerNr = playerNr;
        this.onTurn = onTurn;
        this.ready = ready;
        this.pieces = pieces;
    }

    public CheckersPlayer(String name, int playerNr, Boolean onTurn, Boolean ready) {
        this.name = name;
        this.playerNr = playerNr;
        this.onTurn = onTurn;
        this.ready = ready;
    }

    public CheckersPlayer(String name, int playerNr) {
        this.name = name;
        this.playerNr = playerNr;
        ready = false;
        pieces = new ArrayList<>();
        addAllPiecesToList();
    }

    public void readyUp() {
        ready = true;
    }

    public boolean allHit() {
        for (Piece p : pieces) {
            if (!p.isHit()) {
                return false;
            }
        }
        return true;
    }

    public void addAllPiecesToList() {
        for (int i = 0; i < 21; i++) {
            pieces.add(new Piece(PieceType.NORMAL, this));
        }
    }
}
