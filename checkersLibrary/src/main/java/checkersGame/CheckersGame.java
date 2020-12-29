package checkersGame;

import models.CheckersPlayer;

public abstract class CheckersGame implements ICheckersGame {

    protected final static int player_count = 2;
    protected int player_turn = -1;
    protected CheckersPlayer[] checkersPlayers = new CheckersPlayer[player_count];
    public ICheckersGUI application;

    @Override
    public void notifyReady(int playerNumber) {
        CheckersPlayer seabattlePlayer = checkersPlayers[playerNumber];
        seabattlePlayer.readyUp();
        if (checkersPlayers[1-playerNumber] != null && checkersPlayers[1 - playerNumber].getReady()) {
            player_turn = 1;
            application.notifyStartGame(player_turn);
            application.setPlayerTurn(player_turn);
        }
    }

    @Override
    public void movePiece() {

    }

    @Override
    public void moveDam() {

    }

    @Override
    public void obtainDam() {

    }

    @Override
    public void hitPiece() {

    }

    @Override
    public void hitMultiplePieces() {

    }

    @Override
    public void startNewGame() {

    }
}
