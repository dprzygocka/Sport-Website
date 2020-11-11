package server.sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.sport.model.ActivityStatus;
import server.sport.model.ActivityStatusPK;

public interface ActivityStatusRepository extends JpaRepository<ActivityStatus, ActivityStatusPK> {
}
