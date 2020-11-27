package rest.controllers;

import models.ScoreboardItem;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import rest.services.ScoreboardContainerService;
import rest.services.ScoreboardService;

import java.util.List;
import java.util.Optional;

@RestController
public class ScoreboardController {

    @Autowired
    private ScoreboardContainerService scoreboardContainerService;

    @Autowired
    private ScoreboardService scoreboardService;

    @GetMapping(value = "/scoreboard")
    public List<ScoreboardItem> getScoreboard() {
        return scoreboardContainerService.getScoreboard();
    }

    @GetMapping(value = "/scoreboard/{id}")
    public Optional<ScoreboardItem> getScoreboardItem(@PathVariable("id") int id) {
        return scoreboardContainerService.getScoreboardItem(id);
    }

    @PutMapping(value = "/scoreboard",
            headers = "Accept=application/json")
    public ResponseEntity<?> updateScoreBoardItem(@RequestBody ScoreboardItem scoreboardItem) {
        scoreboardService.updateScoreboardItem(scoreboardItem);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/scoreboard",
            headers = "Accept=application/json")
    public ResponseEntity<?> addAccount(@RequestBody ScoreboardItem scoreboardItem, UriComponentsBuilder ucBuilder) {
        scoreboardContainerService.addScoreboardItem(scoreboardItem);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/scoreboard/{id}").buildAndExpand(scoreboardItem.getId()).toUri());
        return new ResponseEntity<>(scoreboardItem, headers, HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping(value = "/scoreboard/{id}")
    public ResponseEntity<?> deleteScoreboardItem(@PathVariable ("id") int id) {
        scoreboardContainerService.deleteScoreboardItem(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
