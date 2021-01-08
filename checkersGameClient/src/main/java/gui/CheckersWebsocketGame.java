package gui;

import checkersGame.ICheckersGUI;
import checkersGame.ICheckersGame;
import checkersGame.exceptions.InvalidBoxException;
import checkersGame.exceptions.MustHitException;
import checkersGame.exceptions.NotPlayersTurnException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import communication.GameMessage;
import communication.MessageTypes;
import communication.dto.*;
import models.Piece;
import models.User;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

public class CheckersWebsocketGame extends StompSessionHandlerAdapter implements ICheckersGame {
    private final static WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
    ICheckersGUI application;
    StompSession session;
    ObjectMapper mapper = new ObjectMapper();
    RegisteredPlayer player;

    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        this.session = session;
        System.out.println("Connected");
        StompSession.Subscription subscription = session.subscribe("/game/checkers", this);
    }

    private boolean isDesignatedPlayer(int playerNr) {
        return playerNr == this.player.getNumber();
    }

    public Type getPayloadType(StompHeaders headers) {
        return GameMessage.class;
    }

    @Override
    public synchronized void handleFrame(StompHeaders headers, Object payload) {
        try {
            System.out.println("WEBSOCKET MESSAGE: " + mapper.writeValueAsString(payload));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        GameMessage gm = (GameMessage) payload;
        if (!isDesignatedPlayer(gm.getPlayerNr())) {
            return;
        }

        switch (gm.getMessageType()) {
            case (MessageTypes.REGISTERED):
                try {
                    RegisteredPlayer rp = mapper.readValue(gm.getMessageData(), RegisteredPlayer.class);
                    if (rp.getName().equals(player.getName())) {
                        application.setPlayerNumber(rp.getNumber(), rp.getName());
                        player.setNumber(rp.getNumber());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case (MessageTypes.REGISTERED_OPPONENT):
                try {
                    RegisteredPlayer rp = mapper.readValue(gm.getMessageData(), RegisteredPlayer.class);
                    application.setOpponentName(gm.getPlayerNr(), rp.getName());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                break;
            case (MessageTypes.SET_PLAYER_TURN):
                try {
                    String playerTurn = mapper.readValue(gm.getMessageData(), String.class);
                    application.setPlayerTurn(Integer.parseInt(playerTurn));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                break;
            case (MessageTypes.PLACE_PIECE_PLAYER):
                PlacePiece spp = null;
                try {
                    spp = mapper.readValue(gm.getMessageData(), PlacePiece.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                application.placePiecePlayer(gm.getPlayerNr(), spp.getPosX(), spp.getPosY(), spp.getHasPiece());
                break;
            case (MessageTypes.PLACE_PIECE_OPPONENT):
                PlacePiece spo = null;
                try {
                    spo = mapper.readValue(gm.getMessageData(), PlacePiece.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                application.placePieceOpponent(gm.getPlayerNr(), spo.getPosX(), spo.getPosY(), spo.getHasPiece());
                break;
            case (MessageTypes.MOVE_PIECE_PLAYER):
                MovePiece mpp = null;
                try {
                    mpp = mapper.readValue(gm.getMessageData(), MovePiece.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                application.movePiecePlayer(gm.getPlayerNr(), mpp.getPosX(), mpp.getPosY(), mpp.getOldX(), mpp.getOldY());
                break;
            case (MessageTypes.MOVE_PIECE_OPPONENT):
                MovePiece mpo = null;
                try {
                    mpo = mapper.readValue(gm.getMessageData(), MovePiece.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                application.movePieceOpponent(gm.getPlayerNr(), mpo.getPosX(), mpo.getPosY(), mpo.getOldX(), mpo.getOldY());
                break;
            case (MessageTypes.HIT_PIECE_PLAYER):
                HitPiece hpp = null;
                try {
                    hpp = mapper.readValue(gm.getMessageData(), HitPiece.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                application.hitPiecePlayer(gm.getPlayerNr(), hpp.getPosX(), hpp.getPosY());
                break;
            case (MessageTypes.HIT_PIECE_OPPONENT):
                HitPiece hpo = null;
                try {
                    hpo = mapper.readValue(gm.getMessageData(), HitPiece.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                application.hitPieceOpponent(gm.getPlayerNr(), hpo.getPosX(), hpo.getPosY());
                break;
            case (MessageTypes.NOTIFY_START):
                application.notifyStartGame(gm.getPlayerNr());
                break;
            case (MessageTypes.SHOW_WINNER):
                application.showWinner(Integer.parseInt(gm.getMessageData()));
                break;
            case (MessageTypes.ERROR):
                application.showErrorMessage(gm.getPlayerNr(), gm.getMessageData());
                break;
            case (MessageTypes.INFO):
                application.showInfoMessage(gm.getPlayerNr(), gm.getMessageData());
                break;
            default:
                break;
        }
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        System.err.println(exception.getMessage());
        System.err.println(new String(payload));
    }

    @Override
    public void notifyReady(int playerNumber) throws NotPlayersTurnException  {
        this.session.send("/action/notify-ready", new PlayerAction(playerNumber));
    }

    @Override
    public void registerPlayer(User user, ICheckersGUI application) {
        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        MappingJackson2MessageConverter mc = new MappingJackson2MessageConverter();
        mc.setObjectMapper(new ObjectMapper());
        stompClient.setMessageConverter(mc);
        ListenableFuture<StompSession> session = stompClient.connect("ws://{host}:{port}/start", headers, this, "localhost", 9090);
        try {
            this.session = session.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        this.player = new RegisteredPlayer(user.getUsername(), -1);
        this.session.send("/action/join-game", user);
        this.application = application;
    }

    @Override
    public void movePiece(int playerNumber, Piece piece, int newX, int newY) throws InvalidBoxException, NotPlayersTurnException, MustHitException {
        this.session.send("/action/move-piece", new PositionAction(playerNumber, piece, newX, newY));
    }

    @Override
    public void moveDam() {
    }

    @Override
    public void obtainDam() {

    }

    @Override
    public void hitPiece() {

    }

    @Override
    public void hitMultiplePieces() {

    }

    @Override
    public void startNewGame(int playerNumber) {
        this.session.send("/action/start-new", new PlayerAction(playerNumber));
    }
}
