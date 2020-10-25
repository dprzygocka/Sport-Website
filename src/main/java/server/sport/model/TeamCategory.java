package server.sport.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="team_category")
public class TeamCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="team_category_id")
    private int teamCategoryId;

    @Column(name="gender")
    private String gender;

    @Column(name="age")
    private int age;

    @ManyToOne
    @JoinColumn(name="sport_id")
    private Sport sport;

    @OneToMany(mappedBy = "teamCategory",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private List<Team> teams = new ArrayList<>();

    public TeamCategory (String gender, int age, Sport sport){
        this.gender = gender;
        this.age = age;
        this.sport = sport;
    }

    public TeamCategory() {
    }

    public int getTeamCategoryId() {
        return teamCategoryId;
    }

    public void setTeamCategoryId(int teamCategoryId) {
        this.teamCategoryId = teamCategoryId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    @Override
    public String toString() {
        return "TeamCategory{" +
                "teamCategoryId=" + teamCategoryId +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", sport=" + sport.toString() +
                ", teams=" + teams.toString() +
                '}';
    }
}
