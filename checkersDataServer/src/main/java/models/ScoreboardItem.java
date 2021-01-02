package models;

import lombok.NoArgsConstructor;
import lombok.ToString;
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

    @OneToOne(optional = false)
    @JoinColumn
    private User user;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private LocalDate gameDate;

    @Column(nullable = false)
    private boolean win;

    public ScoreboardItem(User user, int score, LocalDate gameDate, boolean win) {
        this.user = user;
        this.score = score;
        this.gameDate = gameDate;
        this.win = win;
    }
}
