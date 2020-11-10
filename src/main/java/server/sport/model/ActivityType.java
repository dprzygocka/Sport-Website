package server.sport.model;

import org.hibernate.annotations.GenericGenerator;
import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
/*
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "activityTypeId")*/
@Table(name = "activity_types")
public class ActivityType {
    private int activityTypeId;
    private String activityTypeName;
    private Collection<Activity> activities;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
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
        if (!Objects.equals(activityTypeName, that.activityTypeName))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = activityTypeId;
        result = 31 * result + (activityTypeName != null ? activityTypeName.hashCode() : 0);
        return result;
    }

    @JsonBackReference(value="activityType")
    @OneToMany(mappedBy = "activityType")
    public Collection<Activity> getActivities() {
        return activities;
    }

    public void setActivities(Collection<Activity> activities) {
        this.activities = activities;
    }

    public ActivityType(String activityTypeName, Collection<Activity> activities) {
        this.activityTypeName = activityTypeName;
        this.activities = activities;
    }

    public ActivityType() {}

    /*
    //We will look into Collection of activities later
    @Override
    public String toString() {
        return "ActivityType{" +
                "activityTypeId=" + activityTypeId +
                ", activityTypeName='" + activityTypeName + '\'' +
                ", activitiesByActivityTypeId=" + activities +
                '}';
    }*/
}
