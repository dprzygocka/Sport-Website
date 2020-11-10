package server.sport.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import server.sport.model.Team;
import server.sport.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE dbo.users SET team_id = NULL WHERE user_id = ?1", nativeQuery = true)
    int removeFromTeam(int user_id);

    Page<User> findAllByTeamTeamId(int teamId, Pageable paging);

}
