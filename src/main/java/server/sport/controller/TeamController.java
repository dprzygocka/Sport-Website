package server.sport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.sport.model.Team;
import server.sport.repository.TeamRepository;

import java.util.List;
import java.util.NoSuchElementException;

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

    @GetMapping("/{team_id}")//teams information
    public ResponseEntity<Team> getTeam(@PathVariable("team_id") int teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NoSuchElementException("Not found team with id = " + teamId));
        return new ResponseEntity<>(team, HttpStatus.OK);
    }


}

