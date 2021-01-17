package models;

import java.util.ArrayList;
import java.util.List;

public class CheckersPlayer {
    private String name;
    private int playerNumber;
    private Boolean onTurn;
    private Boolean ready;
    private List<Piece> pieces;
    private Board gameBoard;
    private char color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
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

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public Board getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(Board gameBoard) {
        this.gameBoard = gameBoard;
    }

    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }

    public CheckersPlayer(String name, int playerNumber, Boolean onTurn, Boolean ready, List<Piece> pieces, Board gameBoard) {
        this.name = name;
        this.playerNumber = playerNumber;
        this.onTurn = onTurn;
        this.ready = ready;
        this.pieces = pieces;
        this.gameBoard = gameBoard;
    }

    public CheckersPlayer(String name, int playerNumber, Boolean onTurn, Boolean ready, List<Piece> pieces) {
        this.name = name;
        this.playerNumber = playerNumber;
        this.onTurn = onTurn;
        this.ready = ready;
        this.pieces = pieces;
    }

    public CheckersPlayer(String name, int playerNumber, Boolean onTurn, Boolean ready) {
        this.name = name;
        this.playerNumber = playerNumber;
        this.onTurn = onTurn;
        this.ready = ready;
    }

    public CheckersPlayer(String name, int playerNumber) {
        this.name = name;
        this.playerNumber = playerNumber;
        ready = false;
        pieces = new ArrayList<>();
        addAllPiecesToList();
        color = (playerNumber == 0) ? 'W' : 'R';
        gameBoard = new Board(10, 10, playerNumber);
    }

    public void readyUp() {
        ready = true;
    }

    public boolean allHit() {
        int count = 0;
        for (Piece p : pieces) {
            if (p.isHit()) {
                count++;
            }
        }
        return count == 20;
    }

    public void addAllPiecesToList() {
        for (int i = 0; i < 20; i++) {
            pieces.add(new Piece((playerNumber == 0) ? PieceType.WHITE : PieceType.RED, this));
        }
    }

    public void placePieces() {
        gameBoard.placePiece(pieces);
    }

    public Piece getPieceAtBox(int posX, int posY) {
        Piece piece = gameBoard.getBox(posX, posY).getPiece();
        return piece;
    }

    public void movePiece(int oldX, int oldY, int newX, int newY) {
        Box newBox = gameBoard.getBox(newX, newY);
        Box oldBox = gameBoard.getBox(oldX, oldY);
        Piece piece = gameBoard.getBox(oldX, oldY).getPiece();

        newBox.setPiece(piece);
        oldBox.setPiece(null);
        piece.setPlace(newBox);
    }

    public void hitPiece(int posX, int posY) {
        Piece piece = gameBoard.getBox(posX, posY).getPiece();
        piece.setPlace(null);
        piece.setHit(true);
        gameBoard.getBox(posX, posY).setPiece(null);
    }
}
