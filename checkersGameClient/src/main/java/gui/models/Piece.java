package gui.models;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import models.PieceType;

import static gui.scenes.CheckersClientGui.BOX_SIZE;

public class Piece extends StackPane {
    private PieceType type;
    private int playerNumber;

    private double mouseX, mouseY;
    private double oldX, oldY;

    public PieceType getType() {
        return type;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public Piece(int playerNumber, PieceType type, int x, int y) {
        this.type = type;
        this.playerNumber = playerNumber;

        move(x, y);

        Ellipse bg = new Ellipse(BOX_SIZE * 0.3125, BOX_SIZE * 0.26);
        bg.setFill(Color.BLACK);

        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(BOX_SIZE * 0.03);

        bg.setTranslateX((BOX_SIZE - BOX_SIZE * 0.3125 * 2) / 2);
        bg.setTranslateY((BOX_SIZE - BOX_SIZE * 0.26 * 2) / 2 + BOX_SIZE * 0.07);

        Ellipse ellipse = new Ellipse(BOX_SIZE * 0.3125, BOX_SIZE * 0.26);
        ellipse.setFill(type == PieceType.RED
                ? Color.valueOf("#c40003") : Color.valueOf("#fff9f4"));

        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(BOX_SIZE * 0.03);

        ellipse.setTranslateX((BOX_SIZE - BOX_SIZE * 0.3125 * 2) / 2);
        ellipse.setTranslateY((BOX_SIZE - BOX_SIZE * 0.26 * 2) / 2);

        getChildren().addAll(bg, ellipse);

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> {
            if (playerNumber == 0 && type == PieceType.WHITE) {
                relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
            } else if (playerNumber == 1 && type == PieceType.RED) {
                relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
            }
        });
    }

    public Piece() {

    }


    public void move(int x, int y) {
        oldX = x * BOX_SIZE;
        oldY = y * BOX_SIZE;
        relocate(oldX, oldY);
    }

    public void abortMove() {
        relocate(oldX, oldY);
    }
}
