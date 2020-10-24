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

    public Match() {
    }

    public Match(int score, User playerOfTheMatch, Activity activity) {
        this.score = score;
        this.playerOfTheMatch = playerOfTheMatch;
        this.activity = activity;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public User getPlayerOfTheMatch() {
        return playerOfTheMatch;
    }

    public void setPlayerOfTheMatch(User playerOfTheMatch) {
        this.playerOfTheMatch = playerOfTheMatch;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "Match{" +
                "matchId=" + matchId +
                ", score=" + score +
                ", playerOfTheMatch=" + playerOfTheMatch +
                ", activity=" + activity +
                '}';
    }
}
