package checkersGame;

import checkersGame.exceptions.InvalidBoxException;
import checkersGame.exceptions.NotPlayersTurnException;
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
    public void movePiece(int playerNumber, Piece piece, int newX, int newY) throws InvalidBoxException, NotPlayersTurnException {
        if (player_turn != playerNumber) {
            throw new NotPlayersTurnException();
        }

        Board gameBoard = checkersPlayers[playerNumber].getGameBoard();
        Box b = gameBoard.getBox(newX, newY);

        if (b.getPiece() != null || (newX + newY) % 2 == 0) {
            throw new InvalidBoxException();
        }

        int x0 = gameBoard.toBoard(piece.getOldX());
        int y0 = gameBoard.toBoard(piece.getOldY());

        if (Math.abs(newX - x0) == 1 && newY - y0 == piece.getType().getMoveDir()) {
            
            gameBoard.getBox(x0, y0).setPiece(null);
            b.setPiece(piece);
            piece.setPlace(b);

            //TODO add hit movetype
        } else if (Math.abs(newX - x0) == 2 && newY - y0 == piece.getType().getMoveDir() * 2) {

            int x1 = x0 + (newX - x0) / 2;
            int y1 = y0 + (newY - y0) / 2;

            if (gameBoard.getBox(x1, y1).getPiece() != null && gameBoard.getBox(x1, y1).getPiece().getType() != piece.getType()) {

            }
        }


        if (checkersPlayers[1 - playerNumber].allHit()) {
            application.showErrorMessage(playerNumber, "Winner");
            application.showWinner(playerNumber);
            application.showErrorMessage(playerNumber, checkersPlayers[playerNumber].getName() + " has won the game");
        }

        player_turn = 1 - playerNumber;
        application.setPlayerTurn(player_turn);
        updatePlayerBoard(playerNumber);
    }
}
