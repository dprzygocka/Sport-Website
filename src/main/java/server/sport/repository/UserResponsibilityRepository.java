package server.sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import server.sport.model.UserResponsibility;
import server.sport.model.UserResponsibilityPK;

public interface UserResponsibilityRepository extends JpaRepository<UserResponsibility, UserResponsibilityPK> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE dbo.user_responsibilities SET user_id = ?1 WHERE responsibility_id = ?2 AND activity_id=?3", nativeQuery = true)
    int saveUserResponsibilityForActivity(int user_id, int responsibility_id, int activity_id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO dbo.user_responsibilities (responsibility_id, activity_id) VALUES (?,?)", nativeQuery = true)
    int saveResponsibilityForActivity(int responsibility_id, int activity_id);
}
