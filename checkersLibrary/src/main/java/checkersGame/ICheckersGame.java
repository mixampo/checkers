package checkersGame;

public interface ICheckersGame {
    void registerPlayer(String name, ICheckersGUI application);
    void notifyReady(int playerNumber);
    void movePiece();
    void moveDam();
    void obtainDam();
    void hitPiece();
    void hitMultiplePieces();
    void startNewGame();
}
