package models;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private int length;
    private int width;
    private int boxSize;
    private List<Box> boxes;
    private int playerNumber;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public Board(int length, int width, int playerNumber) {
        if (length < 0 || width < 0) {
            throw new IllegalArgumentException("Negative length or width not allowed");
        }
        this.length = length;
        this.width = width;
        this.boxSize = length * width;
        this.playerNumber = playerNumber;
        generateBoxes();
    }

    public void generateBoxes() {
        boxes = new ArrayList<>();
        for (int y = 0; y < width; y++) {
            for (int z = 0; z < length; z++) {
                boxes.add(new Box(z, y));
            }
        }
    }

    public Box getBox(int x, int y) {
        for (Box b : boxes) {
            if (b.getxCord() == x && b.getyCord() == y) {
                return b;
            }
        }
        return null;
    }

    public void clearBoard() {
        for (Box b : boxes) {
            if (b.getPiece() != null) {
                b.removePiece();
            }
        }
    }

    public void placePiece(Piece piece) {
        for (int y = 0; y < length; y++) {
            for (int x = 0; x < width; x++) {
                if (y >= 6 && (x + y) % 2 != 0) {
                    Box b = this.getBox(x, y);
                    b.setPiece(piece);
                    piece.setPlace(b);
                }
            }
        }
    }

    public int toBoard(double pixel) {
        return (int) (pixel + boxSize / 2) / boxSize;
    }
}
