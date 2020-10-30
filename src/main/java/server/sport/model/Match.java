package server.sport.model;

import javax.persistence.*;

@Entity
@Table(name = "matches")
public class Match {
    private int matchesId;
    private Integer score;
    private User playerOfTheMatch;
    private Activity activity;

    @Id
    @Column(name = "matches_id", nullable = false)
    public int getMatchesId() {
        return matchesId;
    }

    public void setMatchesId(int matchesId) {
        this.matchesId = matchesId;
    }

    @Basic
    @Column(name = "score", nullable = true)
    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Match match = (Match) o;

        if (matchesId != match.matchesId) return false;
        if (score != null ? !score.equals(match.score) : match.score != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = matchesId;
        result = 31 * result + (score != null ? score.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "player_of_the_match", referencedColumnName = "user_id")
    public User getPlayerOfTheMatch() {
        return playerOfTheMatch;
    }

    public void setPlayerOfTheMatch(User playerOfTheMatch) {
        this.playerOfTheMatch = playerOfTheMatch;
    }

    @ManyToOne
    @JoinColumn(name = "activity_id", referencedColumnName = "activity_id", nullable = false)
    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Match() {
    }

    public Match(Integer score, User playerOfTheMatch, Activity activity) {
        this.score = score;
        this.playerOfTheMatch = playerOfTheMatch;
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "Match{" +
                "matchesId=" + matchesId +
                ", score=" + score +
                ", playerOfTheMatch=" + playerOfTheMatch +
                ", activity=" + activity +
                '}';
    }
}
