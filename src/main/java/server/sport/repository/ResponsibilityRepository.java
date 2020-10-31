package server.sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.sport.model.Responsibility;
import server.sport.model.Sport;

import java.util.List;
import java.util.Optional;

public interface ResponsibilityRepository extends JpaRepository<Responsibility, Integer> {
    List<Responsibility> findAllBySport(Sport sport);
}
