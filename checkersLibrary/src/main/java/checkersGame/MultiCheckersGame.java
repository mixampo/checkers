package checkersGame;

import checkersGame.exceptions.InvalidBoxException;
import checkersGame.exceptions.NotPlayersTurnException;
import checkersGame.exceptions.PointOutOfBoundsException;
import models.*;

public class MultiCheckersGame extends CheckersGame {


    @Override
    public void registerPlayer(User user, ICheckersGUI application) {
        if (this.checkersPlayers[1] != null) {
            this.application.showErrorMessage(-1, "Server is full");
        }
        this.application = application;
        CheckersPlayer opponent = this.checkersPlayers[0];

        if (opponent == null) {
            this.checkersPlayers[0] = new CheckersPlayer(user.getUsername(), 0);
            this.application.setPlayerNumber(0, user.getUsername());
        } else {
            this.checkersPlayers[1] = new CheckersPlayer(user.getUsername(), 1);
            this.application.setOpponentName(1, checkersPlayers[0].getName());
            this.application.setOpponentName(0, checkersPlayers[1].getName());
            this.application.setPlayerNumber(1, user.getUsername());
        }

    }

    @Override
    public void movePiece(int playerNumber, Piece piece, int newX, int newY) throws InvalidBoxException, NotPlayersTurnException, PointOutOfBoundsException {

        Board gameBoard = checkersPlayers[playerNumber].getGameBoard();
        Box b = gameBoard.getBox(newX, newY);
        int oldX = gameBoard.toBoard(piece.getOldX());
        int oldY = gameBoard.toBoard(piece.getOldY());

        if (player_turn != playerNumber) {
            application.showErrorMessage(playerNumber, "It's not your turn!");

            newX = oldX;
            newY = oldY;
            updatePlayerBoard(playerNumber, MoveType.NONE, newX, newY, oldX, oldY);
            throw new NotPlayersTurnException();
        }

        if (b.getPiece() != null || (newX + newY) % 2 == 0 || (Math.abs(newX - oldX) != 1 || newY - oldY != piece.getType().getMoveDir())) {
            application.showErrorMessage(playerNumber, "Invalid position!");

            newX = oldX;
            newY = oldY;
            updatePlayerBoard(playerNumber, MoveType.NONE, newX, newY, oldX, oldY);
            throw new InvalidBoxException();
        }

        if (Math.abs(newX - oldX) == 1 && newY - oldY == piece.getType().getMoveDir()) {
            gameBoard.getBox(oldX, oldY).setPiece(null);
            b.setPiece(piece);
            piece.setPlace(b);

            updatePlayerBoard(playerNumber, MoveType.NORMAL, newX, newY, oldX, oldY);
            updateOpponentBoard(playerNumber, MoveType.NORMAL, newX, newY, oldX, oldY);

//        } else if (Math.abs(newX - oldX) == 2 && newY - oldY == piece.getType().getMoveDir() * 2) {
//
//            int x1 = oldX + (newX - oldX) / 2;
//            int y1 = oldY + (newY - oldY) / 2;
//
//            if (gameBoard.getBox(x1, y1).getPiece() != null && gameBoard.getBox(x1, y1).getPiece().getType() != piece.getType()) {
//                updatePlayerBoard(playerNumber, MoveType.HIT);
//                updateOpponentBoard(1 - playerNumber, MoveType.HIT);
//
//            }
        }

        if (checkersPlayers[1 - playerNumber].allHit()) {
            application.showErrorMessage(playerNumber, "Winner");
            application.showWinner(playerNumber);
            application.showErrorMessage(playerNumber, checkersPlayers[playerNumber].getName() + " has won the game");
        }

        player_turn = 1 - playerNumber;
        application.setPlayerTurn(player_turn);
    }
}
