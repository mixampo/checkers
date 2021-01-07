package gui.scenes;

import checkersGame.ICheckersGUI;
import checkersGame.ICheckersGame;
import checkersGame.SingleCheckersGame;
import checkersGame.exceptions.CheckersGameFullException;
import checkersGame.exceptions.InvalidBoxException;
import checkersGame.exceptions.NotPlayersTurnException;
import checkersGame.exceptions.PointOutOfBoundsException;
import gui.CheckersWebsocketGame;
import gui.models.*;
import gui.shared.SceneSwitcher;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.User;
import models.PieceType;


public class CheckersClientGui extends Application implements ICheckersGUI {

    public static final int BOX_SIZE = 100;
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;

    private Box[][] board = new Box[WIDTH][HEIGHT];

    private Group boxGroup = new Group();
    private Group pieceGroup = new Group();

    private String opponentName;

    private boolean playingMode = false;

    private ICheckersGame game;
    private User loggedInUser;

    int playerNumber = -1;
    protected int playerTurn = -1;

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
        primaryStage.setMaxHeight(1039);
        primaryStage.setMaxWidth(1016);
        primaryStage.setMinHeight(1039);
        primaryStage.setMinWidth(1016);
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

    private int toBoard(double pixel) {
        return (int) (pixel + BOX_SIZE / 2) / BOX_SIZE;
    }


    private Piece makePiece(PieceType type, int x, int y) {

        Piece piece = new Piece(playerNumber, type, x, y);

        piece.setOnMouseReleased((EventHandler) mouseEvent -> {
            int newX = toBoard(piece.getLayoutX());
            int newY = toBoard(piece.getLayoutY());

            if (playingMode && newX != toBoard(piece.getOldX()) && newY != toBoard(piece.getOldY())) {
                if (newX < 0 || newY < 0 || newX >= WIDTH || newY >= HEIGHT) {
                    sceneSwitcher.showAlert("Checkers - notification", "Point out of bounds!", "Message for player with player number: " + playerNumber);
                    piece.abortMove();
                } else {
                    try {
                        game.movePiece(playerNumber, new models.Piece(piece.getType(), piece.getOldX(), piece.getOldY()), toBoard(piece.getLayoutX()), toBoard(piece.getLayoutY()));
                    } catch (InvalidBoxException | NotPlayersTurnException | PointOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                sceneSwitcher.showAlert("Checkers - notification", "Can't move piece", "Message for player with player number: " + playerNumber);
                piece.abortMove();
            }
//            MoveResult result;
//
//            if (newX < 0 || newY < 0 || newX >= WIDTH || newY >= HEIGHT) {
//                result = new MoveResult(MoveType.NONE);
//            } else {
//                result = tryMove(piece, newX, newY);
//            }

//            int x0 = toBoard(piece.getOldX());
//            int y0 = toBoard(piece.getOldY());

//            switch (result.getType()) {
//                case NONE:
//                    piece.abortMove();
//                    break;
//                case NORMAL:
//                    piece.move(newX, newY);
//                    board[x0][y0].setPiece(null);
//                    board[newX][newY].setPiece(piece);
//                    break;
//                case HIT:
//                    piece.move(newX, newY);
//                    board[x0][y0].setPiece(null);
//                    board[newX][newY].setPiece(piece);
//
//                    Piece otherPiece = result.getPiece();
//                    board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
//                    pieceGroup.getChildren().remove(otherPiece);
//                    break;
//            }
        });
        return piece;
    }

    public void registerPlayer(boolean singlePlayerMode) throws CheckersGameFullException {
        if (!singlePlayerMode) {
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
        sceneSwitcher.showAlert("Checkers - Error", errorMessage, ("Error for player with playerNumber: " + playerNumber));
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
    public void placePiecePlayer(int playerNumber, int posX, int posY, boolean hasPiece) {
        if (this.playerNumber != playerNumber) {
            return;
        }
        Platform.runLater(() -> {
            Box tile = board[posX][posY];

            Piece piece = (playerNumber == 0 && hasPiece) ? makePiece(PieceType.WHITE, posX, posY) : (playerNumber != 0 && hasPiece) ? makePiece(PieceType.RED, posX, posY) : null;

            if (piece != null) {
                tile.setPiece(piece);
                pieceGroup.getChildren().add(piece);
            }
        });
    }

    @Override
    public void placePieceOpponent(int playerNumber, int posX, int posY, boolean hasPiece) {
        if (this.playerNumber != playerNumber) {
            return;
        }
        Platform.runLater(() -> {
            Box tile = board[posX][posY];

            Piece piece = (playerNumber == 0 && hasPiece) ? makePiece(PieceType.RED, posX, posY) : (playerNumber != 0 && hasPiece) ? makePiece(PieceType.WHITE, posX, posY) : null;

            if (piece != null) {
                tile.setPiece(piece);
                pieceGroup.getChildren().add(piece);
            }
        });
    }

    @Override
    public void movePiecePlayer(int playerNumber, int posX, int posY, int oldX, int oldY) {
        if (this.playerNumber != playerNumber) {
            return;
        }
        Platform.runLater(() -> {
            Piece piece = board[oldX][oldY].getPiece();

            if (posX == oldX && posY == oldY) {
                piece.abortMove();
            } else {
                piece.move(posX, posY);
                board[posX][posY].setPiece(piece);
                board[oldX][oldY].setPiece(null);
            }
        });
    }

    @Override
    public void movePieceOpponent(int playerNumber, int posX, int posY, int oldX, int oldY) {
        movePiecePlayer(playerNumber, posX, posY, oldX, oldY);
    }

    @Override
    public void hitPiecePlayer(int playerNumber, int posX, int posY) {
        if (this.playerNumber != playerNumber) {
            return;
        }
        Platform.runLater(() -> {
            Piece piece = board[posX][posY].getPiece();
            board[posX][posY].setPiece(null);
            pieceGroup.getChildren().remove(piece);
        });
    }

    @Override
    public void hitPieceOpponent(int playerNumber, int posX, int posY) {
        hitPiecePlayer(playerNumber, posX, posY);
    }
}
