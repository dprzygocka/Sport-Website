package server.sport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.sport.exception.ResourceNotFoundException;
import server.sport.model.Sport;
import server.sport.model.Team;
import server.sport.model.User;
import server.sport.model.UserType;
import server.sport.repository.SportRepository;
import server.sport.repository.TeamRepository;
import server.sport.repository.UserRepository;
import server.sport.repository.UserTypeRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/profiles")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTypeRepository userTypeRepository;

    @Autowired
    TeamRepository teamRepository;

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        UserType userType = userTypeRepository.findUserTypeByUserName(user.getUserType().getUserName())
                .orElseThrow(() -> new ResourceNotFoundException("User type " + user.getUserType().getUserName() + " not found."));
        user.setUserType(userType);
        Team team = teamRepository.findTeamByTeamName(user.getTeam().getTeamName())
                .orElseThrow(() -> new ResourceNotFoundException("Team " + user.getTeam().getTeamName() + " not found."));
        user.setTeam(team);
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
    }
}

