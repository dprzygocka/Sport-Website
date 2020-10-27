package server.sport.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name="status")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="status_id")
    private int statusId;

    @Column(name = "status_name", nullable = false)
    private String statusName;

    @JoinTable(name = "activity_status",
            joinColumns = @JoinColumn(name = "status_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id"))
    @MapKeyJoinColumn(name = "user_id")
    @ElementCollection
    private Map<User, Activity> userActivity = new HashMap<>();

    public Status(String statusName) {
        this.statusName = statusName;
    }

    public Status() {
    }

    public long getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Map<User, Activity> getUserActivity() {
        return userActivity;
    }

    public void setUserActivity(Map<User, Activity> userActivity) {
        this.userActivity = userActivity;
    }

    @Override
    public String toString() {
        return "Status{" +
                "statusId=" + statusId +
                ", statusName='" + statusName + '\'' +
                ", userActivity=" + userActivity +
                '}';
    }
}
