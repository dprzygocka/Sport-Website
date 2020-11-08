package server.sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.sport.model.Sport;

import java.util.List;
import java.util.Optional;

public interface SportRepository extends JpaRepository <Sport, Integer> {

    Optional<Sport> findBySportName(String sportName);

    Optional<Sport> findSportBySportName(String sportsName);
}