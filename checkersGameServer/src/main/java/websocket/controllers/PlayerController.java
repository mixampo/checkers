package websocket.controllers;

import checkersGame.ICheckersGUI;
import checkersGame.ICheckersGame;
import checkersGame.MultiCheckersGame;
import checkersGame.exceptions.InvalidBoxException;
import checkersGame.exceptions.MustHitException;
import checkersGame.exceptions.NotPlayersTurnException;
import checkersGame.exceptions.PointOutOfBoundsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import communication.GameMessage;
import communication.MessageTypes;
import communication.dto.*;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import service.ApiCallService;
import service.IApiCallService;

@Controller
public class PlayerController implements ICheckersGUI {
    final SimpMessagingTemplate websocket;
    private final static String endpoint = "/game/checkers";
    protected ICheckersGame game;
    private IApiCallService apiCallService;
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public PlayerController(SimpMessagingTemplate websocket) {
        this.websocket = websocket;
        game = new MultiCheckersGame();
        apiCallService = new ApiCallService();
    }

    @Override
    public void setPlayerNumber(int playerNumber, String username) {
        try {
            websocket.convertAndSend(endpoint, new GameMessage(-1 , MessageTypes.REGISTERED, mapper.writeValueAsString(new RegisteredPlayer(username, playerNumber))));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setPlayerTurn(int playerNumber) {
        this.websocket.convertAndSend(endpoint, new GameMessage(playerNumber, MessageTypes.SET_PLAYER_TURN, String.valueOf(playerNumber)));
        this.websocket.convertAndSend(endpoint, new GameMessage(1 - playerNumber, MessageTypes.SET_PLAYER_TURN, String.valueOf(playerNumber)));
    }

    @Override
    public void setOpponentName(int playerNumber, String name) {
        try {
            websocket.convertAndSend(endpoint, new GameMessage(playerNumber, MessageTypes.REGISTERED_OPPONENT, mapper.writeValueAsString(new RegisteredPlayer(name, playerNumber))));
            websocket.convertAndSend(endpoint, new GameMessage(1 - playerNumber, MessageTypes.REGISTERED_OPPONENT, mapper.writeValueAsString(new RegisteredPlayer(name, playerNumber))));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showErrorMessage(int playerNumber, String errorMessage) {
        this.websocket.convertAndSend(endpoint, new GameMessage(playerNumber, MessageTypes.ERROR, errorMessage));
    }

    @Override
    public void showInfoMessage(int playerNumber, String infoMessage) {
        this.websocket.convertAndSend(endpoint, new GameMessage(playerNumber, MessageTypes.INFO, infoMessage));
    }

    @Override
    public void notifyStartGame(int playerNumber) {
        websocket.convertAndSend(endpoint, new GameMessage(playerNumber, MessageTypes.NOTIFY_START, "READY"));
        websocket.convertAndSend(endpoint, new GameMessage(1 - playerNumber, MessageTypes.NOTIFY_START, "READY"));
    }

    @Override
    public void showWinner(int playerNumber) {
        this.websocket.convertAndSend(endpoint, new GameMessage(playerNumber, MessageTypes.SHOW_WINNER, String.valueOf(playerNumber)));
        this.websocket.convertAndSend(endpoint, new GameMessage(1 - playerNumber, MessageTypes.SHOW_WINNER, String.valueOf(playerNumber)));

        //TODO update scoreboardItem on with API
//        apiCallService.updateScoreBoardItem();

        this.game = new MultiCheckersGame();
    }

    @Override
    public void placePiecePlayer(int playerNumber, int posX, int posY, boolean hasPiece) {
        try {
            this.websocket.convertAndSend(endpoint, new GameMessage(playerNumber, MessageTypes.PLACE_PIECE_PLAYER, mapper.writeValueAsString(new PlacePiece(posX, posY, hasPiece))));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void placePieceOpponent(int playerNumber, int posX, int posY, boolean hasPiece) {
        try {
            this.websocket.convertAndSend(endpoint, new GameMessage(playerNumber, MessageTypes.PLACE_PIECE_OPPONENT, mapper.writeValueAsString(new PlacePiece(posX, posY, hasPiece))));
            this.websocket.convertAndSend(endpoint, new GameMessage(1 - playerNumber, MessageTypes.PLACE_PIECE_OPPONENT, mapper.writeValueAsString(new PlacePiece(posX, posY, hasPiece))));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void movePiecePlayer(int playerNumber, int posX, int posY, int oldX, int oldY) {
        try {
            this.websocket.convertAndSend(endpoint, new GameMessage(playerNumber, MessageTypes.MOVE_PIECE_PLAYER, mapper.writeValueAsString(new MovePiece(posX, posY, oldX, oldY))));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void movePieceOpponent(int playerNumber, int posX, int posY, int oldX, int oldY) {
        try {
            this.websocket.convertAndSend(endpoint, new GameMessage(1 - playerNumber, MessageTypes.MOVE_PIECE_OPPONENT, mapper.writeValueAsString(new MovePiece(posX, posY, oldX, oldY))));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hitPiecePlayer(int playerNumber, int posX, int posY) {
        try {
            this.websocket.convertAndSend(endpoint, new GameMessage(playerNumber, MessageTypes.HIT_PIECE_PLAYER, mapper.writeValueAsString(new HitPiece(posX, posY))));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hitPieceOpponent(int playerNumber, int posX, int posY) {
        try {
            this.websocket.convertAndSend(endpoint, new GameMessage(1 - playerNumber, MessageTypes.HIT_PIECE_OPPONENT, mapper.writeValueAsString(new HitPiece(posX, posY))));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    @MessageMapping("/join-game")
    @SendTo("/game/checkers")
    public void registerPlayer(User user) {
        game.registerPlayer(user, this);
    }

    @MessageMapping("/notify-ready")
    @SendTo("/game/checkers")
    public void notifyReadyToPlay(PlayerAction action) {
        try {
            game.notifyReady(action.getPlayerNr());
        } catch (NotPlayersTurnException e) {
            e.printStackTrace();
        }
    }

    @MessageMapping("/move-piece")
    @SendTo("/game/checkers")
    public void movePiece(PositionAction action) throws InvalidBoxException, NotPlayersTurnException, PointOutOfBoundsException, MustHitException {
        game.movePiece(action.getPlayerNr(), action.getPiece(), action.getPosX(), action.getPosY());
    }

    @MessageMapping("/move-dam")
    @SendTo("/game/checkers")
    public void moveDam() {

    }

    @MessageMapping("/obtain-dam")
    @SendTo("/game/checkers")
    public void obtainDam() {

    }

    @MessageMapping("/hit-piece")
    @SendTo("/game/checkers")
    public void hitPiece() {

    }

    @MessageMapping("/hit-pieces")
    @SendTo("/game/checkers")
    public void hitMultiplePieces() {

    }

    @MessageMapping("/start-new")
    @SendTo("/game/checkers")
    public void startNewGame() {

    }
}
