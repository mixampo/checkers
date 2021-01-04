package models;

public class Piece {
    private PieceType type;
    private Box place;
    private char color;
    private CheckersPlayer player;
    private double oldX, oldY;

    public PieceType getType() {
        return type;
    }

    public void setType(PieceType type) {
        this.type = type;
    }

    public Box getPlace() {
        return place;
    }

    public void setPlace(Box place) {
        this.place = place;
    }

    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public CheckersPlayer getPlayer() {
        return player;
    }

    public void setPlayer(CheckersPlayer player) {
        this.player = player;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public Piece(PieceType type, Box place, char color, CheckersPlayer player) {
        this.type = type;
        this.place = place;
        this.color = color;
        this.player = player;
    }

    public Piece(PieceType type, char color, CheckersPlayer player) {
        this.type = type;
        this.color = color;
        this.player = player;
    }

    public Piece(PieceType type, CheckersPlayer player) {
        this.type = type;
        this.player = player;
    }

    public Piece(double oldX, double oldY) {
        this.oldX = oldX;
        this.oldY = oldY;
    }

    public Piece(PieceType type, double oldX, double oldY) {
        this.type = type;
        this.oldX = oldX;
        this.oldY = oldY;
    }

    public Piece(CheckersPlayer player) {
        this.player = player;
    }

    public Piece() {

    }

    public boolean isHit() {
        return place == null;
    }

    public boolean isPlaced() {
        return place != null;
    }

    public boolean removeBox(Box selectedBox) {
        if (place == selectedBox) {
            place.removePiece();
            place = null;
            return true;
        } else {
            throw new IllegalArgumentException("Point out of bounds");
        }
    }
}
