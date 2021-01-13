import models.Board;
import models.Box;
import models.Piece;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class PieceTest {

    private int playerNumber;
    private Board board;
    private Piece piece;

    @BeforeEach
    void setUp() {
        this.playerNumber = 0;
        this.board = new Board(10, 10, playerNumber);
        piece = new Piece('W');
    }

    @Test
    public void piecePlaced() {
        board.occupyBox(6, 7, piece);
        piece.setPlace(board.getBox(6, 7));

        Box place = piece.getPlace();

        Assertions.assertNotNull(place);
    }

    @Test
    public void boxRemoved() {
        piece.setPlace(board.getBox(5, 6));
        board.occupyBox(5, 6, piece);

        Box b = board.getBox(5, 6) ;

        boolean removed = piece.removeBox(b);

        Assertions.assertTrue(removed);
    }

    @Test
    public void invalidBoxRemoved() {
        piece.setPlace(board.getBox(4, 5));
        board.occupyBox(4, 5, piece);

        Box b = board.getBox(4, 5) ;

        boolean removed = piece.removeBox(b);

        Assertions.assertThrows(IllegalArgumentException.class, () -> piece.removeBox(b));
    }

    @Test
    public void isPiecePlaced() {
        board.occupyBox(4, 7, piece);
        piece.setPlace(board.getBox(4, 7));

        boolean isPlaced = piece.isPlaced();

        Assertions.assertTrue(isPlaced);
    }
}
