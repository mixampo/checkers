package checkersGame;

import checkersGame.exceptions.InvalidBoxException;
import checkersGame.exceptions.MustHitException;
import checkersGame.exceptions.NotPlayersTurnException;
import checkersGame.exceptions.PointOutOfBoundsException;
import models.CheckersPlayer;
import models.Piece;
import models.User;

public interface ICheckersGame {
    void registerPlayer(User user, ICheckersGUI application);
    void notifyReady(int playerNumber) throws NotPlayersTurnException;
    CheckersPlayer getCheckersPlayer(int playerNumber);
    void movePiece(int playerNumber, Piece piece, int newX, int newY) throws InvalidBoxException, NotPlayersTurnException, PointOutOfBoundsException, MustHitException;
    void moveDam();
    void obtainDam();
    void hitPiece();
    void hitMultiplePieces();
    void startNewGame(int playerNumber);
}
