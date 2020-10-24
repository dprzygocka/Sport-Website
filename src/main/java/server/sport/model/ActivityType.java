package server.sport.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "activity_type")
public class ActivityType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "activity_type_id")
    private long activityTypeId;

    @Column(name = "activity_type_name", nullable = false)
    private String activityTypeName;

    @OneToMany(mappedBy = "activity",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private List<Activity> activities = new ArrayList<>();

    public ActivityType() {
    }

    public ActivityType(String activityTypeName, List<Activity> activities) {
        this.activityTypeName = activityTypeName;
        this.activities = activities;
    }

    public long getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(long activityTypeId) {
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
