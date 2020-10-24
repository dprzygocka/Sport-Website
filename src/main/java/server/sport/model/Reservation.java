package server.sport.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    @Column(name ="reservation_id")
    private long reservationId;

    @Column(name = "date_time", nullable = false)
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTime;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    public Reservation(Date dateTime, Location location) {
        this.dateTime = dateTime;
        this.location = location;
    }

    public Reservation() {
    }

    public long getReservationId() {
        return reservationId;
    }

    public void setReservationId(long reservationId) {
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
