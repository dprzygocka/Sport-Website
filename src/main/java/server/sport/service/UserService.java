package server.sport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import server.sport.exception.ResourceNotFoundException;
import server.sport.model.User;
import server.sport.repository.TeamRepository;
import server.sport.repository.UserRepository;
import server.sport.repository.UserTypeRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTypeRepository userTypeRepository;

    @Autowired
    TeamRepository teamRepository;

    public ResponseEntity<User> addUser(@RequestBody User user) {
        userTypeRepository.findById(user.getUserType().getUserTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("User type with id " + user.getUserType().getUserTypeId() + " not found."));
        teamRepository.findById(user.getTeam().getTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team with id " + user.getTeam().getTeamId() + " not found."));
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
    }
}
