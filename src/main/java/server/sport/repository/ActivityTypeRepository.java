package server.sport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.sport.model.Activity;

public interface ActivityRepository extends JpaRepository <Activity, Integer> {



}
