package server.sport.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "user_responsibilities")
@IdClass(UserResponsibilityPK.class)
public class UserResponsibility {
    private int responsibilityId;
    private int activityId;
    private Responsibility responsibility;
    private User user;
    private Activity activity;

    @Id
    @Column(name = "responsibility_id", nullable = false, insertable = false, updatable = false)
    public int getResponsibilityId() {
        return responsibilityId;
    }

    public void setResponsibilityId(int responsibilityId) {
        this.responsibilityId = responsibilityId;
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
    @JoinColumn(name = "responsibility_id", referencedColumnName = "responsibility_id", nullable = false)
    public Responsibility getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(Responsibility responsibility) {
        this.responsibility = responsibility;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "activity_id", referencedColumnName = "activity_id", nullable = false)
    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public UserResponsibility(int activityId, Responsibility responsibility, User user, Activity activity) {
        this.activityId = activityId;
        this.responsibility = responsibility;
        this.user = user;
        this.activity = activity;
    }

    public UserResponsibility() {
    }

    //deal with Collection later
    @Override
    public String toString() {
        return "UserResponsibility{" +
                "responsibilityId=" + responsibilityId +
                ", activityId=" + activityId +
                ", responsibility=" + responsibility +
                ", user=" + user +
                ", activity=" + activity +
                '}';
    }
}
