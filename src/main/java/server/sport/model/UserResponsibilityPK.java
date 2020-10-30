package server.sport.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class UserResponsibilityPK implements Serializable {
    private int responsibilityId;
    private int activityId;

    @Column(name = "responsibility_id", nullable = false, insertable = false, updatable = false)
    @Id
    public int getResponsibilityId() {
        return responsibilityId;
    }

    public void setResponsibilityId(int responsibilityId) {
        this.responsibilityId = responsibilityId;
    }

    @Column(name = "activity_id", nullable = false, insertable = false, updatable = false)
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

        UserResponsibilityPK that = (UserResponsibilityPK) o;

        if (responsibilityId != that.responsibilityId) return false;
        if (activityId != that.activityId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = responsibilityId;
        result = 31 * result + activityId;
        return result;
    }
}
