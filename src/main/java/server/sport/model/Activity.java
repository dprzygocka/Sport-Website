package server.sport.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "activities")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="activity_id")
    private int activityId;

    @Column(name = "activity_name", nullable = false)
    private String activityName;

    @Column(name = "capacity", nullable = false)
    private long capacity;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "creator")
    private User creator;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne
    @JoinColumn(name = "activity_type_id")
    private ActivityType activityType;

    @Column(name = "is_cancelled", nullable = false)
    private boolean isCancelled;

    @OneToOne(mappedBy = "activity")
    private Match match;

    @JoinTable(name = "user_responsibilities",
            joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @MapKeyJoinColumn(name = "responsibility_id")
    @ElementCollection
    private Map<Responsibility, User> userResponsibility = new HashMap<>();

    @JoinTable(name = "activity_status",
            joinColumns = @JoinColumn(name = "activity_id"),
            inverseJoinColumns = @JoinColumn(name = "status_id"))
    @MapKeyJoinColumn(name = "user_id")
    @ElementCollection
    private Map<User, Status> userStatus = new HashMap<>();

    public Activity() {
    }

    public Activity(String activityName, long capacity, String description, User creator, Reservation reservation,
                    ActivityType activityType, boolean isCancelled, Match match) {
        this.activityName = activityName;
        this.capacity = capacity;
        this.description = description;
        this.creator = creator;
        this.reservation = reservation;
        this.activityType = activityType;
        this.isCancelled = isCancelled;
        this.match = match;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Map<Responsibility, User> getUserResponsibility() {
        return userResponsibility;
    }

    public void setUserResponsibility(Map<Responsibility, User> userResponsibility) {
        this.userResponsibility = userResponsibility;
    }

    public Map<User, Status> getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Map<User, Status> userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "activityId=" + activityId +
                ", activityName='" + activityName + '\'' +
                ", capacity=" + capacity +
                ", description='" + description + '\'' +
                ", creator=" + creator +
                ", reservation=" + reservation +
                ", activityType=" + activityType +
                ", isCancelled=" + isCancelled +
                ", match=" + match +
                ", userResponsibility=" + userResponsibility +
                ", userStatus=" + userStatus +
                '}';
    }
}
