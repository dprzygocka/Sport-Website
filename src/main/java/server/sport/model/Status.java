package server.sport.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Status {
    private int statusId;
    private String statusName;
    private Collection<ActivityStatus> activityStatusesByStatusId;

    @Id
    @Column(name = "status_id", nullable = false)
    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    @Basic
    @Column(name = "status_name", nullable = false, length = 45)
    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Status status = (Status) o;

        if (statusId != status.statusId) return false;
        if (statusName != null ? !statusName.equals(status.statusName) : status.statusName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = statusId;
        result = 31 * result + (statusName != null ? statusName.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "statusByStatusId")
    public Collection<ActivityStatus> getActivityStatusesByStatusId() {
        return activityStatusesByStatusId;
    }

    public void setActivityStatusesByStatusId(Collection<ActivityStatus> activityStatusesByStatusId) {
        this.activityStatusesByStatusId = activityStatusesByStatusId;
    }
}
