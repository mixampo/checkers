package models;

public class Box {
    private int xCord;
    private  int yCord;
    private Piece piece;
    private Board board;

    public int getxCord() {
        return xCord;
    }

    public void setxCord(int xCord) {
        this.xCord = xCord;
    }

    public int getyCord() {
        return yCord;
    }

    public void setyCord(int yCord) {
        this.yCord = yCord;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }


    public Box(int xCord, int yCord, Piece piece, Board board) {
        this.xCord = xCord;
        this.yCord = yCord;
        this.piece = piece;
        this.board = board;
    }

    public Box(int xCord, int yCord) {
        this.xCord = xCord;
        this.yCord = yCord;
    }

    public void removePiece() {
        piece = null;
    }
}
