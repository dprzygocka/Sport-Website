package server.sport.model;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "responsibility")
public class Responsibility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="responsibility_id", nullable = false)
    private int responsibilityId;

    @Column(name = "responsibility_name", nullable = false)
    private String responsibilityName;

    @ManyToOne
    @JoinColumn(name="sport_id")
    private Sport sport;

    @JoinTable(name = "user_responsibilities",
            joinColumns = @JoinColumn(name = "responsibility_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @MapKeyJoinColumn(name = "activity_id")
    @ElementCollection
    private Map<Activity, User> userActivity = new HashMap<>();

    public Responsibility() {
    }

    public Responsibility(String responsibilityName, Sport sport) {
        this.responsibilityName = responsibilityName;
        this.sport = sport;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public Map<Activity, User> getUserActivity() {
        return userActivity;
    }

    public void setUserActivity(Map<Activity, User> userActivity) {
        this.userActivity = userActivity;
    }

    public long getResponsibilityId() {
        return responsibilityId;
    }

    public void setResponsibilityId(int responsibilityId) {
        this.responsibilityId = responsibilityId;
    }

    public String getResponsibilityName() {
        return responsibilityName;
    }

    public void setResponsibilityName(String responsibilityName) {
        this.responsibilityName = responsibilityName;
    }

    @Override
    public String toString() {
        return "Responsibility{" +
                "responsibilityId=" + responsibilityId +
                ", responsibilityName='" + responsibilityName + '\'' +
                ", sport='" + sport + '\'' +
                '}';
    }
}
