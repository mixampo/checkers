package gui.models;


import gui.scenes.CheckersClientGui;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Box extends Rectangle {
    private Piece piece;

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Box(boolean light, int x, int y) {
        setWidth(CheckersClientGui.BOX_SIZE);
        setHeight(CheckersClientGui.BOX_SIZE);

        relocate(x * CheckersClientGui.BOX_SIZE, y * CheckersClientGui.BOX_SIZE);
        setFill(light ? Color.BLACK : Color.DARKGREY);
    }

    public boolean hasPiece() {
        return piece != null;
    }
}
