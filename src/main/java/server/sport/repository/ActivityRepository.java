package server.sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.sport.model.Activity;


import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    Activity findByActivityId(int activityId);
    List<Activity> findByTeamTeamId(int teamId);
}
