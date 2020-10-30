package server.sport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.sport.model.Sport;
import server.sport.model.Team;
import server.sport.model.User;
import server.sport.repository.SportRepository;
import server.sport.repository.TeamRepository;
import server.sport.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    TeamRepository teamRepository;

    @GetMapping //list of teams with sport
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = teamRepository.findTeamsWithoutUsers();
        if (teams.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(teams, HttpStatus.OK);
        }
    }

}
