package server.sport.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name ="users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="user_id", nullable = false)
    private int userId;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name="last_name", nullable = false)
    private String lastName;

    @Column(name="age", nullable = false)
    private int age;

    @Column(name="email", nullable = false)
    private String email;

    @Column(name="gender", nullable = false) //delete?
    private String gender;

    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name="user_type_id")
    private UserType userType;

    @OneToMany(mappedBy = "creator",
            cascade = CascadeType.ALL)
    private List<Activity> createdActivities = new ArrayList<>();

    @OneToOne(mappedBy = "playerOfTheMatch",
    cascade = CascadeType.ALL)
    private Match playerOfTheMatch;

    @JoinTable(name = "user_responsibilities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "responsibility_id"))
    @MapKeyJoinColumn(name = "activity_id")
    @ElementCollection
    private Map<Activity, Responsibility> responsibilityActivity = new HashMap<>();

    @JoinTable(name = "activity_status",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "status_id"))
    @MapKeyJoinColumn(name = "activity_id")
    @ElementCollection
    private Map<Activity, Status> activityStatus = new HashMap<>();

    public User() {
    }

    public User(String firstName, String lastName, int age, String email, String gender, Team team, UserType userType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.gender = gender;
        this.team = team;
        this.userType = userType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public List<Activity> getCreatedActivities() {
        return createdActivities;
    }

    public void setCreatedActivities(List<Activity> createdActivities) {
        this.createdActivities = createdActivities;
    }

    public Match getPlayerOfTheMatch() {
        return playerOfTheMatch;
    }

    public void setPlayerOfTheMatch(Match playerOfTheMatch) {
        this.playerOfTheMatch = playerOfTheMatch;
    }

    public Map<Activity, Responsibility> getResponsibilityActivity() {
        return responsibilityActivity;
    }

    public void setResponsibilityActivity(Map<Activity, Responsibility> responsibilityActivity) {
        this.responsibilityActivity = responsibilityActivity;
    }

    public Map<Activity, Status> getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(Map<Activity, Status> activityStatus) {
        this.activityStatus = activityStatus;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", team=" + team +
                ", userType=" + userType +
                ", createdActivities=" + createdActivities +
                ", playerOfTheMatch=" + playerOfTheMatch +
                ", responsibilityActivity=" + responsibilityActivity +
                ", activityStatus=" + activityStatus +
                '}';
    }
}
