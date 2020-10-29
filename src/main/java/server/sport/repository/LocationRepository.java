package server.sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.sport.model.Location;

import java.util.Date;
import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    List<Location> findAllByDateAndTime(Date date);
}
