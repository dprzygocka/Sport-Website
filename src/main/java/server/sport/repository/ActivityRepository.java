package server.sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.sport.model.Activity;

<<<<<<< HEAD
public interface ActivityRepository extends JpaRepository <Activity, Integer> {



=======
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    Activity findByActivityId(int activityId);
    List<Activity> findByTeamTeamId(int teamId);
>>>>>>> fbf6d543309bd0f29246b531706296d889b21536
}
