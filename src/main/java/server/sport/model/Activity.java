package server.sport.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.Collection;


@Entity
/*@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "activityId")*/
@Table(name = "activities")
public class Activity {
    private int activityId;
    private String activityName;
    private Integer capacity;
    private String description;
    private boolean isCancelled;
    private User creator;
    private ActivityType activityType;
    private Reservation reservation;
    private Collection<ActivityStatus> activityStatuses;
    private Match match;
    private Collection<UserResponsibility> userResponsibilities;
    private Team team;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    @Column(name = "activity_id", nullable = false)

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    @Basic
    @Column(name = "activity_name", nullable = false, length = 45)
    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    @Basic
    @Column(name = "capacity", nullable = true)
    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 450)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "is_cancelled", nullable = false)
    public boolean getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Activity activity = (Activity) o;

        if (activityId != activity.activityId) return false;
        if (activityName != null ? !activityName.equals(activity.activityName) : activity.activityName != null)
            return false;
        if (capacity != null ? !capacity.equals(activity.capacity) : activity.capacity != null) return false;
        if (description != null ? !description.equals(activity.description) : activity.description != null)
            return false;
        if (isCancelled!=activity.isCancelled) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = activityId;
        result = 31 * result + (activityName != null ? activityName.hashCode() : 0);
        result = 31 * result + (capacity != null ? capacity.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + Boolean.hashCode(isCancelled);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "user_id", nullable = false)
    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    @JsonBackReference(value="activityType")
    @ManyToOne
    @JoinColumn(name = "activity_type_id", referencedColumnName = "activity_type_id", nullable = false)
    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "team_id", nullable = true)
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @OneToOne
    @JoinColumn(name = "reservation_id", referencedColumnName = "reservation_id", nullable = false)
    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @OneToMany(mappedBy = "activity")
    public Collection<ActivityStatus> getActivityStatuses() {
        return activityStatuses;
    }

    public void setActivityStatuses(Collection<ActivityStatus> activityStatuses) {
        this.activityStatuses = activityStatuses;
    }


    @OneToOne(mappedBy = "activity")
    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    @OneToMany(mappedBy = "activity")
    @JsonBackReference
    public Collection<UserResponsibility> getUserResponsibilities() {
        return userResponsibilities;
    }

    public void setUserResponsibilities(Collection<UserResponsibility> userResponsibilities) {
        this.userResponsibilities = userResponsibilities;
    }

    public Activity(String activityName, Integer capacity, String description, boolean isCancelled, User creator, ActivityType activityType, Reservation reservation, Collection<ActivityStatus> activityStatuses, Match match, Collection<UserResponsibility> userResponsibilities) {
        this.activityName = activityName;
        this.capacity = capacity;
        this.description = description;
        this.isCancelled = isCancelled;
        this.creator = creator;
        this.activityType = activityType;
        this.reservation = reservation;
        this.activityStatuses = activityStatuses;
        this.match = match;
        this.userResponsibilities = userResponsibilities;
    }

    public Activity() {}

    //We will look into Collection of activities later
   /* @Override
    public String toString() {
        return "Activity{" +
                "activityId=" + activityId +
                ", activityName='" + activityName + '\'' +
                ", capacity=" + capacity +
                ", description='" + description + '\'' +
                ", isCancelled=" + isCancelled +
                ", creator=" + creator +
                ", activityType=" + activityType +
                ", reservation=" + reservation +
                ", activityStatuses=" + activityStatuses.toString() +
                ", match=" + match +
                ", userResponsibilities=" + userResponsibilities.toString() +
                '}';
    }*/
}
