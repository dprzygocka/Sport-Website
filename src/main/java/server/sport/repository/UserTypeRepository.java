package server.sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import server.sport.model.User;
import server.sport.model.UserType;

import java.util.Optional;

public interface UserTypeRepository extends JpaRepository<UserType, Integer> {

    Optional<UserType> findUserTypeByUserName(String userName);
}
