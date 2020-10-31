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
            return new ResponseEntity<>(teams, HttpStatus.OK);
        }
    }

    @GetMapping("/{team_id}")//teams information
    public ResponseEntity<Team> getTeam(@PathVariable("team_id") int teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NoSuchElementException("Not found team with id = " + teamId));
        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    @PutMapping("/{team_id}")//update team @RequestBody team
    public ResponseEntity<Team> updateTeam(@PathVariable("team_id") int teamId, @RequestBody Team team) {
        Team _team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NoSuchElementException("Not found team with id = " + teamId));
        _team.setTeamId(teamId);
        _team.setTeamName(team.getTeamName());
        Optional<Sport> optionalSport = sportRepository.findById(team.getSport().getSportId());
        if(optionalSport.isEmpty()){
            Sport sport = sportRepository.save(team.getSport());
            _team.setSport(sport);
        } else {
            _team.setSport(team.getSport());
        }
        return new ResponseEntity<>(teamRepository.save(_team), HttpStatus.OK);
    }

    @PostMapping//new team @RequestBody team
    public ResponseEntity<Team> addTeam(@RequestBody Team team) {
        /*Optional<Sport> sport = sportRepository.findById(team.getSport().getSportsId());
        if(sport==null){
            sport = sportRepository.save(team.getSport());
            team.setSport(sport.get());
        }*/
        Sport sport;
        if (team.getSport() != null) {
            sport = sportRepository.findById(team.getSport().getSportId())
                    .orElseThrow(() -> new NoSuchElementException("Sport not found with id = " + team.getSport().getSportId()));
        } else {
            sport = sportRepository.save(team.getSport());
        }
        team.setSport(sport);
        return new ResponseEntity<>(teamRepository.save(team), HttpStatus.OK);
    }

    @DeleteMapping("{team_id}/{profile_id}")//delete user from team - not null or delete user completely? but for that its delete user
    public ResponseEntity<Team> deleteUserFromTeam(@PathVariable("team_id") int teamId,@PathVariable("profile_id") int userId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new NoSuchElementException("Not found tean with id = " + teamId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Not found user with id = " + userId));
        userRepository.removeFromTeam(user.getUserId());
        return new ResponseEntity<>(teamRepository.findById(teamId).get(), HttpStatus.OK);
    }
}

