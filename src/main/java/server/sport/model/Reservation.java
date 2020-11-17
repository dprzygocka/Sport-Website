package server.sport.model;

import org.hibernate.annotations.GenericGenerator;
import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "reservations")
public class Reservation {
    private int reservationId;
    private Timestamp startAt;
    private Timestamp endAt;
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
    @Column(name = "start_at", nullable = false)
    public Timestamp getStartAt() {
        return startAt;
    }

    public void setStartAt(Timestamp startAt) {
        this.startAt = startAt;
    }

    @Basic
    @Column(name = "end_at", nullable = false)
    public Timestamp getEndAt() {
        return endAt;
    }

    public void setEndAt(Timestamp endAt) {
        this.endAt = endAt;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reservation that = (Reservation) o;

        if (reservationId != that.reservationId) return false;
        if (!Objects.equals(startAt, that.startAt)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = reservationId;
        result = 31 * result + (startAt != null ? startAt.hashCode() : 0);
        result = 31 * result + (endAt != null ? endAt.hashCode() : 0);
        return result;
    }

    @JsonBackReference
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

    public Reservation() {
    }

    public Reservation(int reservationId, Timestamp startAt, Timestamp endAt, Activity activity, Location location) {
        this.reservationId = reservationId;
        this.startAt = startAt;
        this.endAt = endAt;
        this.activity = activity;
        this.location = location;
    }
/*@Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", startAt=" + startAt +
                ", endAt=" + endAt +
                ", activity=" + activity +
                ", location=" + location +
                '}';
    }*/
}
