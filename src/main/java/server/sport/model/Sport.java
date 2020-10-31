package server.sport.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "sports")
public class Sport {
    private int sportsId;
    private String sportsName;
    @JsonBackReference
    private Collection<Responsibility> responsibilities;
    private Collection<Team> teams;

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
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

    @OneToMany(mappedBy = "sport")
    public Collection<Responsibility> getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(Collection<Responsibility> responsibilities) {
        this.responsibilities = responsibilities;
    }

    @OneToMany(mappedBy = "sport")
    public Collection<Team> getTeams() {
        return teams;
    }

    public void setTeams(Collection<Team> teams) {
        this.teams = teams;
    }

    public Sport() {
    }

    @Override
    public String toString() {
        return "Sport{" +
                "sportsId=" + sportsId +
                ", sportsName='" + sportsName + '\'' +
                ", responsibilities=" + responsibilities +
                ", teams=" + teams +
                '}';
    }

    public Sport(String sportsName, Collection<Responsibility> responsibilities, Collection<Team> teams) {
        this.sportsName = sportsName;
        this.responsibilities = responsibilities;
        this.teams = teams;
    }

    public Sport(String sportsName) {
        this.sportsName = sportsName;
    }
}
