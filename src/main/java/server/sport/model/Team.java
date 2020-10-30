package server.sport.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "teams")
public class Team {
    private int teamId;
    private String teamName;
    private Sport sport;
    private Collection<User> users;

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
        if (!Objects.equals(teamName, team.teamName)) return false;

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
    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    @OneToMany(mappedBy = "team")
    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public Team(int teamId, String teamName, Sport sport, Collection<User> users) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.sport = sport;
        this.users = users;
    }

    public Team() {
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", sport=" + sport +
                ", users=" + users +
                '}';
    }
}
