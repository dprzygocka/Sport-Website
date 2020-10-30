package server.sport.model;

import javax.persistence.*;

@Entity
@Table(name = "user_responsibilities", schema = "mydb", catalog = "")
@IdClass(UserResponsibilityPK.class)
public class UserResponsibility {
    private int responsibilityId;
    private int activityId;
    private Responsibility responsibilitiesByResponsibilityId;
    private User usersByUserId;
    private Activity activitiesByActivityId;

    @Id
    @Column(name = "responsibility_id", nullable = false)
    public int getResponsibilityId() {
        return responsibilityId;
    }

    public void setResponsibilityId(int responsibilityId) {
        this.responsibilityId = responsibilityId;
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

        UserResponsibility that = (UserResponsibility) o;

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

    @ManyToOne
    @JoinColumn(name = "responsibility_id", referencedColumnName = "responsility_id", nullable = false)
    public Responsibility getResponsibilitiesByResponsibilityId() {
        return responsibilitiesByResponsibilityId;
    }

    public void setResponsibilitiesByResponsibilityId(Responsibility responsibilitiesByResponsibilityId) {
        this.responsibilitiesByResponsibilityId = responsibilitiesByResponsibilityId;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
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
