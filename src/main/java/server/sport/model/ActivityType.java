package server.sport.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "activity_type", schema = "mydb", catalog = "")
public class ActivityType {
    private int activityTypeId;
    private String activityTypeName;
    private Collection<Activity> activitiesByActivityTypeId;

    @Id
    @Column(name = "activity_type_id", nullable = false)
    public int getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(int activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    @Basic
    @Column(name = "activity_type_name", nullable = false, length = 45)
    public String getActivityTypeName() {
        return activityTypeName;
    }

    public void setActivityTypeName(String activityTypeName) {
        this.activityTypeName = activityTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActivityType that = (ActivityType) o;

        if (activityTypeId != that.activityTypeId) return false;
        if (activityTypeName != null ? !activityTypeName.equals(that.activityTypeName) : that.activityTypeName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = activityTypeId;
        result = 31 * result + (activityTypeName != null ? activityTypeName.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "activityTypeByActivityTypeId")
    public Collection<Activity> getActivitiesByActivityTypeId() {
        return activitiesByActivityTypeId;
    }

    public void setActivitiesByActivityTypeId(Collection<Activity> activitiesByActivityTypeId) {
        this.activitiesByActivityTypeId = activitiesByActivityTypeId;
    }
}
