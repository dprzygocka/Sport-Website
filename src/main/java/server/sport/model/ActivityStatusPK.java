package server.sport.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class ActivityStatusPK implements Serializable {
    private int statusId;
    private int userId;
    private int activityId;

    @Column(name = "status_id", nullable = false)
    @Id
    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    @Column(name = "user_id", nullable = false)
    @Id
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "activity_id", nullable = false)
    @Id
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

        ActivityStatusPK that = (ActivityStatusPK) o;

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
}
