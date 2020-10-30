package server.sport.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "locations")
public class Location {
    private int locationId;
    private String courtName;
    private Collection<Reservation> reservations;

    @Id
    @Column(name = "location_id", nullable = false)
    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    @Basic
    @Column(name = "court_name", nullable = false, length = 45)
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

    public Location() {
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
