package models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreboardItem {

    @JsonProperty("id")
    private int id;

    @JsonProperty("user")
    private User user;

    @JsonProperty("score")
    private int score;

    @JsonProperty("gameDate")
    private LocalDate gameDate;

    @JsonProperty("win")
    private boolean win;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDate getGameDate() {
        return gameDate;
    }

    public void setGameDate(LocalDate gameDate) {
        this.gameDate = gameDate;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public ScoreboardItem(int id, User user, int score, LocalDate gameDate, boolean win) {
        this.id = id;
        this.user = user;
        this.score = score;
        this.gameDate = gameDate;
        this.win = win;
    }

    public ScoreboardItem() {
    }
}
