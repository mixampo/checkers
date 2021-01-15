package checkersComponentTests;

import checkersGame.ICheckersGame;
import checkersGame.MultiCheckersGame;
import checkersGame.exceptions.InvalidBoxException;
import checkersGame.exceptions.MustHitException;
import checkersGame.exceptions.NotPlayersTurnException;
import checkersGame.exceptions.PointOutOfBoundsException;

import models.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class CheckersGameTest {

    private User playerUser;
    private User opponentUser;
    private ICheckersGame game;
    private MockCheckersApplication playerApplication;
    private MockCheckersApplication opponentApplication;
    int playerNumber;
    int opponentNumber;

    public CheckersGameTest() {
    }

    private void registerPlayers() {
        game.registerPlayer(playerUser, playerApplication);
        game.registerPlayer(opponentUser, opponentApplication);

        playerNumber = playerApplication.getPlayerNumber();
        opponentNumber = opponentApplication.getPlayerNumber();
    }

    @BeforeEach
    void setUp() {
        this.playerUser = new User("TestPlayer", "TesTPlayeR");
        this.opponentUser = new User("TestOpponent", "TesTOpponenT");
        this.playerApplication = new MockCheckersApplication();
        this.opponentApplication = new MockCheckersApplication();
        this.game = new MultiCheckersGame();
    }

    @Test
    public void testRegisterPlayer() {
        String username = "Tester";
        String password = "TesterPassword";

        User registerUser = new User(username, password);

        game.registerPlayer(registerUser, playerApplication);

        Assertions.assertEquals(username, playerApplication.getPlayerName());
        Assertions.assertEquals(0, playerApplication.getPlayerNumber());
        Assertions.assertNull(playerApplication.getOpponentName());
    }

    @Test
    public void testRegisterOpponent() {
        String playerUsername = "Player";
        String playerPassword = "PlayerPassword";

        String opponentUsername = "Opponent";
        String opponentPassword = "OpponentPassword";

        User registerPlayerUser = new User(playerUsername, playerPassword);
        User registerOpponentUser = new User(opponentUsername, opponentPassword);

        game.registerPlayer(registerPlayerUser, playerApplication);
        game.registerPlayer(registerOpponentUser, opponentApplication);

        Assertions.assertEquals(playerUsername, playerApplication.getPlayerName());
        Assertions.assertEquals(0, playerApplication.getPlayerNumber());

        Assertions.assertEquals(opponentUsername, opponentApplication.getPlayerName());
        Assertions.assertEquals(1, opponentApplication.getPlayerNumber());
    }

    @Test
    public void testRegisterServerFull() {
        String playerUsername = "Player";
        String playerPassword = "PlayerPassword";

        String opponentUsername = "Opponent";
        String opponentPassword = "OpponentPassword";

        String thirdUsername = "thirdUser";
        String thirdPassword = "thirdUserPassword";

        User registerPlayerUser = new User(playerUsername, playerPassword);
        User registerOpponentUser = new User(opponentUsername, opponentPassword);
        User registerThirdUser = new User(thirdUsername, thirdPassword);

        MockCheckersApplication thirdUserApplication = new MockCheckersApplication();

        game.registerPlayer(registerPlayerUser, playerApplication);
        game.registerPlayer(registerOpponentUser, opponentApplication);
        game.registerPlayer(registerThirdUser, thirdUserApplication);

        Assertions.assertEquals("Server is full", opponentApplication.getErrorMessage());
    }

    @Test
    public void notifyReady() throws NotPlayersTurnException {
        registerPlayers();

        game.notifyReady(playerNumber);

        boolean gameStarted = playerApplication.isGameStarted();

        Assertions.assertFalse(gameStarted);
    }

    @Test
    public void notifyAllReady() throws NotPlayersTurnException {
        registerPlayers();

        game.notifyReady(playerNumber);
        game.notifyReady(opponentNumber);

        boolean gameStarted = opponentApplication.isGameStarted();

        Assertions.assertTrue(gameStarted);
    }

    @Test
    public void testPlayerTurnSet() throws NotPlayersTurnException {
        registerPlayers();

        game.notifyReady(playerNumber);
        game.notifyReady(opponentNumber);

        boolean gameStarted = opponentApplication.isGameStarted();

        Assertions.assertEquals(0, opponentApplication.getPlayerTurn());
    }


    @Test
    public void testmovePieceRightForward() throws InvalidBoxException, NotPlayersTurnException, PointOutOfBoundsException, MustHitException {
        registerPlayers();
        game.notifyReady(playerNumber);
        game.notifyReady(opponentNumber);

        int oldX = 1;
        int oldY = 6;

        CheckersPlayer player = game.getCheckersPlayer(playerNumber);
        Piece piece = player.getPieceAtBox(oldX, oldY);

        piece.setOldX(100.0);
        piece.setOldY(600.0);

        int newX = 2;
        int newY = 5;

        game.movePiece(playerNumber, piece, newX, newY);

        Assertions.assertNotNull(opponentApplication.getPlayerBoard().getBox(newX, newY).getPiece());
        Assertions.assertNull(opponentApplication.getPlayerBoard().getBox(oldX, oldY).getPiece());
    }

    @Test
    public void testmovePieceLeftForward() throws InvalidBoxException, NotPlayersTurnException, PointOutOfBoundsException, MustHitException {
        registerPlayers();
        game.notifyReady(playerNumber);
        game.notifyReady(opponentNumber);

        int oldX = 9;
        int oldY = 6;

        CheckersPlayer player = game.getCheckersPlayer(playerNumber);
        Piece piece = player.getPieceAtBox(oldX, oldY);

        piece.setOldX(900.0);
        piece.setOldY(600.0);

        int newX = 8;
        int newY = 5;

        game.movePiece(playerNumber, piece, newX, newY);

        Assertions.assertNotNull(opponentApplication.getPlayerBoard().getBox(newX, newY).getPiece());
        Assertions.assertNull(opponentApplication.getPlayerBoard().getBox(oldX, oldY).getPiece());
    }

    @Test
    public void testmovePieceNotPlayersTurn() throws InvalidBoxException, NotPlayersTurnException, PointOutOfBoundsException, MustHitException {
        registerPlayers();
        game.notifyReady(playerNumber);
        game.notifyReady(opponentNumber);

        int oldX = 9;
        int oldY = 6;

        CheckersPlayer opponent = game.getCheckersPlayer(opponentNumber);
        Piece piece = opponent.getPieceAtBox(oldX, oldY);

        piece.setOldX(900.0);
        piece.setOldY(600.0);

        int newX = 8;
        int newY = 5;

        Assertions.assertThrows(NotPlayersTurnException.class, () -> game.movePiece(opponentNumber, piece, newX, newY));
        Assertions.assertEquals("It's not your turn!", opponentApplication.getErrorMessage());
    }

    @Test
    public void testmovePieceOutOfBounds() throws InvalidBoxException, NotPlayersTurnException, PointOutOfBoundsException, MustHitException {
        registerPlayers();
        game.notifyReady(playerNumber);
        game.notifyReady(opponentNumber);

        int oldX = 9;
        int oldY = 6;

        CheckersPlayer player = game.getCheckersPlayer(playerNumber);
        Piece piece = player.getPieceAtBox(oldX, oldY);

        piece.setOldX(900.0);
        piece.setOldY(600.0);

        int newX = 20;
        int newY = 5;

        Assertions.assertThrows(NullPointerException.class, () -> game.movePiece(playerNumber, piece, newX, newY));
    }

    @Test
    public void testmovePieceInvalidBox() throws InvalidBoxException, NotPlayersTurnException, PointOutOfBoundsException, MustHitException {
        registerPlayers();
        game.notifyReady(playerNumber);
        game.notifyReady(opponentNumber);

        int oldX = 3;
        int oldY = 8;

        CheckersPlayer player = game.getCheckersPlayer(playerNumber);
        Piece piece = player.getPieceAtBox(oldX, oldY);

        piece.setOldX(300.0);
        piece.setOldY(800.0);

        int newX = 6;
        int newY = 5;

        Assertions.assertThrows(InvalidBoxException.class, () -> game.movePiece(playerNumber, piece, newX, newY));
        Assertions.assertEquals("Invalid position!", opponentApplication.getErrorMessage());
    }

    @Test
    public void testmovePieceWhenMustHit() throws InvalidBoxException, NotPlayersTurnException, PointOutOfBoundsException, MustHitException {
        registerPlayers();
        game.notifyReady(playerNumber);
        game.notifyReady(opponentNumber);

        CheckersPlayer player = game.getCheckersPlayer(playerNumber);
        CheckersPlayer opponent = game.getCheckersPlayer(opponentNumber);

        Board playerBoard = player.getGameBoard();
        Box playerBox = playerBoard.getBox(6, 5);

        playerBox.setPiece(new Piece(PieceType.RED, opponent));

        int oldX = 1;
        int oldY = 6;

        Piece piece = player.getPieceAtBox(oldX, oldY);

        piece.setOldX(100.0);
        piece.setOldY(600.0);

        int newX = 2;
        int newY = 5;

        Assertions.assertThrows(MustHitException.class, () -> game.movePiece(playerNumber, piece, newX, newY));
        Assertions.assertEquals("Must hit when possible!", opponentApplication.getErrorMessage());
    }

    @Test
    public void testhitPieceForwards() throws InvalidBoxException, NotPlayersTurnException, PointOutOfBoundsException, MustHitException {
        registerPlayers();
        game.notifyReady(playerNumber);
        game.notifyReady(opponentNumber);

        CheckersPlayer player = game.getCheckersPlayer(playerNumber);
        CheckersPlayer opponent = game.getCheckersPlayer(opponentNumber);

        Board playerBoard = player.getGameBoard();
        Board opponentBoard = opponent.getGameBoard();

        Box opponentBox = opponentBoard.getBox(0, 9);
        Piece opponentPiece = opponentBox.getPiece();
        opponentBox.setPiece(null);

        Box playerBox = playerBoard.getBox(6, 5);
        playerBox.setPiece(opponentPiece);

        Box opponentBoxMirrored = opponentBoard.getBox((9 - 6), (9 - 5));
        opponentBoxMirrored.setPiece(opponentPiece);

        int oldX = 5;
        int oldY = 6;

        Piece playerPiece = player.getPieceAtBox(oldX, oldY);

        playerPiece.setOldX(500.0);
        playerPiece.setOldY(600.0);

        int newX = 7;
        int newY = 4;

        game.movePiece(playerNumber, playerPiece, newX, newY);

        Assertions.assertTrue(opponentApplication.getPlayerBoard().isBoxAvailable(oldX, oldY));
        Assertions.assertTrue(opponentApplication.getPlayerBoard().isBoxAvailable(6, 5));
        Assertions.assertFalse(opponentApplication.getPlayerBoard().isBoxAvailable(newX, newY));
    }

    @Test
    public void testHitPieceBackwards() throws InvalidBoxException, NotPlayersTurnException, PointOutOfBoundsException, MustHitException {
        registerPlayers();
        game.notifyReady(playerNumber);
        game.notifyReady(opponentNumber);

        CheckersPlayer player = game.getCheckersPlayer(playerNumber);
        CheckersPlayer opponent = game.getCheckersPlayer(opponentNumber);

        Board playerBoard = player.getGameBoard();
        Board opponentBoard = opponent.getGameBoard();

        Piece p1 = playerBoard.getBox(5, 6).getPiece();
        playerBoard.getBox(5,6).setPiece(null);
        playerBoard.occupyBox(7, 4, p1);

        Piece p2 = opponentBoard.getBox(0,9).getPiece();
        opponentBoard.getBox(0,9).setPiece(null);
        opponentBoard.occupyBox(6,5, p2);

        playerBoard.occupyBox(6,5, p2);
        opponentBoard.occupyBox((9 - 6),(9 - 5), p1);

        int oldX = 7;
        int oldY = 4;
        int newX = 5;
        int newY = 6;

        p1.setOldX(700.0);
        p1.setOldY(400.0);
        p2.setOldX(600.0);
        p2.setOldY(500.0);

        game.movePiece(playerNumber, p1, newX, newY);

        Assertions.assertTrue(opponentApplication.getPlayerBoard().isBoxAvailable(oldX, oldY));
        Assertions.assertTrue(opponentApplication.getPlayerBoard().isBoxAvailable(6, 5));
        Assertions.assertFalse(opponentApplication.getPlayerBoard().isBoxAvailable(newX, newY));
    }

    @Test
    public void testTurnSwitchedAfterSuccess() throws InvalidBoxException, NotPlayersTurnException, PointOutOfBoundsException, MustHitException {
        registerPlayers();
        game.notifyReady(playerNumber);
        game.notifyReady(opponentNumber);

        int oldX = 9;
        int oldY = 6;

        CheckersPlayer player = game.getCheckersPlayer(playerNumber);
        Piece piece = player.getPieceAtBox(oldX, oldY);

        piece.setOldX(900.0);
        piece.setOldY(600.0);

        int newX = 8;
        int newY = 5;

        game.movePiece(playerNumber, piece, newX, newY);

        Assertions.assertEquals(1, opponentApplication.getPlayerTurn());
    }

    @Test
    public void moveDam() {

    }

    @Test
    public void obtainDam() {

    }

    @Test
    public void hitMultiplePieces() {

    }

    @Test
    public void startNewGame() {

    }
}
