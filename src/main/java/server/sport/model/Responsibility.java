package server.sport.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "responsibility")
public class Responsibility {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    @Column(name ="responsibility_id", nullable = false)
    private long responsibilityId;

    @Column(name = "responsibility_name", nullable = false)
    private String responsibilityName;

    @JoinTable(name = "user_responsibilities",
            joinColumns = @JoinColumn(name = "responsibility_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @MapKeyJoinColumn(name = "activity_id")
    @ElementCollection
    private Map<Activity, User> userActivity = new HashMap<>();

    public Responsibility() {
    }

    public Responsibility(String responsibilityName, Map<Activity, User> userActivity) {
        this.responsibilityName = responsibilityName;
        this.userActivity = userActivity;
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

    public void setResponsibilityId(long responsibilityId) {
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
                '}';
    }
}
