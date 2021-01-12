package checkersUnitTests;

import checkersGame.ICheckersGUI;

public class MockCheckersApplication implements ICheckersGUI {
    @Override
    public void notifyStartGame(int playerNumber) {

    }

    @Override
    public void setPlayerNumber(int playerNumber, String username) {

    }

    @Override
    public void setPlayerTurn(int playerNumber) {

    }

    @Override
    public void showErrorMessage(int playerNumber, String errorMessage) {

    }

    @Override
    public void showInfoMessage(int playerNumber, String infoMessage) {

    }

    @Override
    public void setOpponentName(int playerNr, String name) {

    }

    @Override
    public void showWinner(int playerNumber) {

    }

    @Override
    public void placePiecePlayer(int playerNumber, int posX, int posY, boolean hasPiece) {

    }

    @Override
    public void placePieceOpponent(int playerNumber, int posX, int posY, boolean hasPiece) {

    }

    @Override
    public void movePiecePlayer(int playerNumber, int posX, int posY, int oldX, int oldY) {

    }

    @Override
    public void movePieceOpponent(int playerNumber, int posX, int posY, int oldX, int oldY) {

    }

    @Override
    public void hitPiecePlayer(int playerNumber, int posX, int posY) {

    }

    @Override
    public void hitPieceOpponent(int playerNumber, int posX, int posY) {

    }
}
