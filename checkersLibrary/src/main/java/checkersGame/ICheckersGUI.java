package checkersGame;

public interface ICheckersGUI {
    void notifyStartGame(int playerNumber);
    void setPlayerNumber(int playerNumber, String username);
    void setPlayerTurn(int playerNumber);
    void showErrorMessage(int playerNr, String errorMessage);
    void setOpponentName(int playerNr, String name);
    void showWinner(int playerNumber);
}