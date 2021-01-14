package checkersComponentTests;

import checkersGame.ICheckersGUI;
import checkersGame.ICheckersGame;
import checkersGame.exceptions.InvalidBoxException;
import checkersGame.exceptions.MustHitException;
import checkersGame.exceptions.NotPlayersTurnException;
import checkersGame.exceptions.PointOutOfBoundsException;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

public class CheckersGameTest {

    private ICheckersGame game;
    private ICheckersGUI playerApplication;
    private ICheckersGUI opponentApplication;
    int playerNumber;

    public CheckersGameTest() {
    }

    @BeforeEach
    void setUp() {
        playerApplication = Mockito.mock(ICheckersGUI.class);
        opponentApplication = Mockito.mock(ICheckersGUI.class);
        game = Mockito.mock(ICheckersGame.class);
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
