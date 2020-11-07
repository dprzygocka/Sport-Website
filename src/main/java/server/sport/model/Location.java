package server.sport.model;

import org.hibernate.annotations.GenericGenerator;
import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "locations")
public class Location {
    private int locationId;
    private String courtName; // Hall A Lyngby, Hall B Lynby, Gulbergsgade court, Rantzausgade needs to be UNIQUE values
    private Collection<Reservation> reservations;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    @Column(name = "location_id", nullable = false)
    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    @Basic
    @Column(name = "court_name", nullable = false, length = 45, unique = true)
    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (locationId != location.locationId) return false;
        if (courtName != null ? !courtName.equals(location.courtName) : location.courtName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = locationId;
        result = 31 * result + (courtName != null ? courtName.hashCode() : 0);
        return result;
    }

    @JsonBackReference
    @OneToMany(mappedBy = "location")
    public Collection<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Collection<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Location(String courtName, Collection<Reservation> reservations) {
        this.courtName = courtName;
        this.reservations = reservations;
    }

    public Location(String courtName){
        this.courtName = courtName;
    }

    public Location() {
    }

    public Location(int locationId){
        this.locationId = locationId;
    }

    public Location(String courtName) {
        this.courtName = courtName;
    }


    @Override
    public String toString() {
        return "Location{" +
                "locationId=" + locationId +
                ", courtName='" + courtName + '\'' +
                ", reservations=" + reservations +
                '}';
    }
}
