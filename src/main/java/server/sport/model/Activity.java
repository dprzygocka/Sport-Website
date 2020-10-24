package server.sport.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "activities")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    @Column(name ="activity_id")
    private long activityId;

    @Column(name = "capacity", nullable = false)
    private long capacity;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToOne(fetch = "user")
    @JoinColumn(name = "creator")
    private User creator;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="activity_status",
        joinColumns = {@JoinColumn(name = "activity_id")},
        inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> users = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="user_responsibilities",
            joinColumns = {@JoinColumn(name = "activity_id")},
            inverseJoinColumns = {@JoinColumn(name = "responsibility_id")})
    private List<Responsibility> responsibilities = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @OneToOne
    @JoinColumn(name = "activity_type_id")
    private ActivityType activityType;

    @Column(name = "is_cancelled", nullable = false)
    private boolean isCancelled;

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Responsibility> getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(List<Responsibility> responsibilities) {
        this.responsibilities = responsibilities;
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

    public Activity() {
    }

    public Activity(long capacity, String description, User creator, List<User> users,
                    List<Responsibility> responsibilities, Reservation reservation, ActivityType activityType,
                    boolean isCancelled) {
        this.capacity = capacity;
        this.description = description;
        this.creator = creator;
        this.users = users;
        this.responsibilities = responsibilities;
        this.reservation = reservation;
        this.activityType = activityType;
        this.isCancelled = isCancelled;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "activityId=" + activityId +
                ", capacity=" + capacity +
                ", description='" + description + '\'' +
                ", creator=" + creator +
                ", users=" + users +
                ", responsibilities=" + responsibilities +
                ", reservation=" + reservation +
                ", activityType=" + activityType +
                ", isCancelled=" + isCancelled +
                '}';
    }
}
