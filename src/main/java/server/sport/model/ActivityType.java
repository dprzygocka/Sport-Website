package server.sport.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "activity_type")
public class ActivityType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_type_id", nullable = false)
    private int activityTypeId;

    @Column(name = "activity_type_name", nullable = false)
    private String activityTypeName;

    @OneToMany(mappedBy = "activityType",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private List<Activity> activities = new ArrayList<>();

    public ActivityType() {
    }

    public ActivityType(String activityTypeName) {
        this.activityTypeName = activityTypeName;
    }

    public long getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(int activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public String getActivityTypeName() {
        return activityTypeName;
    }

    public void setActivityTypeName(String activityTypeName) {
        this.activityTypeName = activityTypeName;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    @Override
    public String toString() {
        return "ActivityType{" +
                "activityTypeId=" + activityTypeId +
                ", activityTypeName='" + activityTypeName + '\'' +
                ", activities=" + activities +
                '}';
    }
}
