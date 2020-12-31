package checkersGame;

import checkersGame.exceptions.InvalidBoxException;
import checkersGame.exceptions.NotPlayersTurnException;
import models.Box;
import models.CheckersPlayer;
import models.User;

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
    public void movePiece(int playerNumber, int newX, int newY) throws InvalidBoxException, NotPlayersTurnException {
        if (player_turn != playerNumber) {
            throw new NotPlayersTurnException();
        }

        updateBoard(playerNumber);

        if (checkersPlayers[1 - playerNumber].allHit()) {
            application.showErrorMessage(playerNumber, "Winner");
            application.showWinner(playerNumber);
            application.showErrorMessage(playerNumber, checkersPlayers[playerNumber].getName() + " has won the game");
        }

        player_turn = 1 - playerNumber;
        application.setPlayerTurn(player_turn);
    }
}
