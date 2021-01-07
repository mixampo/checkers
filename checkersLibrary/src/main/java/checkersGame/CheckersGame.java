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
            combineBoards(playerNumber);
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

    protected void combineBoards(int playerNumber) {
        Board playerBoard = checkersPlayers[playerNumber].getGameBoard();
        Board opponentBoard = checkersPlayers[1 - playerNumber].getGameBoard();

        for (Box a : playerBoard.getBoxes()) {
            if (a.getPiece() != null) {
                Box bo = opponentBoard.getBox((9 - a.getxCord()), (9 - a.getyCord()));
                bo.setPiece(a.getPiece());
            }
        }

        for (Box b : opponentBoard.getBoxes()) {
            if (b.getPiece() != null) {
                Box bp = playerBoard.getBox((9 - b.getxCord()), (9 - b.getyCord()));
                bp.setPiece(b.getPiece());
            }
        }
    }


    protected void updatePlayerBoard(int playerNumber, MoveType type, int newX, int newY, int oldX, int oldY) {
        Board playerBoard = checkersPlayers[playerNumber].getGameBoard();
        for (Box b : playerBoard.getBoxes()) {
            switch (type) {
                case NONE:
                case NORMAL:
                    if (b.getxCord() == newX && b.getyCord() == newY && b.getPiece() != null) {
                        application.movePiecePlayer(playerNumber, newX, newY, oldX, oldY);
                    }
                    break;
                case HIT:
                    if (b.getxCord() == newX && b.getyCord() == newY) {
                        application.hitPiecePlayer(playerNumber, newX, newY);
                    }
                    break;
                case PLACE:
                    application.placePiecePlayer(playerNumber, b.getxCord(), b.getyCord(), b.getPiece() != null);
                    break;
            }
        }
    }

    protected void updateOpponentBoard(int playerNumber, MoveType type, int newX, int newY, int oldX, int oldY) {
        Board opponentBoard = checkersPlayers[1 - playerNumber].getGameBoard();

        int x0 = 9 - newX;
        int y0 = 9 - newY;
        oldX = 9 - oldX;
        oldY = 9 - oldY;

        for (Box b : opponentBoard.getBoxes()) {
            switch (type) {
                case NONE:
                case NORMAL:
                    if (b.getxCord() == newX && b.getyCord() == newY) {
                        opponentBoard.getBox(oldX, oldY).setPiece(null);
                        opponentBoard.getBox(x0, y0).setPiece(checkersPlayers[playerNumber].getGameBoard().getBox(newX, newY).getPiece());

                        application.movePieceOpponent(playerNumber, x0, y0, oldX, oldY);
                    }
                    break;
                case HIT:
                    if (b.getxCord() == newX && b.getyCord() == newY) {
//                        checkersPlayers[1 - playerNumber].hitPiece(opponentBoard.getBox(newX, newY).getPiece());
                        opponentBoard.getBox(newX, newY).getPiece().setHit(true);
                        opponentBoard.getBox(newX, newY).setPiece(null);
                        application.hitPieceOpponent(playerNumber, newX, newY);
                    }
                    break;
                case PLACE:
                    application.placePieceOpponent(playerNumber, (9 - b.getxCord()), (9 - b.getyCord()), b.getPiece() != null);
                    break;
            }
        }
    }
}
