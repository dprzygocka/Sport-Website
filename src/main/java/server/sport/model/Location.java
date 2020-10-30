package server.sport.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="location_id", nullable = false)
    private int locationId;

    @Column(name = "court_name", nullable = false)
    private String courtName;

    @OneToMany(mappedBy = "location",
    fetch = FetchType.EAGER,
    cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();

    public Location() {
    }

    public Location(String courtName) {
        this.courtName = courtName;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
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
