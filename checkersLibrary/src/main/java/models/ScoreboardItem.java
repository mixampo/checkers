package models;

import java.time.LocalDate;

public class ScoreboardItem {
    private int id;
    private User user;
    private int score;
    private LocalDate date;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public ScoreboardItem(int id, User user, int score, LocalDate date, boolean win) {
        this.id = id;
        this.user = user;
        this.score = score;
        this.date = date;
        this.win = win;
    }

    public ScoreboardItem() {
    }
}
