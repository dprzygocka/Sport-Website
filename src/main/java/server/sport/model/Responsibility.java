package server.sport.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;
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
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
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

    @JsonBackReference(value = "sportResponsibilities")
    @ManyToOne
    @JoinColumn(name = "sport_id", referencedColumnName = "sport_id")
    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    @JsonBackReference
    @OneToMany(mappedBy = "responsibility")
    public Collection<UserResponsibility> getUserResponsibilities() {
        return userResponsibilities;
    }

    public void setUserResponsibilities(Collection<UserResponsibility> userResponsibilities) {
        this.userResponsibilities = userResponsibilities;
    }

    public Responsibility(String responsibilityName, Sport sport, Collection<UserResponsibility> userResponsibilities) {
        this.responsibilityName = responsibilityName;
        this.sport = sport;
        this.userResponsibilities = userResponsibilities;
    }

    public Responsibility() {
    }

    public Responsibility(int responsibilityId) {
        this.responsibilityId = responsibilityId;
    }


    public Responsibility(String responsibilityName, Sport sport) {
        this.responsibilityName = responsibilityName;
        this.sport = sport;
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
