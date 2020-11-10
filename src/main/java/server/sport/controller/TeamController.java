package server.sport.controller;

import org.hibernate.procedure.NoSuchParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.sport.exception.ResourceNotFoundException;
import server.sport.exception.ForbiddenActionException;
import server.sport.model.Sport;
import server.sport.model.Team;
import server.sport.model.User;
import server.sport.repository.SportRepository;
import server.sport.repository.TeamRepository;
import server.sport.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SportRepository sportRepository;

    @GetMapping //list of teams with sport
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = teamRepository.findTeamsWithoutUsers();
        if (teams.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            for (Team team : teams) {
                //to remove associated users as query doesnt work
                team.setUsers(Collections.emptyList());
            }
            return new ResponseEntity<>(teams, HttpStatus.OK);
        }
    }

    @GetMapping("/{team_id}")//teams information
    public ResponseEntity<Team> getTeam(@PathVariable("team_id") int teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Did not find team with id = " + teamId));
        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    @PutMapping("/{team_id}")//update team @RequestBody team
    public ResponseEntity<Team> updateTeam(@PathVariable("team_id") int teamId, @RequestBody Team team) {
        Team _team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Did not find team with id = " + teamId));
        _team.setTeamId(teamId);
        _team.setTeamName(team.getTeamName());
        Sport sport = sportRepository.findById(team.getSport().getSportId())
                .orElseThrow(() -> new ResourceNotFoundException("Did not find sport with id = " + team.getSport().getSportId()));
        _team.setSport(sport);
        return new ResponseEntity<>(teamRepository.save(_team), HttpStatus.OK);
    }

    @PostMapping//new team @RequestBody team
    public ResponseEntity<Team> addTeam(@RequestBody Team team) {
        if (team.getSport() != null) {
            sportRepository.findById(team.getSport().getSportId())
                    .orElseThrow(() -> new ResourceNotFoundException("Sport not found with id = " + team.getSport().getSportId()));
        } else {
            throw new NoSuchParameterException("No sport passed");
        }
        return new ResponseEntity<>(teamRepository.save(team), HttpStatus.OK);
    }

    @DeleteMapping("{team_id}/{profile_id}")//delete user from team
    public ResponseEntity<Team> deleteUserFromTeam(@PathVariable("team_id") int teamId,@PathVariable("profile_id") int userId) {
        teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found tean with id = " + teamId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user with id = " + userId));
        if (user.getTeam().getTeamId() != teamId) {
            throw new ForbiddenActionException("Can't delete user with id: " + userId + " from team with id: " + teamId + ". User isn't part of this team.");
        }
        userRepository.removeFromTeam(user.getUserId());
        return new ResponseEntity<>(teamRepository.findById(teamId).get(), HttpStatus.OK);
    }
}

