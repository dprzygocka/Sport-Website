package server.sport.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "activity_statuses")
@IdClass(ActivityStatusPK.class)
public class ActivityStatus {
    private int statusId;
    private int userId;
    private int activityId;
    private UserStatus userStatus;
    private User user;
    private Activity activity;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    @Column(name = "status_id", nullable = false, insertable = false, updatable = false)
    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    @Id
    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "activity_id", nullable = false, insertable = false, updatable = false)
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
    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "activity_id", referencedColumnName = "activity_id", nullable = false)
    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }


    public ActivityStatus() {
    }

    public ActivityStatus(int userId, int activityId, UserStatus userStatus, User user, Activity activity) {
        this.userId = userId;
        this.activityId = activityId;
        this.userStatus = userStatus;
        this.user = user;
        this.activity = activity;
    }

    @Override
    public String toString() {
        return "ActivityStatus{" +
                "statusId=" + statusId +
                ", userId=" + userId +
                ", activityId=" + activityId +
                ", status=" + userStatus +
                ", user=" + user +
                ", activity=" + activity +
                '}';
    }

}
