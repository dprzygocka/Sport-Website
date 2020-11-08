package server.sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.sport.model.Team;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Integer> {

    //it takes user either way...
    @Query(value = "SELECT t.team_id,t.team_name, t.sport_id, s.sport_name  " +
            "FROM dbo.teams t JOIN dbo.sports s ON t.sport_id = s.sport_id", nativeQuery = true)
    List<Team> findTeamsWithoutUsers();

    Optional<Team> findTeamByTeamName(String teamName);
}
