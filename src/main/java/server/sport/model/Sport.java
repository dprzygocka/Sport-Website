package server.sport.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "sports", schema = "mydb", catalog = "")
public class Sport {
    private int sportsId;
    private String sportsName;
    private Collection<Responsibility> responsibilitiesBySportsId;
    private Collection<Team> teamsBySportsId;

    @Id
    @Column(name = "sports_id", nullable = false)
    public int getSportsId() {
        return sportsId;
    }

    public void setSportsId(int sportsId) {
        this.sportsId = sportsId;
    }

    @Basic
    @Column(name = "sports_name", nullable = false, length = 45)
    public String getSportsName() {
        return sportsName;
    }

    public void setSportsName(String sportsName) {
        this.sportsName = sportsName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sport sport = (Sport) o;

        if (sportsId != sport.sportsId) return false;
        if (sportsName != null ? !sportsName.equals(sport.sportsName) : sport.sportsName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sportsId;
        result = 31 * result + (sportsName != null ? sportsName.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "sportsBySportId")
    public Collection<Responsibility> getResponsibilitiesBySportsId() {
        return responsibilitiesBySportsId;
    }

    public void setResponsibilitiesBySportsId(Collection<Responsibility> responsibilitiesBySportsId) {
        this.responsibilitiesBySportsId = responsibilitiesBySportsId;
    }

    @OneToMany(mappedBy = "sportsBySportsId")
    public Collection<Team> getTeamsBySportsId() {
        return teamsBySportsId;
    }

    public void setTeamsBySportsId(Collection<Team> teamsBySportsId) {
        this.teamsBySportsId = teamsBySportsId;
    }
}
