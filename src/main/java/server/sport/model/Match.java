package server.sport.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "match")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    @Column(name ="match_id")
    private long matchId;

    @Column(name = "score", nullable = false)
    private int score;

    @OneToOne(fetch = "user")
    @JoinColumn(name = "player_of_the_match")
    private User playerOfTheMatch;

    @OneToOne(fetch = "activity")
    @JoinColumn(name = "activity_id")
    private Activity activity;
}
