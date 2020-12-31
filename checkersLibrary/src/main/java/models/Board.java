package models;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private int length;
    private int width;
    private List<Box> boxes;

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

    public Board(int length, int width, List<Box> boxes) {
        if (length < 0 || width < 0) {
            throw new IllegalArgumentException("Negative length or width not allowed");
        }
        this.length = length;
        this.width = width;
        this.boxes = boxes;
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
}
