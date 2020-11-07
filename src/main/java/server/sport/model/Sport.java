package server.sport.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "sports")
public class Sport {
    private int sportId;
    private String sportName;
    @JsonBackReference
    private Collection<Responsibility> responsibilities;
    private Collection<Team> teams;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    @Column(name = "sport_id", nullable = false)
    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    @Basic
    @Column(name = "sport_name", nullable = false, length = 45)
    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sport sport = (Sport) o;

        if (sportId != sport.sportId) return false;
        if (sportName != null ? !sportName.equals(sport.sportName) : sport.sportName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sportId;
        result = 31 * result + (sportName != null ? sportName.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "sport")
    public Collection<Responsibility> getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(Collection<Responsibility> responsibilities) {
        this.responsibilities = responsibilities;
    }

    @JsonBackReference(value="sportTeams")
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
                "sportId=" + sportId +
                ", sportName='" + sportName + '\'' +
                ", responsibilities=" + responsibilities +
                ", teams=" + teams +
                '}';
    }

    public Sport(String sportName, Collection<Responsibility> responsibilities, Collection<Team> teams) {
        this.sportName = sportName;
        this.responsibilities = responsibilities;
        this.teams = teams;
    }

    public Sport(int sportId, String sportName, Collection<Responsibility> responsibilities) {
        this.sportId = sportId;
        this.sportName = sportName;
        this.responsibilities = responsibilities;
    }

    public Sport(String sportName) {
        this.sportName = sportName;
    }

    public Sport(int sportId) {
        this.sportId = sportId;
    }
}
