package server.sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.sport.model.Activity;
import server.sport.model.ActivityType;

import java.util.Optional;

public interface ActivityTypeRepository extends JpaRepository <ActivityType, Integer> {

    Optional<ActivityType> findActivityTypeByActivityTypeName (String activityTypeName);

}
