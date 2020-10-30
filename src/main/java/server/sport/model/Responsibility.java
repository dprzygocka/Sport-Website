package server.sport.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "responsibilities")
public class Responsibility {
    private int responsibilityId;
    private String responsibilityName;
    private Sport sport;
    private Collection<UserResponsibility> userResponsibilities;

    @Id
    @Column(name = "responsibility_id", nullable = false)
    public int getResponsibilityId() {
        return responsibilityId;
    }

    public void setResponsibilityId(int responsibilityId) {
        this.responsibilityId = responsibilityId;
    }

    @Basic
    @Column(name = "responsibility_name", nullable = false, length = 45)
    public String getResponsibilityName() {
        return responsibilityName;
    }

    public void setResponsibilityName(String responsibilityName) {
        this.responsibilityName = responsibilityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Responsibility that = (Responsibility) o;

        if (responsibilityId != that.responsibilityId) return false;
        if (!Objects.equals(responsibilityName, that.responsibilityName))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = responsibilityId;
        result = 31 * result + (responsibilityName != null ? responsibilityName.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "sport_id", referencedColumnName = "sports_id")
    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    @OneToMany(mappedBy = "responsibilities")
    public Collection<UserResponsibility> getUserResponsibilities() {
        return userResponsibilities;
    }

    public void setUserResponsibilities(Collection<UserResponsibility> userResponsibilities) {
        this.userResponsibilities = userResponsibilities;
    }

    public Responsibility(int responsibilityId, String responsibilityName, Sport sport, Collection<UserResponsibility> userResponsibilities) {
        this.responsibilityId = responsibilityId;
        this.responsibilityName = responsibilityName;
        this.sport = sport;
        this.userResponsibilities = userResponsibilities;
    }

    public Responsibility() {
    }

    @Override
    public String toString() {
        return "Responsibility{" +
                "responsibilityId=" + responsibilityId +
                ", responsibilityName='" + responsibilityName + '\'' +
                ", sport=" + sport +
                ", userResponsibilities=" + userResponsibilities +
                '}';
    }
}
