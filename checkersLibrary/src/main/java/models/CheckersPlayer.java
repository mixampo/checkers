package models;

public class CheckersPlayer {
    private String name;
    private int playerNr;
    private Boolean onTurn;
    private Boolean ready;

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

    public CheckersPlayer(String name, int playerNr, Boolean onTurn, Boolean ready) {
        this.name = name;
        this.playerNr = playerNr;
        this.onTurn = onTurn;
        this.ready = ready;
    }

    public CheckersPlayer(String name, int playerNr) {
        this.name = name;
        this.playerNr = playerNr;
    }

    public void readyUp() {
        ready = true;
    }
}
