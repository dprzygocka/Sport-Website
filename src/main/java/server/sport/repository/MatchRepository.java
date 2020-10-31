package server.sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.sport.model.Match;
import server.sport.model.Sport;

import java.util.List;

public interface MatchRepository extends JpaRepository <Match, Integer> {

}