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
import server.sport.model.Sport;
import server.sport.model.Team;
import server.sport.model.User;
import server.sport.model.helper.BasicUser;
import server.sport.repository.SportRepository;
import server.sport.repository.TeamRepository;
import server.sport.repository.UserRepository;

import java.util.*;

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
        List<Team> teams = teamRepository.findAll();
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
        userRepository.removeFromTeam(user.getUserId());
        return new ResponseEntity<>(teamRepository.findById(teamId).get(), HttpStatus.OK);
    }

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        }
        return Sort.Direction.DESC;
    }

    private boolean sortCriteriaCheck(String sort){
        return sort.equals("firstName") || sort.equals("lastName") || sort.equals("userId");
    }

    @GetMapping("/{team_id}/profiles") //profiles in the club (basic info - team, name, surname, email)
    public ResponseEntity<Map<String, Object>> getTeamMembers(
            @PathVariable("team_id") int teamId,
            @RequestParam(defaultValue = "userId,desc") String[] sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){

        List<Sort.Order> orders= new ArrayList<>();
        if(sort[0].contains(",")){
            for(String sortOrders: sort){
                String[] _sort = sortOrders.split(",");
                if (!sortCriteriaCheck(_sort[0])) throw new IllegalArgumentException("Can't sort according to: " + _sort[0]);
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            if (!sortCriteriaCheck(sort[0])) throw new IllegalArgumentException("Can't sort according to: " + sort[0]);
            orders.add(new Sort.Order(getSortDirection(sort[1]),sort[0]));
        }

        Pageable pagingSort = PageRequest.of(page, size,Sort.by(orders));
        Page<User> pageUsers = userRepository.findAllByTeamTeamId(teamId, pagingSort);
        if (pageUsers.getContent().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<BasicUser> basicUsers = new ArrayList<>();
        for (User user: pageUsers.getContent()) {
            Team tempTeam = user.getTeam();
            Sport tempSport = tempTeam.getSport();
            basicUsers.add(new BasicUser(user.getUserId(),user.getFirstName(),user.getLastName(),user.getEmail(),
                    new Team(tempTeam.getTeamId(),tempTeam.getTeamName(),
                            new Sport(tempSport.getSportId(),tempSport.getSportName(), tempSport.getResponsibilities())))
            );
        }

        Map<String,Object> response = new HashMap<>();
        response.put("users", basicUsers);
        response.put("currentPage", pageUsers.getNumber());
        response.put("totalItems", pageUsers.getTotalElements());
        response.put("totalPages", pageUsers.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

