package server.sport.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "teams", schema = "mydb", catalog = "")
public class Team {
    private int teamId;
    private String teamName;
    private Sport sportsBySportsId;
    private Collection<User> usersByTeamId;

    @Id
    @Column(name = "team_id", nullable = false)
    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    @Basic
    @Column(name = "team_name", nullable = false, length = 45)
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        if (teamId != team.teamId) return false;
        if (teamName != null ? !teamName.equals(team.teamName) : team.teamName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = teamId;
        result = 31 * result + (teamName != null ? teamName.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "sports_id", referencedColumnName = "sports_id", nullable = false)
    public Sport getSportsBySportsId() {
        return sportsBySportsId;
    }

    public void setSportsBySportsId(Sport sportsBySportsId) {
        this.sportsBySportsId = sportsBySportsId;
    }

    @OneToMany(mappedBy = "teamsByTeamId")
    public Collection<User> getUsersByTeamId() {
        return usersByTeamId;
    }

    public void setUsersByTeamId(Collection<User> usersByTeamId) {
        this.usersByTeamId = usersByTeamId;
    }
}
