package server.sport.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    @Column(name ="location_id", nullable = false)
    private long locationId;

    @Column(name = "google_map_url", nullable = false)
    private String googleMapUrl;

    @Column(name = "court_name", nullable = false)
    private String courtName;

    @OneToMany(mappedBy = "location",
    fetch = FetchType.EAGER,
    cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();

    public Location() {
    }

    public Location(String googleMapUrl, String courtName) {
        this.googleMapUrl = googleMapUrl;
        this.courtName = courtName;
    }

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public String getGoogleMapUrl() {
        return googleMapUrl;
    }

    public void setGoogleMapUrl(String googleMapUrl) {
        this.googleMapUrl = googleMapUrl;
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
                ", googleMapUrl='" + googleMapUrl + '\'' +
                ", courtName='" + courtName + '\'' +
                ", reservations=" + reservations +
                '}';
    }
}
