package server.sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import server.sport.model.ActivityStatus;
import server.sport.model.ActivityStatusPK;

public interface ActivityStatusRepository extends JpaRepository<ActivityStatus, ActivityStatusPK> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE dbo.activity_statuses SET status_id=?1 WHERE activity_id = ?2 AND user_id = ?3", nativeQuery = true)
    int saveNewActivityStatus(int status_id, int activity_id, int user_id);
}
