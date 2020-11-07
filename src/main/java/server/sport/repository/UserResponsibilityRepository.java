package server.sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.sport.model.UserResponsibility;
import server.sport.model.UserResponsibilityPK;

public interface UserResponsibilityRepository extends JpaRepository<UserResponsibility, UserResponsibilityPK> {
}
