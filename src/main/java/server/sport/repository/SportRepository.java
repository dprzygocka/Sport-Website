package server.sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.sport.model.Sport;

import java.util.List;

public interface SportRepository extends JpaRepository <Sport, Integer> {

    List<Sport> findBySportName (String sportName); //maybe this is not necessary
}
