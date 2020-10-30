package server.sport.model;

import javax.persistence.*;

@Entity
@Table(name = "activity_status", schema = "mydb", catalog = "")
@IdClass(ActivityStatusPK.class)
public class ActivityStatus {
    private int statusId;
    private int userId;
    private int activityId;
    private Status statusByStatusId;
    private User usersByUserId;
    private Activity activitiesByActivityId;

    @Id
    @Column(name = "status_id", nullable = false)
    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    @Id
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "activity_id", nullable = false)
    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActivityStatus that = (ActivityStatus) o;

        if (statusId != that.statusId) return false;
        if (userId != that.userId) return false;
        if (activityId != that.activityId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = statusId;
        result = 31 * result + userId;
        result = 31 * result + activityId;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "status_id", nullable = false)
    public Status getStatusByStatusId() {
        return statusByStatusId;
    }

    public void setStatusByStatusId(Status statusByStatusId) {
        this.statusByStatusId = statusByStatusId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public User getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(User usersByUserId) {
        this.usersByUserId = usersByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "activity_id", referencedColumnName = "activity_id", nullable = false)
    public Activity getActivitiesByActivityId() {
        return activitiesByActivityId;
    }

    public void setActivitiesByActivityId(Activity activitiesByActivityId) {
        this.activitiesByActivityId = activitiesByActivityId;
    }
}
