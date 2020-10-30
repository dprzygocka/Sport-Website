package server.sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.sport.model.Responsibility;

import java.util.List;
import java.util.Optional;

public interface ResponsibilityRepository extends JpaRepository<Responsibility, Integer> {

    @Query(value = "SELECT * FROM responsibilities WHERE responsibilities.sport_id = ?1",
            nativeQuery=true)
    List<Responsibility> findAllBySportId(Integer sport_id);
}
