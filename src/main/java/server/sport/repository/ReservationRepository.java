package server.sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.sport.model.Reservation;
import server.sport.model.Sport;

import java.util.List;

public interface ReservationRepository extends JpaRepository <Reservation, Integer> {

    List<Reservation> findByReservationId(int reservationId);
}
