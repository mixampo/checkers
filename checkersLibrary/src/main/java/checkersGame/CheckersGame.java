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
        updatePlayerBoard(playerNumber, MoveType.PLACE, 0, 0, 0, 0);
        if (checkersPlayers[1 - playerNumber] != null && checkersPlayers[1 - playerNumber].getReady()) {
            updateOpponentBoard(playerNumber, MoveType.PLACE, 0, 0, 0, 0);
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


    protected void updatePlayerBoard(int playerNumber, MoveType type, int newX, int newY, int oldX, int oldY) {
        Board playerBoard = checkersPlayers[playerNumber].getGameBoard();
        for (Box b : playerBoard.getBoxes()) {
            switch (type) {
                case NONE:
                case NORMAL:
                case HIT:
                    if (b.getxCord() == newX && b.getyCord() == newY && b.hasPiece()) {
                        application.movePiece(playerNumber, newX, newY, oldX, oldY);
                    }
                    break;
                case PLACE:
                    application.placePiecePlayer(playerNumber, b.getxCord(), b.getyCord(), b.hasPiece());
                    break;
            }
        }
    }

    protected void updateOpponentBoard(int playerNumber, MoveType type, int newX, int newY, int oldX, int oldY) {
        Board opponentBoard = checkersPlayers[1 - playerNumber].getGameBoard();
        for (Box b : opponentBoard.getBoxes()) {
            newX = 9 - newX;
            newY = 9 - newY;
            oldX = 9 - oldX;
            oldY = 9 - oldY;

            switch (type) {
                case NONE:
                case NORMAL:
                case HIT:
                    if (9 - (b.getxCord()) == newX && (9 - b.getyCord()) == newY && b.hasPiece()) {
                        application.movePieceOpponent(playerNumber, newX, newY, oldX, oldY);
                    }
                    break;
                case PLACE:
                    application.placePieceOpponent(playerNumber, (9 - b.getxCord()), (9 - b.getyCord()), b.hasPiece());
                    break;
            }
        }
    }
}
