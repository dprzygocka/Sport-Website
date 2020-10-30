package server.sport.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "reservations", schema = "mydb", catalog = "")
public class Reservation {
    private int reservationId;
    private Timestamp dateTime;
    private Collection<Activity> activitiesByReservationId;
    private Location locationsByLocationId;

    @Id
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reservation that = (Reservation) o;

        if (reservationId != that.reservationId) return false;
        if (dateTime != null ? !dateTime.equals(that.dateTime) : that.dateTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = reservationId;
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "reservationsByReservationId")
    public Collection<Activity> getActivitiesByReservationId() {
        return activitiesByReservationId;
    }

    public void setActivitiesByReservationId(Collection<Activity> activitiesByReservationId) {
        this.activitiesByReservationId = activitiesByReservationId;
    }

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    public Location getLocationsByLocationId() {
        return locationsByLocationId;
    }

    public void setLocationsByLocationId(Location locationsByLocationId) {
        this.locationsByLocationId = locationsByLocationId;
    }
}
