package gui.scenes;

import checkersGame.ICheckersGUI;
import checkersGame.ICheckersGame;
import checkersGame.SingleCheckersGame;
import checkersGame.exceptions.CheckersGameFullException;
import checkersGame.exceptions.InvalidBoxException;
import checkersGame.exceptions.NotPlayersTurnException;
import gui.CheckersWebsocketGame;
import gui.models.*;
import gui.shared.SceneSwitcher;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.User;
import models.PieceType;


public class CheckersClientGui extends Application implements ICheckersGUI {

    public static final int BOX_SIZE = 100;
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;

    //Buttons
    private final int BUTTONWIDTH = 180; // Width of button

    private Box[][] board = new Box[WIDTH][HEIGHT];

    private Group boxGroup = new Group();
    private Group pieceGroup = new Group();

    //Buttons
    private Button btnReadyToPlay;

    // Opponent's name
    private String opponentName;

    private boolean playingMode = false;

    private ICheckersGame game;
    private User loggedInUser;

    int playerNumber = 0;
    private int playerTurn = 0;

    private SceneSwitcher sceneSwitcher;

    public static void main(String[] args) {
        launch(args);
    }

    public CheckersClientGui(User user) {
        this.loggedInUser = user;
        sceneSwitcher = new SceneSwitcher();
        opponentName = "Opponent";
    }

    public CheckersClientGui() {
        sceneSwitcher = new SceneSwitcher();
        opponentName = "Opponent";
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("Checkers - Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        notifyReady();
    }

    @Override
    public void stop() {
        //TODO add disconnect from websocket functionalality
    }

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * BOX_SIZE, HEIGHT * BOX_SIZE);
        root.getChildren().addAll(boxGroup, pieceGroup);

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Box tile = new Box((x + y) % 2 == 0, x, y);
                board[x][y] = tile;

                boxGroup.getChildren().add(tile);
            }
        }
        return root;
    }

    private MoveResult tryMove(Piece piece, int newX, int newY) {
        if (playingMode) {
            try {
                game.movePiece(playerNumber, new models.Piece(piece.getType(), piece.getOldX(), piece.getOldY()), newX, newY);

                //TODO continue moving this logic to MultiCheckersGame.java
//                if (board[newX][newY].hasPiece() || (newX + newY) % 2 == 0) {
//                    return new MoveResult(MoveType.NONE);
//                }
//
//                int x0 = toBoard(piece.getOldX());
//                int y0 = toBoard(piece.getOldY());
//
//                if (Math.abs(newX - x0) == 1 && newY - y0 == piece.getType().getMoveDir()) {
//                    return new MoveResult(MoveType.NORMAL);
//                } else if (Math.abs(newX - x0) == 2 && newY - y0 == piece.getType().getMoveDir() * 2) {
//
//                    int x1 = x0 + (newX - x0) / 2;
//                    int y1 = y0 + (newY - y0) / 2;
//
//                    if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType()) {
//                        return new MoveResult(MoveType.HIT, board[x1][y1].getPiece());
//                    }
//                }
            } catch (InvalidBoxException e) {
                sceneSwitcher.showAlert("Checkers - error", "Invalid box, can't move piece to the new position", "");
                e.printStackTrace();
            } catch (NotPlayersTurnException e) {
                sceneSwitcher.showAlert("Checkers - error", "Not your turn!", "");
                e.printStackTrace();
            }
        } else {
            sceneSwitcher.showAlert("Checkers - notification", "Wait for the other player to ready up", "Message for player with player number: " + playerNumber);
        }
        return new MoveResult(MoveType.NONE);
    }

    private int toBoard(double pixel) {
        return (int) (pixel + BOX_SIZE / 2) / BOX_SIZE;
    }


    private Piece makePiece(PieceType type, int x, int y) {
        Piece piece = new Piece(type, x, y);

        piece.setOnMouseReleased(e -> {
            int newX = toBoard(piece.getLayoutX());
            int newY = toBoard(piece.getLayoutY());

            MoveResult result;

            if (newX < 0 || newY < 0 || newX >= WIDTH || newY >= HEIGHT) {
                result = new MoveResult(MoveType.NONE);
            } else {
                result = tryMove(piece, newX, newY);
            }

            int x0 = toBoard(piece.getOldX());
            int y0 = toBoard(piece.getOldY());

            switch (result.getType()) {
                case NONE:
                    piece.abortMove();
                    break;
                case NORMAL:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);
                    break;
                case HIT:
                    piece.move(newX, newY);
                    board[x0][y0].setPiece(null);
                    board[newX][newY].setPiece(piece);

                    Piece otherPiece = result.getPiece();
                    board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
                    pieceGroup.getChildren().remove(otherPiece);
                    break;
            }
        });

        return piece;
    }

    public void registerPlayer(boolean singlePlayermode) throws CheckersGameFullException {
        if (!singlePlayermode) {
            game = new CheckersWebsocketGame();
            game.registerPlayer(this.loggedInUser, this);
        } else {
            game = new SingleCheckersGame();
            System.out.println("Different implementation");
        }
    }

    private void notifyReady() {
        try {
            game.notifyReady(playerNumber);
        } catch (NotPlayersTurnException e) {
            showErrorMessage(playerNumber, "Wait for the other player to ready up");
            e.printStackTrace();
        }
    }

    @Override
    public void notifyStartGame(int playerNumber) {
        playingMode = true;
        sceneSwitcher.showAlert("Checkers", "Game has started!", ("Player with player number: " + playerTurn + " has the beginning turn"));
    }

    @Override
    public void setPlayerNumber(int playerNumber, String username) {
        if (!this.loggedInUser.getUsername().equals(username)) {
            return;
        }
        this.playerNumber = playerNumber;
        sceneSwitcher.showAlert("Join game", (username + " has joined the game"), ("Message for Player with playerNumber: " + playerNumber));
    }

    @Override
    public void setPlayerTurn(int playerNumber) {
        this.playerTurn = playerNumber;
    }

    @Override
    public void showErrorMessage(int playerNumber, String errorMessage) {
        sceneSwitcher.showAlert("Error", ("Error for player with playerNumber: " + playerNumber), errorMessage);
    }

    @Override
    public void setOpponentName(int playerNumber, String name) {
        sceneSwitcher.showAlert("Checkers", ("Your opponent is: " + name), "");
        opponentName = name;
    }

    @Override
    public void showWinner(int playerNumber) {

    }

    @Override
    public void showPiecePlayer(int playerNumber, int posX, int posY) {
        Platform.runLater(() -> {
            Piece piece = null;
            Box tile = board[posX][posY];

            if (posY <= 3 && (posX + posY) % 2 != 0) {
                piece = makePiece(PieceType.RED, posX, posY);
            }

            if (posY >= 6 && (posX + posY) % 2 != 0) {
                piece = makePiece(PieceType.WHITE, posX, posY);
            }

            if (piece != null) {
                tile.setPiece(piece);
                pieceGroup.getChildren().add(piece);
            }
        });
    }

    @Override
    public void showPieceOpponent(int playerNumber, int posX, int posY) {

    }
}
