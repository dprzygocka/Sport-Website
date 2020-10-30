package server.sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.sport.model.Team;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}
