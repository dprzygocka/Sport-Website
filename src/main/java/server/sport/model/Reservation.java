package server.sport.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "reservations")
public class Reservation {
    private int reservationId;
    private Timestamp dateTime;
    private Activity activity;
    private Location location;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    @Column(name = "reservation_id", nullable = false)
    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    @Basic
    @Column(name = "date_time", nullable = false)
    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public Reservation(Timestamp dateTime, Location location) {
        this.dateTime = dateTime;
        this.location = location;
    }

    public Reservation(Timestamp dateTime, Location location, Activity activity) {
        this.dateTime = dateTime;
        this.location = location;
        this.activity = activity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reservation that = (Reservation) o;

        if (reservationId != that.reservationId) return false;
        if (!Objects.equals(dateTime, that.dateTime)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = reservationId;
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        return result;
    }

    @OneToOne(mappedBy = "reservation")
    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Reservation(Timestamp dateTime, Activity activity, Location location) {
        this.dateTime = dateTime;
        this.activity = activity;
        this.location = location;
    }

    public Reservation() {
    }

    //deal with collection later
    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", dateTime=" + dateTime +
                ", activity=" + activity +
                ", location=" + location +
                '}';
    }
}
