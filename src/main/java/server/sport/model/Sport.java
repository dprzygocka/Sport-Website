package server.sport.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="sports")
public class Sport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sport_id", nullable = false)
    private int sportId;

    @Column(name="sport_name", nullable = false)
    private String sportName;

    @OneToMany
    @JoinColumn(name="sport", nullable = false)
    private List<Responsibility> responsibilities = new ArrayList<>();

    @OneToMany(mappedBy = "sport",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private List<Team> teams = new ArrayList<>();

    public Sport(String sportName){
        this.sportName =sportName;
    }

    public Sport() {
    }

    public List<Responsibility> getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(List<Responsibility> responsibilities) {
        this.responsibilities = responsibilities;
    }

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    @Override
    public String toString() {
        return "Sport{" +
                "sportId=" + sportId +
                ", sportName='" + sportName + '\'' +
                ", teams=" + teams +
                '}';
    }
}
