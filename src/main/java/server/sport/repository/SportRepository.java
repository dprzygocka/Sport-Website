package server.sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.sport.model.Sport;

import java.util.List;
import java.util.Optional;

public interface SportRepository extends JpaRepository <Sport, Integer> {

    List<Sport> findBySportName(String sportName); //maybe this is not necessary

    Optional<Sport> findSportBySportName(String sportsName);
}