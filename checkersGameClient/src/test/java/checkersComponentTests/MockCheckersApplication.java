package checkersComponentTests;

import checkersGame.ICheckersGUI;
import models.Board;
import models.Piece;

public class MockCheckersApplication implements ICheckersGUI {

    private final int WIDTH = 10;
    private final int LENGTH = 10;

    private int playerNumber = -1;
    private int playerTurn = -1;
    private int opponentNumber = -1;
    private int winner = -1;
    private String playerName = null;
    private String opponentName = null;
    private String errorMessage = null;
    private String infoMessage = null;

    private boolean wrongPlayerNumberReceived = false;
    private boolean gameStarted = false;

    private Piece piece;
    private Board playerBoard;
    private Board opponentBoard;

    public int getWIDTH() {
        return WIDTH;
    }

    public int getLENGTH() {
        return LENGTH;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getOpponentName() {
        return opponentName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getInfoMessage() {
        return infoMessage;
    }

    public boolean isWrongPlayerNumberReceived() {
        return wrongPlayerNumberReceived;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public Board getPlayerBoard() {
        return playerBoard;
    }

    public Board getOpponentBoard() {
        return opponentBoard;
    }

    public void setOpponentBoard(Board opponentBoard) {
        this.opponentBoard = opponentBoard;
    }

    public Piece getPiece() {
        return piece;
    }

    @Override
    public void notifyStartGame(int playerNumber) {
        this.gameStarted = true;
    }

    @Override
    public void setPlayerNumber(int playerNumber, String username) {
        this.playerNumber = playerNumber;
        this.playerName = username;

        this.wrongPlayerNumberReceived = false;
        this.gameStarted = false;
    }

    @Override
    public void setPlayerTurn(int playerNumber) {
        this.playerTurn = playerNumber;
    }

    @Override
    public void showErrorMessage(int playerNumber, String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void showInfoMessage(int playerNumber, String infoMessage) {
        this.infoMessage = infoMessage;
    }

    @Override
    public void setOpponentName(int opponentNumber, String username) {
        this.opponentNumber = opponentNumber;
        this.opponentName = username;

        playerBoard = new Board(LENGTH, WIDTH, playerNumber);
        opponentBoard = new Board(LENGTH, WIDTH, opponentNumber);
    }

    @Override
    public void showWinner(int playerNumber) {
        this.winner = playerNumber;
    }

    @Override
    public void placePiecePlayer(int playerNumber, int posX, int posY, boolean hasPiece) {
        if (hasPiece) {
            Piece piece = new Piece();
            playerBoard.occupyBox(posX, posY, piece);
        }
    }

    @Override
    public void placePieceOpponent(int playerNumber, int posX, int posY, boolean hasPiece) {
        if (hasPiece) {
            Piece piece = new Piece();
            opponentBoard.occupyBox(posX, posY, piece);
        }
    }

    @Override
    public void movePiecePlayer(int playerNumber, int posX, int posY, int oldX, int oldY) {
        if (posX != oldX || posX != oldY) {
            piece = playerBoard.getBox(oldX, oldY).getPiece();
            if (piece != null) {
                playerBoard.occupyBox(posX, posY, piece);
                playerBoard.getBox(oldX, oldY).setPiece(null);

                piece.setOldX(oldX);
                piece.setOldY(oldY);
            }
        }
    }

    @Override
    public void movePieceOpponent(int playerNumber, int posX, int posY, int oldX, int oldY) {
        if (posX != oldX || posX != oldY) {
            piece = opponentBoard.getBox(oldX, oldY).getPiece();
            if (piece != null) {
                opponentBoard.occupyBox(posX, posY, piece);
                opponentBoard.getBox(oldX, oldY).setPiece(null);

                piece.setOldX(oldX);
                piece.setOldY(oldY);
            }
        }
    }

    @Override
    public void hitPiecePlayer(int playerNumber, int posX, int posY) {
        playerBoard.getBox(posX, posY).setPiece(null);
    }

    @Override
    public void hitPieceOpponent(int playerNumber, int posX, int posY) {
        opponentBoard.getBox(posX, posY).setPiece(null);
    }
}
