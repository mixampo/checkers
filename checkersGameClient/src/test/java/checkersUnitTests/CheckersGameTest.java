package checkersUnitTests;

import checkersGame.ICheckersGame;
import checkersGame.exceptions.InvalidBoxException;
import checkersGame.exceptions.MustHitException;
import checkersGame.exceptions.NotPlayersTurnException;
import checkersGame.exceptions.PointOutOfBoundsException;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class CheckersGameTest {

    private ICheckersGame game;
    private MockCheckersApplication playerApplication;
    private MockCheckersApplication opponentApplication;
    int playerNumber;

    public CheckersGameTest() {
    }

    @BeforeEach
    void setUp() {

    }

    @Test
    public void registerPlayer() {

    }

    @Test
    public void notifyReady() throws NotPlayersTurnException {

    }

    @Test
    public void movePiece() throws InvalidBoxException, NotPlayersTurnException, PointOutOfBoundsException, MustHitException {

    }


    @Test
    public void moveDam() {

    }


    @Test
    public void obtainDam() {

    }

    @Test
    public void hitPiece() {

    }

    @Test
    public void hitMultiplePieces() {

    }

    @Test
    public void startNewGame() {

    }
}
