package checkersGame;

import checkersGame.exceptions.InvalidBoxException;
import checkersGame.exceptions.MustHitException;
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
    public void movePiece(int playerNumber, Piece piece, int newX, int newY) throws InvalidBoxException, NotPlayersTurnException, PointOutOfBoundsException, MustHitException {

        Board playerBoard = checkersPlayers[playerNumber].getGameBoard();
        Board opponentBoard = checkersPlayers[1 - playerNumber].getGameBoard();
        Box b = playerBoard.getBox(newX, newY);
        int oldX = playerBoard.toBoard(piece.getOldX());
        int oldY = playerBoard.toBoard(piece.getOldY());

        if (player_turn != playerNumber) {
            noMove(playerNumber, oldX, oldY, "It's not your turn!");
            throw new NotPlayersTurnException();
        }


        if (b.getPiece() != null || (newX + newY) % 2 == 0) {
            noMove(playerNumber, oldX, oldY, "Invalid position!");
            throw new InvalidBoxException();
        }

        if (Math.abs(newX - oldX) == 1 && newY - oldY == piece.getType().getMoveDir()) {

            try {
                checkForPossibleHit(playerNumber);
            } catch (MustHitException e) {
                noMove(playerNumber, oldX, oldY, "Must hit when possible!");
                throw e;
            }

            playerBoard.getBox(oldX, oldY).setPiece(null);
            b.setPiece(piece);
            piece.setPlace(b);

            updatePlayerBoard(playerNumber, MoveType.NORMAL, newX, newY, oldX, oldY);
            updateOpponentBoard(playerNumber, MoveType.NORMAL, newX, newY, oldX, oldY);

        } else if (Math.abs(newX - oldX) == 2 && (newY - oldY == piece.getType().getMoveDir() * 2 || newY - oldY == piece.getType().getMoveDir() * -2)) {

            int x1 = oldX + (newX - oldX) / 2;
            int y1 = oldY + (newY - oldY) / 2;
            int x2 = (9 - oldX) + ((9 - newX) - (9 - oldX)) / 2;
            int y2 = (9 - oldY) + ((9 - newY) - (9 - oldY)) / 2;

            if (playerBoard.getBox(x1, y1).getPiece() != null && playerBoard.getBox(x1, y1).getPiece().getType() != piece.getType()) {

                playerBoard.getBox(x1, y1).setPiece(null);
                playerBoard.getBox(oldX, oldY).setPiece(null);
                b.setPiece(piece);
                piece.setPlace(b);

                updatePlayerBoard(playerNumber, MoveType.NORMAL, newX, newY, oldX, oldY);
                updateOpponentBoard(playerNumber, MoveType.NORMAL, newX, newY, oldX, oldY);

                updatePlayerBoard(playerNumber, MoveType.HIT, x1, y1, oldX, oldY);
                updateOpponentBoard(playerNumber, MoveType.HIT, x2, y2, oldX, oldY);

            } else {
                noMove(playerNumber, oldX, oldY, "Invalid position!");
                throw new InvalidBoxException();
            }
        } else {
            noMove(playerNumber, oldX, oldY, "Invalid position!");
            throw new InvalidBoxException();
        }

        if (checkersPlayers[1 - playerNumber].allHit()) {
            application.showErrorMessage(playerNumber, "Victory!");
            application.showErrorMessage(1 - playerNumber, "Defeat!");
            application.showWinner(playerNumber);
            application.showErrorMessage(playerNumber, checkersPlayers[playerNumber].getName() + " has won the game");
            application.showErrorMessage(1 - playerNumber, checkersPlayers[playerNumber].getName() + " has won the game");
        }

        player_turn = 1 - playerNumber;
        application.setPlayerTurn(player_turn);
    }

    private void noMove(int playerNumber, int oldX, int oldY, String message) {
        application.showErrorMessage(playerNumber, message);
        int newX = oldX;
        int newY = oldY;
        updatePlayerBoard(playerNumber, MoveType.NONE, newX, newY, oldX, oldY);
    }
}
