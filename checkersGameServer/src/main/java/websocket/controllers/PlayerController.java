package websocket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import shared.PostMessage;

@Controller
public class PlayerController {
    final SimpMessagingTemplate websocket;
    private final static String endpoint = "/game/checkers";

    @Autowired
    public PlayerController(SimpMessagingTemplate websocket) {
        this.websocket = websocket;
    }

    @MessageMapping("/test")
    @SendTo("/game/checkers")
    public PostMessage test(String test) {
        String msg = "Shit works ayyy: " + test;
        return new PostMessage(msg);
    }

    @MessageMapping("/notify-ready")
    @SendTo("/game/checkers")
    public void notifyReady() {

    }

    @MessageMapping("/move-piece")
    @SendTo("/game/checkers")
    public void movePiece() {
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
    public void hitPieces() {

    }

    @MessageMapping("/start-new")
    @SendTo("/game/checkers")
    public void startNewGame() {

    }
}
