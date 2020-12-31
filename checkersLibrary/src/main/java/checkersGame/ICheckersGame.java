package checkersGame;

import checkersGame.exceptions.InvalidBoxException;
import checkersGame.exceptions.NotPlayersTurnException;
import models.User;

public interface ICheckersGame {
    void registerPlayer(User user, ICheckersGUI application);
    void notifyReady(int playerNumber) throws NotPlayersTurnException;
    void movePiece(int playerNumber, int newX, int newY) throws InvalidBoxException, NotPlayersTurnException;
    void moveDam();
    void obtainDam();
    void hitPiece();
    void hitMultiplePieces();
    void startNewGame(int playerNumber);
}
