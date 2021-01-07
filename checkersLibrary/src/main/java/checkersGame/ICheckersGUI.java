package checkersGame;

public interface ICheckersGUI {
    void notifyStartGame(int playerNumber);
    void setPlayerNumber(int playerNumber, String username);
    void setPlayerTurn(int playerNumber);
    void showErrorMessage(int playerNr, String errorMessage);
    void setOpponentName(int playerNr, String name);
    void showWinner(int playerNumber);
    void placePiecePlayer(int playerNumber, int posX, int posY, boolean hasPiece);
    void placePieceOpponent(int playerNumber, int posX, int posY, boolean hasPiece);
    void movePiecePlayer(int playerNumber, int posX, int posY, int oldX, int oldY);
    void movePieceOpponent(int playerNumber, int posX, int posY, int oldX, int oldY);
    void hitPiecePlayer(int playerNumber, int posX, int posY);
    void hitPieceOpponent(int playerNumber, int posX, int posY);
}
