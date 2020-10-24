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
    @Column(name ="location_id")
    private long locationId;

    @Column(name = "google_map_url", nullable = false)
    private String googleMapUrl;

    @Column(name = "court_name", nullable = false)
    private String courtName;

    @OneToMany(mappedBy = "location",
    fetch = FetchType.EAGER,
    cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();
}
