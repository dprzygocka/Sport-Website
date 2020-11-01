package server.sport.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import server.sport.model.Activity;
<<<<<<< HEAD


=======
>>>>>>> create activity method
import java.util.List;


public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    Activity findByActivityId(int activityId);
    List<Activity> findByTeamTeamId(int teamId);
<<<<<<< HEAD
=======
    Page<Activity> findByActivityName(String activityName, Pageable paging);
    Page<Activity> findByCapacity(int capacity, Pageable paging);
    Page<Activity> findByActivityType(String activityType, Pageable paging);


>>>>>>> create activity method
}
