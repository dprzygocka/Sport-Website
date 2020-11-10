package server.sport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.sport.exception.ResourceNotFoundException;
import server.sport.model.Sport;
import server.sport.model.Team;
import server.sport.model.User;
import server.sport.model.UserType;
import server.sport.model.helper.BasicUser;
import server.sport.repository.TeamRepository;
import server.sport.repository.UserRepository;
import server.sport.repository.UserTypeRepository;

import java.util.*;

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
        userTypeRepository.findById(user.getUserType().getUserTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("User type with id " + user.getUserType().getUserTypeId() + " not found."));
        teamRepository.findById(user.getTeam().getTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team with id " + user.getTeam().getTeamId() + " not found."));
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
    }
}

