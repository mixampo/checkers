package websocket.controllers;

import models.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class PlayerController {
    final SimpMessagingTemplate websocket;
    private final static String endpoint = "/game/checkers";

    @Autowired
    public PlayerController(SimpMessagingTemplate websocket) {
        this.websocket = websocket;
    }

    @MessageMapping("/move-piece")
    @SendTo("/game/checkers")
    public Player movePiece(String test) throws Exception {
        return new Player("Hello, " + HtmlUtils.htmlEscape(test) + "!");
    }
}
