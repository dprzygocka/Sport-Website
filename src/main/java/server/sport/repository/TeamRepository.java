package server.sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.sport.model.Activity;
import server.sport.model.Team;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Integer> {

    @Query(value = "SELECT *  FROM dbo.teams t JOIN dbo.sports s ON t.sports_id = s.sports_id", nativeQuery = true)
    List<Team> findTeamsWithoutUsers();


}
