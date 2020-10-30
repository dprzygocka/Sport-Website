package server.sport.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;

@Entity
@Table(name = "activities")
public class Activity {
    private int activityId;
    private String activityName;
    private Integer capacity;
    private String description;
    private boolean isCancelled;
    private User usersByCreatorId;
    private ActivityType activityTypeByActivityTypeId;
    private Reservation reservationsByReservationId;
    private Collection<ActivityStatus> activityStatusesByActivityId;
    private Collection<Match> matchesByActivityId;
    private Collection<UserResponsibility> userResponsibilitiesByActivityId;

    @Id
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
        if (!Arrays.equals(isCancelled, activity.isCancelled)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = activityId;
        result = 31 * result + (activityName != null ? activityName.hashCode() : 0);
        result = 31 * result + (capacity != null ? capacity.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(isCancelled);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "user_id")
    public User getUsersByCreatorId() {
        return usersByCreatorId;
    }

    public void setUsersByCreatorId(User usersByCreatorId) {
        this.usersByCreatorId = usersByCreatorId;
    }

    @ManyToOne
    @JoinColumn(name = "activity_type_id", referencedColumnName = "activity_type_id", nullable = false)
    public ActivityType getActivityTypeByActivityTypeId() {
        return activityTypeByActivityTypeId;
    }

    public void setActivityTypeByActivityTypeId(ActivityType activityTypeByActivityTypeId) {
        this.activityTypeByActivityTypeId = activityTypeByActivityTypeId;
    }

    @ManyToOne
    @JoinColumn(name = "reservation_id", referencedColumnName = "reservation_id", nullable = false)
    public Reservation getReservationsByReservationId() {
        return reservationsByReservationId;
    }

    public void setReservationsByReservationId(Reservation reservationsByReservationId) {
        this.reservationsByReservationId = reservationsByReservationId;
    }

    @OneToMany(mappedBy = "activitiesByActivityId")
    public Collection<ActivityStatus> getActivityStatusesByActivityId() {
        return activityStatusesByActivityId;
    }

    public void setActivityStatusesByActivityId(Collection<ActivityStatus> activityStatusesByActivityId) {
        this.activityStatusesByActivityId = activityStatusesByActivityId;
    }

    @OneToMany(mappedBy = "activitiesByActivityId")
    public Collection<Match> getMatchesByActivityId() {
        return matchesByActivityId;
    }

    public void setMatchesByActivityId(Collection<Match> matchesByActivityId) {
        this.matchesByActivityId = matchesByActivityId;
    }

    @OneToMany(mappedBy = "activitiesByActivityId")
    public Collection<UserResponsibility> getUserResponsibilitiesByActivityId() {
        return userResponsibilitiesByActivityId;
    }

    public void setUserResponsibilitiesByActivityId(Collection<UserResponsibility> userResponsibilitiesByActivityId) {
        this.userResponsibilitiesByActivityId = userResponsibilitiesByActivityId;
    }

    public Activity(String activityName, Integer capacity, String description, boolean isCancelled, User usersByCreatorId, ActivityType activityTypeByActivityTypeId, Reservation reservationsByReservationId, Collection<ActivityStatus> activityStatusesByActivityId, Collection<Match> matchesByActivityId, Collection<UserResponsibility> userResponsibilitiesByActivityId) {
        this.activityName = activityName;
        this.capacity = capacity;
        this.description = description;
        this.isCancelled = isCancelled;
        this.usersByCreatorId = usersByCreatorId;
        this.activityTypeByActivityTypeId = activityTypeByActivityTypeId;
        this.reservationsByReservationId = reservationsByReservationId;
        this.activityStatusesByActivityId = activityStatusesByActivityId;
        this.matchesByActivityId = matchesByActivityId;
        this.userResponsibilitiesByActivityId = userResponsibilitiesByActivityId;
    }

    public Activity() {}

    //We can fix the toString later
    @Override
    public String toString() {
        return "Activity{" +
                "activityId=" + activityId +
                ", activityName='" + activityName + '\'' +
                ", capacity=" + capacity +
                ", description='" + description + '\'' +
                ", isCancelled=" + isCancelled +
                ", usersByCreatorId=" + usersByCreatorId +
                ", activityTypeByActivityTypeId=" + activityTypeByActivityTypeId +
                ", reservationsByReservationId=" + reservationsByReservationId +
                ", activityStatusesByActivityId=" + activityStatusesByActivityId +
                ", matchesByActivityId=" + matchesByActivityId +
                ", userResponsibilitiesByActivityId=" + userResponsibilitiesByActivityId +
                '}';
    }
}
