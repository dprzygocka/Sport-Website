package server.sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import server.sport.model.Location;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    List<Location> findDistinctByReservationsStartAtAfterOrReservationsEndAtBeforeOrReservationsIsNull(Timestamp startAt, Timestamp endAt);
    List<Location> findDistinctByReservationsStartAtBeforeAndReservationsEndAtAfter(Timestamp endAt, Timestamp startAt);
    Optional<Location> findLocationByCourtName(String courtName);
    Optional<Location> findLocationByCourtNameOrLocationId(String courtName, Integer locationId);
    List<Location> findByCourtName(String courtName);
    List<Location> deleteById (int locationId);
}
