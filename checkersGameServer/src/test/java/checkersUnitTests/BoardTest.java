package checkersUnitTests;

import models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class BoardTest {

    private int playerNumber;
    private CheckersPlayer player;
    private Board board;
    private List<Piece> pieces;

    @BeforeEach
    void init() {
        this.playerNumber = 0;
        this.player = new CheckersPlayer("player", playerNumber);
        this.board = new Board(10, 10, playerNumber);
        this.pieces = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            pieces.add(new Piece('W'));
        }
    }

    @Test
    public void invalidBoardDimensions() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Board(-10, -10, playerNumber));
    }

    @Test
    public void widthEqualsTen() {
        Assertions.assertEquals(10, board.getWidth());
    }

    @Test
    public void lengthEqualsTen() {
        Assertions.assertEquals(10, board.getLength());
    }

    @Test
    public void boxSizeEqualsTen() {
        Assertions.assertEquals(100, board.getBoxSize());
    }

    @Test
    public void occupyBox() {
        board.occupyBox(9, 2, new Piece('W'));
        System.out.println(board.toString());
        Assertions.assertFalse(board.isBoxAvailable(9, 2));
    }

    @Test
    public void occupyInvalidBox() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> board.occupyBox(0, 4, new Piece('W')));
    }

    @Test
    void occupyOutOfBoundsBox() {
        Assertions.assertThrows(NullPointerException.class, () -> board.occupyBox(11, 10, new Piece('W')));
    }

    @Test
    void piecesPlaced() {
        board.placePiece(pieces);
        int counter = 0;
        System.out.println(board.toString());

        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getLength(); y++) {
                if (!board.isBoxAvailable(x, y)) {
                    counter++;
                }
            }
        }

        Assertions.assertEquals(20, counter);
    }

    @Test
    void boardCleared() {
        board.occupyBox(3, 2, new Piece('W'));
        board.occupyBox(2, 7, new Piece('W'));
        System.out.println(board.toString());

        board.clearBoard();

        System.out.println(board.toString());

        Assertions.assertTrue(board.isBoxAvailable(3, 2));
        Assertions.assertTrue(board.isBoxAvailable(2, 6));
    }

    @Test
    void isBoxAvailable() {
        Assertions.assertTrue(board.isBoxAvailable(5, 3));
    }

    @Test
    void isBoxGreaterThanWidth() {
        Assertions.assertNull(board.getBox(11, 5));
    }

    @Test
    void isBoxGreaterThanHeight() {
        Assertions.assertNull(board.getBox(7, 11));
    }

    @Test
    void isBoxSmallerThanWidth() {
        Assertions.assertNull(board.getBox(-1, 5));
    }

    @Test
    void isBoxSmallerThanHeight() {
        Assertions.assertNull(board.getBox(7, -1));
    }

}
