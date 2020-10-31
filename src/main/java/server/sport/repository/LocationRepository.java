package server.sport.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import server.sport.model.Location;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    List<Location> findAllByReservationsDateTime(Date date);

    Optional<Location> findLocationByCourtName(String courtName);
}
