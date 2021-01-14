package checkersUnitTests;

import models.Board;
import models.CheckersPlayer;
import models.Piece;
import models.PieceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CheckersPlayerTest {

    private int playerNumber;
    private CheckersPlayer player;
    private List<Piece> pieces;
    private Board playerBoard;

    @BeforeEach
    void init() {
        this.playerNumber = 0;
        this.player = new CheckersPlayer("Player", 0);
        this.playerBoard = player.getGameBoard();
    }

    @Test
    public void playerReadyUp() {
        player.readyUp();

        Assertions.assertTrue(player.getReady());
    }

    @Test
    public void allPiecesHit() {
        player.placePieces();

        for (Piece piece : player.getPieces()) {
            player.hitPiece(piece.getPlace().getxCord(), piece.getPlace().getyCord());
        }

        Assertions.assertTrue(player.allHit());
    }

    @Test
    public void notAllPiecesHit() {
        player.placePieces();

        player.hitPiece(0, 9);
        player.hitPiece(2, 9);
        player.hitPiece(4, 9);
        player.hitPiece(6, 9);
        player.hitPiece(8, 9);

        Assertions.assertFalse(player.allHit());
    }

    @Test
    public void whitePiecesAddedToList() {
        for (Piece piece : player.getPieces()) {
            Assertions.assertEquals(PieceType.WHITE, piece.getType());
        }
    }

    @Test
    public void redPiecesAddedToList() {
        CheckersPlayer opponent = new CheckersPlayer("Opponent", 1);

        for (Piece piece : opponent.getPieces()) {
            Assertions.assertEquals(PieceType.RED, piece.getType());
        }
    }

    @Test
    public void piecesPlaced() {
        player.placePieces();

        for (Piece piece : player.getPieces()) {
            Assertions.assertNotNull(piece.getPlace());
        }
    }

    @Test
    public void pieceAtBoxRetrieved() {
        player.placePieces();

        Piece piece = player.getPieceAtBox(0, 9);

        Assertions.assertNotNull(piece);
        Assertions.assertNotNull(playerBoard.getBox(0, 9).getPiece());

        Assertions.assertEquals(piece, playerBoard.getBox(0, 9).getPiece());
        Assertions.assertEquals(0, piece.getPlace().getxCord());
        Assertions.assertEquals(9, piece.getPlace().getyCord());
    }

    @Test
    public void pieceAtInvalidBoxRetrieved() {
        player.placePieces();

        Piece piece = player.getPieceAtBox(1, 9);

        Assertions.assertNull(piece);
        Assertions.assertNull(playerBoard.getBox(1, 9).getPiece());
    }

    @Test
    public void pieceMoved() {
        player.placePieces();

        int oldX = 1;
        int oldY = 6;
        int newX = 2;
        int newY = 5;

        Piece piece = player.getPieceAtBox(oldX, oldY);

        Assertions.assertNotNull(piece);
        Assertions.assertNotNull(playerBoard.getBox(oldX, oldY).getPiece());

        Assertions.assertEquals(piece, playerBoard.getBox(oldX, oldY).getPiece());
        Assertions.assertEquals(oldX, piece.getPlace().getxCord());
        Assertions.assertEquals(oldY, piece.getPlace().getyCord());

        player.movePiece(oldX, oldY, newX, newY);

        piece = player.getPieceAtBox(newX, newY);

        Assertions.assertNull(playerBoard.getBox(oldX, oldY).getPiece());

        Assertions.assertNotNull(piece);
        Assertions.assertNotNull(playerBoard.getBox(newX, newY).getPiece());

        Assertions.assertEquals(piece, playerBoard.getBox(newX, newY).getPiece());
        Assertions.assertEquals(newX, piece.getPlace().getxCord());
        Assertions.assertEquals(newY, piece.getPlace().getyCord());
    }

    @Test
    public void pieceHit() {
        player.placePieces();

        int posX = 1;
        int posY = 6;

        Piece piece = player.getPieceAtBox(posX, posY);

        Assertions.assertNotNull(piece);
        Assertions.assertNotNull(playerBoard.getBox(posX, posY).getPiece());

        Assertions.assertEquals(piece, playerBoard.getBox(posX, posY).getPiece());
        Assertions.assertEquals(posX, piece.getPlace().getxCord());
        Assertions.assertEquals(posY, piece.getPlace().getyCord());

        player.hitPiece(posX, posY);

        Assertions.assertTrue(piece.isHit());
        Assertions.assertNull(piece.getPlace());
        Assertions.assertNull(player.getPieceAtBox(posX, posY));
    }

    @Test
    public void pieceHitInvalid() {
        player.placePieces();

        int posX = 7;
        int posY = 5;

        Assertions.assertNull(player.getPieceAtBox(posX, posY));
        Assertions.assertThrows(NullPointerException.class, () -> player.hitPiece(posX, posY));
    }
}
