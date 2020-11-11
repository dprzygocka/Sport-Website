package server.sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.sport.model.UserStatus;

public interface UserStatusRepository extends JpaRepository<UserStatus, Integer> {
    UserStatus findByStatusName(String status);
}
