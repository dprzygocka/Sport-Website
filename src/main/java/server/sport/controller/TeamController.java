package server.sport.controller;

import org.hibernate.procedure.NoSuchParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.sport.exception.ResourceNotFoundException;
import server.sport.exception.EntityCannotBeProcessedExecption;
import server.sport.model.Sport;
import server.sport.model.Team;
import server.sport.model.User;
import server.sport.model.BasicUser;
import server.sport.repository.SportRepository;
import server.sport.repository.TeamRepository;
import server.sport.repository.UserRepository;
import server.sport.service.TeamService;

import java.util.Collections;
import java.util.List;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api/teams")
public class TeamController {

    @Autowired
    TeamService teamService;

    @GetMapping //list of teams with sport
    public ResponseEntity<List<Team>> getAllTeams() {
        return  teamService.getAllTeams();
    }

    @GetMapping("/{team_id}")//teams information
    public ResponseEntity<Team> getTeam(@PathVariable("team_id") int teamId) {
        return teamService.getTeam(teamId);
    }

    @PutMapping("/{team_id}")//update team @RequestBody team
    public ResponseEntity<Team> updateTeam(@PathVariable("team_id") int teamId, @RequestBody Team team) {
        return teamService.updateTeam(teamId, team);
    }

    @PostMapping//new team @RequestBody team
    public ResponseEntity<Team> addTeam(@RequestBody Team team) {
        return teamService.addTeam(team);
    }

    @DeleteMapping("{team_id}/{profile_id}")//delete user from team
    public ResponseEntity<Team> deleteUserFromTeam(@PathVariable("team_id") int teamId,@PathVariable("profile_id") int userId) {
        return teamService.deleteUserFromTeam(teamId, userId);
    }

    @GetMapping("/{team_id}/profiles") //profiles in the club (basic info - team, name, surname, email)
    public ResponseEntity<Map<String, Object>> getTeamMembers(
            @PathVariable("team_id") int teamId,
            @RequestParam(defaultValue = "userId,desc") String[] sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return teamService.getTeamMembers(teamId, sort , page, size);
    }
}

