package models;

import checkersGame.exceptions.InvalidBoxException;
import utils.BoardPrinter;

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

    public int getBoxSize() {
        return boxSize;
    }

    public void setBoxSize(int boxSize) {
        this.boxSize = boxSize;
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
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < length; y++) {
                boxes.add(new Box(x, y));
            }
        }
    }

    public Box getBox(int x, int y) {
        return boxes.stream().filter(b -> b.getxCord() == x && b.getyCord() == y).findAny().orElse(null);
    }

    public void clearBoard() {
        for (Box b : boxes) {
            if (b.getPiece() != null) {
                b.removePiece();
            }
        }
    }

    public void placePiece(List<Piece> pieces) {
        int count = 0;
        for (int y = 0; y < length; y++) {
            for (int x = 0; x < width; x++) {
                if (y >= 6 && (x + y) % 2 != 0) {
                    Box b = getBox(x, y);
                    b.setPiece(pieces.get(count));
                    pieces.get(count).setPlace(b);
                    count++;
                }
            }
        }
    }

    public int toBoard(double pixel) {
        return (int) (pixel + boxSize / 2) / boxSize;
    }

    public void occupyBox(int x, int y, Piece piece) {
        if ((x + y) % 2 != 0) {
            getBox(x, y).setPiece(piece);
        } else {
            throw new IllegalArgumentException("Can't be placed on this box!");
        }
    }

    public boolean isBoxAvailable(int x, int y) {
        return getBox(x, y).getPiece() == null;
    }

    @Override
    public String toString() {
        return BoardPrinter.toString(boxes);
    }
}
