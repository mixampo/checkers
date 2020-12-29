package checkersGame;

import models.CheckersPlayer;

public class MultiCheckersGame extends CheckersGame {


    @Override
    public void registerPlayer(String name, ICheckersGUI application) {
        if (this.checkersPlayers[1] != null) {
            this.application.showErrorMessage(-1, "Server is full");
        }
        this.application = application;
        CheckersPlayer opponent = this.checkersPlayers[0];

        if (opponent == null) {
            this.checkersPlayers[0] = new CheckersPlayer(name, 0);
            this.application.setPlayerNumber(0, name);
        } else {
            this.checkersPlayers[1] = new CheckersPlayer(name, 1);
            this.application.setOpponentName(1, checkersPlayers[0].getName());
            this.application.setOpponentName(0, checkersPlayers[1].getName());
            this.application.setPlayerNumber(1, name);
        }

    }
}
