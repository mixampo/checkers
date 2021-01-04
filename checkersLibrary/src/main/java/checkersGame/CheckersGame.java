package checkersGame;

import models.*;


public abstract class CheckersGame implements ICheckersGame {

    protected final static int player_count = 2;
    protected int player_turn = -1;
    protected CheckersPlayer[] checkersPlayers = new CheckersPlayer[player_count];
    public ICheckersGUI application;

    @Override
    public void notifyReady(int playerNumber) {
        CheckersPlayer checkersPlayer = checkersPlayers[playerNumber];
        checkersPlayer.readyUp();
        checkersPlayer.placePieces();
        updatePlayerBoard(playerNumber, MoveType.NONE, 0, 0);
        if (checkersPlayers[1 - playerNumber] != null && checkersPlayers[1 - playerNumber].getReady()) {
            updateOpponentBoard(1 - playerNumber, MoveType.NONE, 0, 0);
            player_turn = 0;
            application.setPlayerTurn(player_turn);
            application.notifyStartGame(player_turn);
        }
    }

    @Override
    public void moveDam() {

    }

    @Override
    public void obtainDam() {

    }

    @Override
    public void hitPiece() {

    }

    @Override
    public void hitMultiplePieces() {

    }

    @Override
    public void startNewGame(int playerNumber) {

    }

    protected void updatePlayerBoard(int playerNumber, MoveType type, double oldX, double oldY) {
        Board playerBoard = checkersPlayers[playerNumber].getGameBoard();
        for (Box b : playerBoard.getBoxes()) {
            switch (type) {
                case NONE:
                    application.placePiecePlayer(playerNumber, b.getxCord(), b.getyCord(), b.hasPiece());
                    break;
                case NORMAL:
                case HIT:
                    application.movePiecePlayer(playerNumber, b.getxCord(), b.getyCord(), oldX, oldY);
                    break;
            }
        }
    }

    protected void updateOpponentBoard(int playerNumber, MoveType type, double oldX, double oldY) {
        Board opponentBoard = checkersPlayers[playerNumber].getGameBoard();
        for (Box b : opponentBoard.getBoxes()) {
            switch (type) {
                case NONE:
                    application.placePieceOpponent(playerNumber, b.getxCord(), b.getyCord(), b.hasPiece());
                    break;
                case NORMAL:
                case HIT:
                    application.movePieceOpponent(playerNumber, b.getxCord(), b.getyCord(), oldX, oldY);
                    break;
            }
        }
    }
}
