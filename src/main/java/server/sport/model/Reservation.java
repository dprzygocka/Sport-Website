package server.sport.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="reservation_id", nullable = false)
    private int reservationId;

    @Column(name = "date_time", nullable = false)
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;

    @OneToOne(mappedBy = "reservation", cascade = CascadeType.ALL)
    private Activity activity;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    public Reservation(Date dateTime, Location location, Activity activity) {
        this.dateTime = dateTime;
        this.location = location;
        this.activity = activity;
    }

    public Reservation() {
    }

    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", dateTime=" + dateTime +
                ", location=" + location +
                '}';
    }
}
