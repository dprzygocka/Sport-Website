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

    private Sort.Direction getSortDirection(String direction){
        if(direction.equals("asc")){
            return Sort.Direction.ASC;
        }
        return Sort.Direction.DESC;
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        userTypeRepository.findById(user.getUserType().getUserTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("User type with id " + user.getUserType().getUserTypeId() + " not found."));
        teamRepository.findById(user.getTeam().getTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team with id " + user.getTeam().getTeamId() + " not found."));
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
    }

    private boolean sortCriteriaCheck(String sort){
        return sort.equals("firstName") || sort.equals("lastName") || sort.equals("userId");
    }

    @GetMapping("/{team_id}") //profiles in the club (basic info - team, name, surname, email)
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

    @GetMapping("/user/{user_id}")//
    public ResponseEntity<User> getUser(@PathVariable("user_id") int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Did not find User with id = " + userId));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //There is already a delete user from Team method in the TeamController class
    //This method will not deal with team nor activities. The rationale is that the user should still have their historic records within activities
    @DeleteMapping("/user/{user_id}")
    public ResponseEntity<HttpStatus> deleteUser (@PathVariable("user_id") int userId){
        userRepository.deleteByUserId(userId).
               orElseThrow(() -> new ResourceNotFoundException("Did not find User with id = " + userId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }







}

