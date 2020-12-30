package checkersGame;

import models.User;

public interface ICheckersGame {
    void registerPlayer(User user, ICheckersGUI application);
    void notifyReady(int playerNumber);
    void movePiece();
    void moveDam();
    void obtainDam();
    void hitPiece();
    void hitMultiplePieces();
    void startNewGame();
}
