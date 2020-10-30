package server.sport.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "responsibilities", schema = "mydb", catalog = "")
public class Responsibility {
    private int responsilityId;
    private String responsibilityName;
    private Sport sportsBySportId;
    private Collection<UserResponsibility> userResponsibilitiesByResponsilityId;

    @Id
    @Column(name = "responsility_id", nullable = false)
    public int getResponsilityId() {
        return responsilityId;
    }

    public void setResponsilityId(int responsilityId) {
        this.responsilityId = responsilityId;
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

        if (responsilityId != that.responsilityId) return false;
        if (responsibilityName != null ? !responsibilityName.equals(that.responsibilityName) : that.responsibilityName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = responsilityId;
        result = 31 * result + (responsibilityName != null ? responsibilityName.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "sport_id", referencedColumnName = "sports_id")
    public Sport getSportsBySportId() {
        return sportsBySportId;
    }

    public void setSportsBySportId(Sport sportsBySportId) {
        this.sportsBySportId = sportsBySportId;
    }

    @OneToMany(mappedBy = "responsibilitiesByResponsibilityId")
    public Collection<UserResponsibility> getUserResponsibilitiesByResponsilityId() {
        return userResponsibilitiesByResponsilityId;
    }

    public void setUserResponsibilitiesByResponsilityId(Collection<UserResponsibility> userResponsibilitiesByResponsilityId) {
        this.userResponsibilitiesByResponsilityId = userResponsibilitiesByResponsilityId;
    }
}
