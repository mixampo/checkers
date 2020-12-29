import checkersGame.ICheckersGUI;
import checkersGame.ICheckersGame;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import communication.GameMessage;
import communication.MessageTypes;
import communication.dto.PlayerAction;
import communication.dto.RegisteredPlayer;
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
            case (MessageTypes.NOTIFY_START):
                application.notifyStartGame(gm.getPlayerNr());
                break;
            case (MessageTypes.SHOW_WINNER):
                application.showWinner(Integer.parseInt(gm.getMessageData()));
                break;
            case (MessageTypes.ERROR):
                application.showErrorMessage(gm.getPlayerNr(), gm.getMessageData());
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
    public void notifyReady(int playerNumber) {
        this.session.send("/action/notify-ready", new PlayerAction(playerNumber));
    }

    @Override
    public void registerPlayer(String name, ICheckersGUI application) {
        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        MappingJackson2MessageConverter mc = new MappingJackson2MessageConverter();
        mc.setObjectMapper(new ObjectMapper());
        stompClient.setMessageConverter(mc);
        ListenableFuture<StompSession> session = stompClient.connect("ws://{host}:{port}/websocket", headers, this, "localhost", 8082);
        try {
            this.session = session.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        this.player = new RegisteredPlayer(name, -1);
        this.session.send("/action/join-game", name);
        this.application = application;
    }

    @Override
    public void movePiece() {

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
    public void startNewGame() {

    }
}
