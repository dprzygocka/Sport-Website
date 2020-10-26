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

    @OneToMany(mappedBy = "sport",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private List<TeamCategory> teamCategories = new ArrayList<>();

    public Sport(String sportName){
        this.sportName =sportName;
    }

    public Sport() {
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

    public List<TeamCategory> getTeamCategories() {
        return teamCategories;
    }

    public void setTeamCategories(List<TeamCategory> teamCategories) {
        this.teamCategories = teamCategories;
    }

    @Override
    public String toString() {
        return "Sport{" +
                "sportId=" + sportId +
                ", sportName='" + sportName + '\'' +
                ", teamCategories=" + teamCategories +
                '}';
    }
}
