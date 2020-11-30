package models;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ScoreboardItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn
    private User user;

    @NotNull
    private int score;

    @NotNull
    private LocalDate date;

    @NotNull
    private boolean win;

    public ScoreboardItem(User user, int score, LocalDate date, boolean win) {
        this.user = user;
        this.score = score;
        this.date = date;
        this.win = win;
    }
}
